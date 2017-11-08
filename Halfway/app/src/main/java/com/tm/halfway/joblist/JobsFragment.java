package com.tm.halfway.joblist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tm.halfway.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Last edit by tudor.maier on 29/10/2017.
 */

public class JobsFragment extends Fragment {

    private List<Job> jobsList;

    public JobsFragment() {
        jobsList = new ArrayList<>();
        Date date = new Date();
        jobsList.add(new Job("Java developer", "simple tasks", date, "Emerson"));
        jobsList.add(new Job("Janitor", "simple tasks", date, "Emerson"));
        jobsList.add(new Job("Babysister", "simple tasks", date, "Emerson"));
        jobsList.add(new Job("Garbageman", "simple tasks", date, "Emerson"));
        jobsList.add(new Job("Chef", "simple tasks", date, "Emerson"));
        jobsList.add(new Job("Engineer", "simple tasks", date, "Emerson"));
        jobsList.add(new Job("Python developer", "simple tasks", date, "Emerson"));
        jobsList.add(new Job("HR Assistant", "simple tasks", date, "Emerson"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jobs_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ListView jobsListView = (ListView) view.findViewById(R.id.jobsListId);

        jobsListView.setAdapter(new JobListAdapter(getContext(), R.layout.jobs_item_list, jobsList));

        jobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("position", (int) id);

                jobDetailsFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.main_fragment, jobDetailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
