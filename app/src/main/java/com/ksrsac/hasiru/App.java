package com.ksrsac.hasiru;

import android.app.Application;
import android.content.Context;

import com.ksrsac.hasiru.database.SqliteHelper;

import androidx.multidex.MultiDex;

public class App extends Application {

    static Pref pref;
    public static final String SHARED_PREF = "esrimap";
    public static SqliteHelper mDBHandler;
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mDBHandler = new SqliteHelper(this);
        appContext = this;
    }
    public static Pref getPref() {
        if (pref == null) {
            pref = new Pref(getAppContext());
        }
        return pref;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
