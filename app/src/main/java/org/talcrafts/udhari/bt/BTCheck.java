package org.talcrafts.udhari.bt;

/**
 * Created by sushma on 15/2/18.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;

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
