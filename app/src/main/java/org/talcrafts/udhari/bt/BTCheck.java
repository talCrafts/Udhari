package org.talcrafts.udhari.bt;

/**
 * Created by sushma on 15/2/18.
 */

import android.content.Context;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.talcrafts.udhari.data.DatabaseContract;
import org.talcrafts.udhari.neighbours.model.NotificationReceiver;
import org.talcrafts.udhari.tx.AddTxnActivity;
import org.talcrafts.udhari.tx.TxRecyclerApapterImpl;
import org.talcrafts.udhari.wifi.WIFIContent;

public class BTCheck {

    public static class Message {
        final public String title;
        final public String message;

        public Message(java.lang.String title, java.lang.String message) {
            this.title = title;
            this.message = message;
        }
    }


    public static Message check(Context context) {
        // BT LE
        BluetoothManager manager = (BluetoothManager) context.getSystemService(
                Context.BLUETOOTH_SERVICE);
        BluetoothAdapter btAdapter = manager.getAdapter();
        if (btAdapter == null) {
            return new Message("Bluetooth Error", "Bluetooth not detected on device");
        } else if (!btAdapter.isEnabled()) {
            return new Message("BL is Disabled", "Please enable Bluetooth and restart App");
        } else if (!btAdapter.isMultipleAdvertisementSupported()) {
            return new Message("Not supported", "BLE advertising not supported on this device");
        } else {
            BluetoothLeAdvertiser adv = btAdapter.getBluetoothLeAdvertiser();
            //TODO callback when someone scans
        }

        return new Message("All Good","Bluetooth new discovery");
        // BT LE
    }

}
