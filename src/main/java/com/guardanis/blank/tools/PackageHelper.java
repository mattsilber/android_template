package com.guardanis.blank.tools;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class PackageHelper {

    public static String getMetaData(Context context, String key, String defaultValue) {
        try{
            String name = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString(key);
            if(!name.equals("null")) return name;
        }
        catch(Exception e){ e.printStackTrace(); }
        return defaultValue;
    }

    public static int getVersionCode(Context context) {
        try{
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        catch(NameNotFoundException e){ return -1; }
    }

    public static String getVersionName(Context context) {
        try{
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        }
        catch(NameNotFoundException e){ return "Unknown"; }
    }

    public static void displayAllMetaData(Context context) {
        try{
            for(String s : context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.keySet())
                Log.d("Guardanis", s + ": " + context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString(s));
        }
        catch(Exception e){ e.printStackTrace(); }
    }
}
