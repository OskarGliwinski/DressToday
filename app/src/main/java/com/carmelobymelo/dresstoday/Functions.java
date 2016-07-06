package com.carmelobymelo.dresstoday;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Oskar Gliwinski
 */
public class Functions {

    public static void exitForm(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("Do you want to Quit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((Activity) context).finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
