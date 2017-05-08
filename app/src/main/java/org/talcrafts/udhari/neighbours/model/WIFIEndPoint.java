package org.talcrafts.udhari.neighbours.model;

import android.net.wifi.ScanResult;

/**
 * Created by ashis on 5/5/2017.
 */

public class WIFIEndPoint extends EndPoint {

    public WIFIEndPoint(String id, String content, String details) {
        super(id);
        this.content = content;
        this.details = details;
        this.type = this.getClass().getSimpleName();
    }

    public static WIFIEndPoint from(ScanResult result) {
        WIFIEndPoint obj = new WIFIEndPoint(result.BSSID, result.capabilities, result.SSID.toString());
        return obj;
    }

    @Override
    public String toString() {
        return content;
    }
}