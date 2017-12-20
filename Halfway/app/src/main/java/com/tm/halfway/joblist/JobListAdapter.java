package com.tm.halfway.joblist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tm.halfway.R;
import com.tm.halfway.model.Job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Last edit by tudor.maier on 29/10/2017.
 */

public class JobListAdapter extends ArrayAdapter<Job> {
    private static List<Job> itemsList;
    private Context context;
    private int resLayout;

    public JobListAdapter(Context context, int resource, List<Job> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resLayout = resource;
        this.itemsList = objects;
    }

    public List<Job> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Job> itemsList) {
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, resLayout, null);

        TextView jobNameTextView = (TextView) view.findViewById(R.id.jobName);
        TextView jobDescriptionTextView = (TextView) view.findViewById(R.id.jobDescription);
        TextView jobEmployerTextView = (TextView) view.findViewById(R.id.jobEmployer);
        TextView jobDateTextView = (TextView) view.findViewById(R.id.jobDate);

        final Job currentJob = itemsList.get(position);

        jobNameTextView.setText(currentJob.getTitle());
        jobDescriptionTextView.setText(currentJob.getDescription());
        jobEmployerTextView.setText(currentJob.getOwner());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String date = dateFormat.format(currentJob.getEnds_at());
        jobDateTextView.setText(date);

        final Button removeJob = (Button) view.findViewById(R.id.removeJobButton);

        removeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete job: " + currentJob.getTitle())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                JobHelper mDatabaseHelper = new JobHelper(getContext());
                                mDatabaseHelper.deleteJob(Integer.parseInt(currentJob.getId()));
                                itemsList.remove(currentJob);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //do nothing
                            }
                        })
                        .show();
            }
        });

        return view;
    }
}
