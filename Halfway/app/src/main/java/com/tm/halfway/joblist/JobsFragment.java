package com.tm.halfway.joblist;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.tm.halfway.R;
import com.tm.halfway.create_job.CreateJobFragment;
import com.tm.halfway.jobdetails.JobDetailsFragment;
import com.tm.halfway.model.Job;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Last edit by tudor.maier on 29/10/2017.
 */

public class JobsFragment extends Fragment {

    private List<Job> jobsList;
    private JobHelper mDatabaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jobs_fragment, container, false);

        jobsList = new ArrayList<>();
        mDatabaseHelper = new JobHelper(getContext());
//        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
//        mDatabaseHelper.onUpgrade(db,0,0);

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final ListView jobsListView = (ListView) view.findViewById(R.id.jobsListId);
        final JobListAdapter jobListAdapter = new JobListAdapter(getContext(), R.layout.jobs_item_list, jobsList);

        jobsListView.setAdapter(jobListAdapter);
        View headerView = inflater.inflate(R.layout.jobs_header_view, container, false);
        jobsListView.addHeaderView(headerView);
        jobsListView.setDividerHeight(0);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        String token = sharedPref.getString("Authorization", "null");

//        populateListViewOffline(jobListAdapter);
        if (!hasInternetConnection()) {
            populateListViewOffline(jobListAdapter);
        } else {
            populateListViewOnline(token, jobListAdapter);
        }

        jobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("JOB", jobListAdapter.getItem((int) id));


                jobDetailsFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.main_fragment, jobDetailsFragment);
                fragmentTransaction.addToBackStack("JobDetails");
                fragmentTransaction.commit();
            }
        });

        Button addButton = (Button) headerView.findViewById(R.id.add_button);

        String role = sharedPref.getString("Role", "null");

        if("QUEST".equals(role)) {
            addButton.setAlpha(0);
        } else {
            addButton.setAlpha(1);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateJobFragment createJobFragment = new CreateJobFragment();

                fragmentTransaction.replace(R.id.main_fragment, createJobFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void populateListViewOnline(String token, final JobListAdapter jobListAdapter) {
        new JobListAsync() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    List<Job> tmpList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("jobs");

//                    DateFormat df = DateFormat.getDateTimeInstance();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobJSON = jsonArray.getJSONObject(i);
                        Job job = new Job();
                        job.setId(jobJSON.getString("id"));
                        job.setTitle(jobJSON.getString("title"));
                        job.setDescription(jobJSON.getString("description"));
                        Date created_at = df.parse(jobJSON.getString("createdAt"));
                        job.setCreated_at(created_at);
                        Date updated_at = df.parse(jobJSON.getString("updatedAt"));
                        job.setUpdated_at(updated_at);
                        Date ends_at = df.parse(jobJSON.getString("endsAt"));
                        job.setEnds_at(ends_at);
                        job.setCost(Float.valueOf(jobJSON.getString("cost")));
                        job.setOwner(jobJSON.getString("owner"));
                        job.setCategory(jobJSON.getString("category"));
                        job.setLocation(jobJSON.getString("location"));

                        tmpList.add(job);
                    }

                    if (jobsList.size() != tmpList.size()) {
                        jobsList.clear();
                        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                        mDatabaseHelper.onUpgrade(db, 0 ,0);
                        for (Job job : tmpList) {
                            if (job != null) {
                                mDatabaseHelper.addJob(job);
                                jobsList.add(job);
                            }
                        }

                        jobListAdapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    Log.e("JobsFragment", e.getMessage());
                }
            }
        }.execute(token);
    }

    private boolean hasInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void populateListViewOffline(JobListAdapter jobListAdapter) {
        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()) {
            Job job = convertJob(data);
            jobsList.add(job);
        }
        jobListAdapter.notifyDataSetChanged();
    }

    private Job convertJob(Cursor data) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        String id = data.getString(0);
        String title = data.getString(1);
        String description = data.getString(2);
        Date created_at = null;
        Date updated_at = null;
        Date ends_at = null;
        Float cost = null;
        String category = null;
        String location = null;
        try {
            if (data.getString(3) != null) {
                created_at = df.parse(data.getString(3));
            }
            if (data.getString(4) != null) {
                updated_at = df.parse(data.getString(4));
            }
            ends_at = df.parse(data.getString(5));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (data.getString(6) != null) {
            cost = Float.parseFloat(data.getString(6));
        }
        String owner = data.getString(7);
        if (data.getString(8) != null) {
            category = data.getString(8);
        }
        if (data.getString(9) != null) {
            location = data.getString(9);
        }

        return new Job(id, title, description, created_at, updated_at, ends_at, cost, owner, category, location);
    }

}
