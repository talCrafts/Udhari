package org.talcrafts.udhari.wifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import org.talcrafts.udhari.neighbours.model.EndPoint;
import org.talcrafts.udhari.neighbours.model.EndPointContent;

import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class WIFIContent extends EndPointContent {

    private static WIFIContent ourInstance;


    public static WIFIContent getInstance(WifiManager wifiManager) {
        if (wifiManager != null) {
            if (ourInstance == null) {
                ourInstance = new WIFIContent(wifiManager);
            }
        }
        return ourInstance;
    }

    final WifiManager mWifiManager;

    public WIFIContent(WifiManager wifiManager) {
        this.mWifiManager = wifiManager;
    }

    public WIFIContent startScan() {
        this.mWifiManager.startScan(); //TODO better place to initialize
        return this;
    }

    public void syncResult() {
        ITEMS.clear();
        ITEM_MAP.clear();
        List<ScanResult> wifiScanList = mWifiManager.getScanResults();
        for (ScanResult result : wifiScanList) {
            addItem(result);
        }
    }

    private void addItem(ScanResult item) {
        WIFIEndPoint endpoint = WIFIEndPoint.from(item);
        addItem(endpoint);
    }

    public WIFIEndPoint getItem(int position) {
        EndPoint endPoint = ITEMS.get(position);
        if (endPoint instanceof WIFIEndPoint) {
            return (WIFIEndPoint) endPoint;
        }
        return null;
    }

    public WIFIEndPoint getItem(String key) {
        EndPoint endPoint = ITEM_MAP.get(key);
        if (endPoint instanceof WIFIEndPoint) {
            return (WIFIEndPoint) endPoint;
        }
        return null;
    }

    public int size() {
        return ITEMS.size();
    }
}
