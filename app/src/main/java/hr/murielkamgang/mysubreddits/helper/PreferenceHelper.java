/*
 * Copyright 2018 AG04 INNOVATIVE SOLUTIONS d.o.o. All rights reserved.
 */

package hr.murielkamgang.mysubreddits.helper;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by muriel on 25/05/2018.
 */

@Singleton
public class PreferenceHelper {

    private static final String SHARED_PREF_KEY = "SHARED_PREF_KEY";
    private static final String PREF_CONFIGURED = "PREF_CONFIGURED";

    private final Context context;

    @Inject
    PreferenceHelper(Context context) {
        this.context = context;
    }

    private SharedPreferences.Editor editor() {
        return context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE).edit();
    }

    private SharedPreferences preferences() {
        return context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }

    public void setConfigured() {
        editor().putBoolean(PREF_CONFIGURED, true).commit();
    }

    public void setNotConfigured() {
        editor().putBoolean(PREF_CONFIGURED, false).commit();
    }

    public boolean isConfigured() {
        return preferences().getBoolean(PREF_CONFIGURED, false);
    }

}
