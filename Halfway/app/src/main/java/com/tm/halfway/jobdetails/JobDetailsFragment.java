package com.tm.halfway.jobdetails;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tm.halfway.R;
import com.tm.halfway.api.ApiHelper;
import com.tm.halfway.model.ApiCallback;
import com.tm.halfway.model.Category;
import com.tm.halfway.model.GetCategoriesResponse;
import com.tm.halfway.model.GetLocationsResponse;
import com.tm.halfway.model.Job;
import com.tm.halfway.model.Location;
import com.tm.halfway.utils.JobsOrderEnum;
import com.tm.halfway.utils.SessionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Last edit by tudor.maier on 29/10/2017.
 */

public class JobDetailsFragment extends Fragment {

    private final String TAG = "JobDetailsFragment";
    private Job currentJob;
    private DatePickerDialog.OnDateSetListener mDataSetListener;

    @BindView(R.id.jdf_bt_apply)
    Button mApplyBT;
    @BindView(R.id.jdf_tv_you_applied)
    TextView mYouAppliedTV;
    @BindView(R.id.jdf_tv_location)
    TextView mLocationTV;
    @BindView(R.id.jdf_tv_category)
    TextView mCategoryTV;
    @BindView(R.id.jf_s_location_spinner)
    Spinner mLocationSP;
    @BindView(R.id.jf_s_category_spinner)
    Spinner mCategorySP;

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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        final TextView editJobName = (TextView) view.findViewById(R.id.editJobName);
        final TextView editJobDescription = (TextView) view.findViewById(R.id.editJobDescription);
        final TextView editJobDate = (TextView) view.findViewById(R.id.editJobDate);
        final TextView editJobEmployer = (TextView) view.findViewById(R.id.editJobEmployer);

        updateUIForAppliedStatus();

        configureSpinners();

        editJobName.setText(currentJob.getTitle());
        editJobDescription.setText(currentJob.getDescription());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String result = df.format(currentJob.getEnds_at());
        editJobDate.setText(result);
        editJobEmployer.setText(currentJob.getOwner());

        SharedPreferences sharedPref = getContext().getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        String role = sharedPref.getString("Role", "null");
    }

    private void configureSpinners() {
        mCategorySP.setVisibility(View.GONE);
        mLocationSP.setVisibility(View.GONE);
        mLocationTV.setVisibility(View.VISIBLE);
        mCategoryTV.setVisibility(View.VISIBLE);
        mLocationTV.setText(currentJob.getLocation());
        mCategoryTV.setText(currentJob.getCategory());
    }

    private void updateUIForAppliedStatus() {
        if (!SessionUtils.isUserClient()) {
            mApplyBT.setVisibility(currentJob.isApplied() ? View.GONE : View.VISIBLE);
            mYouAppliedTV.setVisibility(!currentJob.isApplied() ? View.GONE : View.VISIBLE);
        } else {
            mApplyBT.setVisibility(View.GONE);
            mYouAppliedTV.setVisibility(View.GONE);
        }
    }

    private void refreshCurrentJob(TextView editJobName, TextView editJobDate, TextView editJobDescription, TextView editJobEmployer) {
        currentJob.setTitle(String.valueOf(editJobName.getText()));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date result;
        try {
            result = df.parse(String.valueOf(editJobDate.getText()));
            currentJob.setEnds_at(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentJob.setDescription(String.valueOf(editJobDescription.getText()));
        currentJob.setOwner(String.valueOf(editJobEmployer.getText()));
    }

    @OnClick(R.id.jdf_bt_apply)
    public void onApplyClicked() {
        ApiHelper.getApi().applyToJob(currentJob.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(JobDetailsFragment.this.getContext(), "Applied for job successfully", Toast.LENGTH_SHORT).show();
                currentJob.setApplied(true);
                updateUIForAppliedStatus();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
