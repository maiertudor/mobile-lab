package com.tm.halfway.joblist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tm.halfway.R;

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

    public static List<Job> getItemsList() {
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

        Job currentJob = itemsList.get(position);

        jobNameTextView.setText(currentJob.getName());
        jobDescriptionTextView.setText(currentJob.getDescription());
        jobEmployerTextView.setText(currentJob.getEmployer());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String date = dateFormat.format(currentJob.getExpirationDate());
        jobDateTextView.setText(date);

        return view;
    }
}
