package com.tm.halfway.joblist;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tm.halfway.MainActivity;
import com.tm.halfway.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Last edit by tudor.maier on 29/10/2017.
 */

public class JobDetailsFragment extends Fragment {

    private Job currentJob;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        int position = 0;

        if (bundle != null) {
            position = bundle.getInt("position");
        }
        currentJob = JobListAdapter.getItemsList().get(position);

        return inflater.inflate(R.layout.job_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editJobName = (EditText) view.findViewById(R.id.editJobName);
        final EditText editJobDescription = (EditText) view.findViewById(R.id.editJobDescription);
        final EditText editJobDate = (EditText) view.findViewById(R.id.editJobDate);
        final EditText editJobEmployer = (EditText) view.findViewById(R.id.editJobEmployer);

        editJobName.setText(currentJob.getName());
        editJobDescription.setText(currentJob.getDescription());
        DateFormat df = DateFormat.getDateTimeInstance();
        String result = df.format(currentJob.getExpirationDate());
        editJobDate.setText(result);
        editJobEmployer.setText(currentJob.getEmployer());

        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentJob.setName(String.valueOf(editJobName.getText()));
                currentJob.setDescription(String.valueOf(editJobDescription.getText()));
                String dateString = String.valueOf(editJobDate.getText());
                DateFormat df = DateFormat.getDateTimeInstance();
                try {
                    Date result = df.parse(dateString);
                    currentJob.setExpirationDate(result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                currentJob.setEmployer(String.valueOf(editJobEmployer.getText()));

                Toast.makeText(getContext(), "Saved new job details!", Toast.LENGTH_SHORT).show();

                getFragmentManager().popBackStackImmediate();
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

    public static void openGmail(Activity activity, String[] email, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = activity.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);

        activity.startActivity(emailIntent);
    }
}
