package com.guardanis.blank.tools

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.util.Log

object PackageHelper {

    fun getMetaData(context: Context, key: String, defaultValue: String): String {
        try {
            return context.packageManager
                    .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                    .metaData
                    .getString(key)
                    .takeIf({ !it.equals("null") }) ?: defaultValue
        }
        catch (e: Exception) { e.printStackTrace() }

        return defaultValue
    }

    fun getVersionCode(context: Context): Int {
        try {
            return context.packageManager
                    .getPackageInfo(context.packageName, 0)
                    .versionCode
        }
        catch (e: NameNotFoundException) { return -1 }
    }

    fun getVersionName(context: Context): String {
        try {
            return context.packageManager
                    .getPackageInfo(context.packageName, 0)
                    .versionName
        }
        catch (e: NameNotFoundException) { return "Unknown" }
    }

    fun displayAllMetaData(context: Context) {
        try {
            val metadata = context.packageManager
                    .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                    .metaData

            metadata.keySet()
                    .forEach({
                        Log.d("Guardanis", "$it:${metadata.getString(it)}")
                    })
        }
        catch (e: Exception) { e.printStackTrace() }
    }
}
