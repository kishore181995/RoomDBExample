package com.example.appcaremachtas.database;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appcaremachtas.model.Person;


@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static final String LOG_TAG = ApplicationDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "userslist";
    private static ApplicationDatabase sInstance;

    public static ApplicationDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance readyy");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        ApplicationDatabase.class, ApplicationDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance ready");
        return sInstance;
    }

    public abstract PersonDao personDao();
}
