package org.talcrafts.udhari.neighbours.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import org.talcrafts.udhari.wifi.WIFIContent;

/**
 * Created by ashis on 5/8/2017.
 */

public class NotificationReceiver extends BroadcastReceiver {

    public NotificationReceiver(WifiManager mgr) {
        WIFIContent.getInstance(mgr);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
            WIFIContent.getInstance(null).syncResult();
        }
    }
}
