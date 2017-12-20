package com.tm.halfway.create_job;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.tm.halfway.R;
import com.tm.halfway.joblist.JobHelper;
import com.tm.halfway.model.Job;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.tm.halfway.R.id.editJobDescription;
import static com.tm.halfway.R.id.editJobName;

/**
 * Last edit by tudor.maier on 16/12/2017.
 */

public class CreateJobFragment extends Fragment {
    private DatePickerDialog.OnDateSetListener mDataSetListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.job_create_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText createJobName = (EditText) view.findViewById(R.id.createJobName);
        final EditText createJobDescription = (EditText) view.findViewById(R.id.createJobDescription);
        final EditText createJobDate = (EditText) view.findViewById(R.id.createJobDate);
        final EditText createJobEmployer = (EditText) view.findViewById(R.id.createJobEmployer);

        Button saveButton = (Button) view.findViewById(R.id.saveNewJob);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobHelper mDatabaseHelper = new JobHelper(getContext());

                final Job newJob = new Job();

                newJob.setTitle(String.valueOf(createJobName.getText()));
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date result;
                try {
                    result = df.parse(String.valueOf(createJobDate.getText()));
                    newJob.setEnds_at(result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                newJob.setDescription(String.valueOf(createJobDescription.getText()));
                newJob.setOwner(String.valueOf(createJobEmployer.getText()));

                mDatabaseHelper.addJob(newJob);
                Toast.makeText(getContext(), "Saved new job details!", Toast.LENGTH_SHORT).show();

                getFragmentManager().popBackStackImmediate();
            }
        });

        createJobDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDataSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                Log.d("onDataSet: ", year + "/" + month+ "/" + day);

                String date = year + "/" + month+ "/" + day + " 00:00";
                System.out.println(date);
                createJobDate.setText(date);

            }
        };

    }
}
