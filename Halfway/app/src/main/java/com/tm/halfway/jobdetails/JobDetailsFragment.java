package com.tm.halfway.jobdetails;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.tm.halfway.R;
import com.tm.halfway.joblist.JobHelper;
import com.tm.halfway.model.Job;
import com.tm.halfway.joblist.JobListAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Last edit by tudor.maier on 29/10/2017.
 */

public class JobDetailsFragment extends Fragment {

    private Job currentJob;
    private DatePickerDialog.OnDateSetListener mDataSetListener;

    public static void openGmail(Activity activity, String[] email, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = activity.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);

        activity.startActivity(emailIntent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.job_details_fragment, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentJob = (Job) bundle.getSerializable("JOB");
        }
        initGraph(view);
        return view;
    }

    private void initGraph(View view) {
        GraphView graph = (GraphView) view.findViewById(R.id.graph);

        JobHelper mDatabaseHelper = new JobHelper(getContext());
        Cursor data = mDatabaseHelper.getData();

        Map<String, Integer> nameMap = new HashMap<>();
        while (data.moveToNext()) {
            String name = data.getString(1);
            if (nameMap.containsKey(name)) {
                int value = nameMap.get(name);
                value++;
                nameMap.put(name, value);
            } else {
                nameMap.put(name, 1);
            }
        }
        int i = 0;
        String[] horizontalLabels = new String[nameMap.keySet().size()];
        DataPoint[] dataPoints = new DataPoint[nameMap.keySet().size()];
        for (String name : nameMap.keySet()) {
            dataPoints[i] = new DataPoint(i, nameMap.get(name).doubleValue());
            horizontalLabels[i] = name;
            i++;
        }

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(horizontalLabels);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
        graph.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });

        series.setSpacing(50);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editJobName = (EditText) view.findViewById(R.id.editJobName);
        final EditText editJobDescription = (EditText) view.findViewById(R.id.editJobDescription);
        final EditText editJobDate = (EditText) view.findViewById(R.id.editJobDate);
        final EditText editJobEmployer = (EditText) view.findViewById(R.id.editJobEmployer);

        editJobName.setText(currentJob.getTitle());
        editJobDescription.setText(currentJob.getDescription());
        DateFormat df = DateFormat.getDateTimeInstance();
        String result = df.format(currentJob.getEnds_at());
        editJobDate.setText(result);
        editJobEmployer.setText(currentJob.getOwner());

        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobHelper mDatabaseHelper = new JobHelper(getContext());

                currentJob.setTitle(String.valueOf(editJobName.getText()));
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date result;
                try {
                    result = df.parse(String.valueOf(editJobDate.getText()));
                    currentJob.setEnds_at(result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                currentJob.setDescription(String.valueOf(editJobDescription.getText()));
                currentJob.setOwner(String.valueOf(editJobEmployer.getText()));

                if (mDatabaseHelper.updateJob(currentJob) == 1) {
                    Toast.makeText(getContext(), "Saved new job details!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getContext(), "Job couldn't be saved!", Toast.LENGTH_SHORT).show();
                }
//                saveJob(currentJob.getId(), String.valueOf(editJobName.getText()), String.valueOf(editJobDescription.getText()), String.valueOf(editJobDate.getText()), String.valueOf(editJobEmployer.getText()));

            }
        });

        EditText feebackEditText = (EditText) view.findViewById(R.id.feebackEditText);
        final String feedbackText = feebackEditText.getText().toString();

        Button sendFeedbackButton = (Button) view.findViewById(R.id.sendFeedbackButton);
        sendFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGmail(getActivity(), new String[]{"johndoe@gmail.com"}, "Feedback from HalfWay", feedbackText);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from HalfWay App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, feedbackText);
                startActivity(emailIntent);
            }
        });


    }

    private void saveJob(String id, String title, String description, String date, String owner) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date result = null;
        try {
            result = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        currentJob.setTitle(title);
        currentJob.setDescription(description);
        currentJob.setEnds_at(result);
        currentJob.setOwner(owner);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        String token = sharedPref.getString("Authorization", "null");

        new JobUpdateAsync().execute(token, id, currentJob);
    }


}
