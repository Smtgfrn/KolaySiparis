package com.smtgfrn.smtgf.restoransiparis;

import android.content.Context;
import android.content.SharedPreferences;

public class ShredPref {
    static final String PREF_NAME = "Dosya";

    public void save(Context context,String key,String text)
    {
        SharedPreferences setting = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = setting.edit();
        editor.putString(key,text);

        editor.commit();
    }

    public void saveBoolen(Context context,String key,Boolean value)
    {
        SharedPreferences setting = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean(key,value);

        editor.commit();
    }

    public String getValue(Context context,String key)
    {
        SharedPreferences setting = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        String text = setting.getString(key,null);
        return text;
    }

    public Boolean getValueBoolean(Context context,String key)
    {
        SharedPreferences setting = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        Boolean value = setting.getBoolean(key,false);
        return value;
    }


}
