package com.transparent.automationfactory.base.storage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.transparent.automationfactory.AutomationApplication;

import java.util.HashSet;
import java.util.Set;

public class PreferenceTools implements PreferencesConstant {

    public static final void clearInfo(String name) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    public static final int getInt(String name, String key, int defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static final int getInt(String key, int defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static final void putInt(String name, String key, int value) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static final void putInt(String key, int value) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static final String getString(String name, String key, String defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static final String getString(String key, String defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static final void putString(String name, String key, String value) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static final void putString(String key, String value) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static final Boolean getBoolean(String name, String key, Boolean defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static final Boolean getBoolean(String key, Boolean defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static final void putBoolean(String name, String key, Boolean value) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static final void putBoolean(String key, Boolean value) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static final float getBoolean(String name, String key, float defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    public static final float getBoolean(String key, float defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    public static final long getLong(String name, String key, long defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static final long getLong(String key, long defValue) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static final void putLong(String name, String key, long value) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(name, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static final void putLong(String key, long value) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static final void putStringSet(String key, Set<String> values) {
        HashSet<String> set = new HashSet<String>();
        set.addAll(values);
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.putStringSet(key, set);
        editor.commit();
    }

    public static final Set<String> getStringSet(String key) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        return sp.getStringSet(key, new HashSet<String>());
    }

    public static final void remove(String key) {
        SharedPreferences sp = AutomationApplication.sContext.getSharedPreferences(CONFIG_SHARED_PREFERENCES_ENTITY_KEY, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

}
