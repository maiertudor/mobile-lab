package com.tm.halfway.joblist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tm.halfway.model.Job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Last edit by tudor.maier on 06/12/2017.
 */

public class JobHelper extends SQLiteOpenHelper {

    private static final String TAG = "JobHelper";

    private static final String TABLE_NAME = "jobs";
    private static final String COL1 = "id";
    private static final String COL2 = "title";
    private static final String COL3 = "description";
    private static final String COL4 = "created_at";
    private static final String COL5 = "updated_at";
    private static final String COL6 = "ends_at";
    private static final String COL7 = "cost";
    private static final String COL8 = "owner";
    private static final String COL9 = "category";
    private static final String COL10 = "location";


    public JobHelper(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + " ( " + COL1 + " INTEGER PRIMARY KEY, "
                + COL2 + " TEXT, "
                + COL3 + " TEXT, "
                + COL4 + " TEXT, "
                + COL5 + " TEXT, "
                + COL6 + " TEXT, "
                + COL7 + " FLOAT, "
                + COL8 + " TEXT, "
                + COL9 + " TEXT, "
                + COL10 + " TEXT)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTable);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = getWritableDatabase();
        String getQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(getQuery, null);

        return data;
    }

    public boolean addJob(Job job) {
        SQLiteDatabase db = getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, job.getTitle());
        contentValues.put(COL3, job.getDescription());
        if (job.getCreated_at() != null) {
            contentValues.put(COL4, dateFormat.format(job.getCreated_at()));
        }
        if (job.getUpdated_at() != null) {
            contentValues.put(COL5, dateFormat.format(job.getUpdated_at()));
        }
        if (job.getEnds_at() != null) {
            contentValues.put(COL6, dateFormat.format(job.getEnds_at()));
        }
        if (job.getCost() != null) {
            contentValues.put(COL7, job.getCost());
        }
        if (job.getOwner() != null) {
            contentValues.put(COL8, job.getOwner());
        }
        if (job.getCategory() != null) {
            contentValues.put(COL9, job.getCategory());
        }
        if (job.getLocation() != null) {
            contentValues.put(COL10, job.getLocation());
        }

        Log.d(TAG, "addJob: " + job.toString() + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public boolean deleteJob(int jobID) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteJobQuery = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + jobID + "'";
        db.execSQL(deleteJobQuery);
        return true;
    }

    public int updateJob(Job newJob) {
        SQLiteDatabase db = getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, newJob.getTitle());
        contentValues.put(COL3, newJob.getDescription());
        contentValues.put(COL6, dateFormat.format(newJob.getEnds_at()));
        contentValues.put(COL8, newJob.getOwner());

        return db.update(TABLE_NAME, contentValues, "id=" + newJob.getId(), null);
    }
}
