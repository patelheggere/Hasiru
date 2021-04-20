package com.ksrsac.hasiru;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
    private Context mContext;

    public Pref(Context context) {

        this.mContext = context;
    }

    public String getString(String key) {

        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        try {
            String value = settings.getString(key, null);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public String getString(String key, String defaultValue) {

        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        try {
            String value = settings.getString(key, defaultValue);
            return value;
        } catch (Exception e) {
            return null;
        }
    }
    public int getInt(String key) {

        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        try {
            int value = settings.getInt(key, 0);
            return value;
        } catch (Exception e) {
            return 0;
        }
    }
    public long getLong(String key) {

        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        try {
            long value = settings.getLong(key, 0);
            return value;
        } catch (Exception e) {
            return 0;
        }
    }
    public float getFloat(String key) {

        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        try {
            float value = settings.getFloat(key, 0.0f);
            return value;
        } catch (Exception e) {
            return 0;
        }
    }
    public boolean getBoolean(String key) {

        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        try {
            boolean value = settings.getBoolean(key, false);
            return value;
        } catch (Exception e) {
            return true;
        }
    }
    public void put(String key, String value) {
        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void put(String key, boolean value) {
        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public void put(String key, long value) {
        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }
    public void put(String key, float value) {
        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    public void put(String key, int value) {
        SharedPreferences settings = mContext.getSharedPreferences(
                App.SHARED_PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public void remove(String key) {
        SharedPreferences settings = mContext.getSharedPreferences(App.SHARED_PREF, Context.MODE_PRIVATE);
        settings.edit().remove(key).commit();
    }
}

