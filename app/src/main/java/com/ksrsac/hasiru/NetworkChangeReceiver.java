package com.ksrsac.hasiru;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by shanuma on 2/27/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (isOnline(context)) {
                Intent bDintent = new Intent();
                bDintent.putExtra("NET",true);
                bDintent.setAction("NET-RECEVIED");
                context.sendBroadcast(bDintent);
                //dialog(true);
                Log.e("net_coonection", "Online Connect Intenet ");
            } else {
                Intent bDintent = new Intent();
                bDintent.putExtra("NET",false);
                bDintent.setAction("NET-RECEVIED");
                context.sendBroadcast(bDintent);
                //dialog(false);
                Log.e("net_coonection", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}