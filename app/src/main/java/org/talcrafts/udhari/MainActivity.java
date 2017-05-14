package org.talcrafts.udhari;

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


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 0;
    private TxRecyclerApapterImpl recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // BT LE
        BluetoothManager manager = (BluetoothManager) getApplicationContext().getSystemService(
                Context.BLUETOOTH_SERVICE);
        BluetoothAdapter btAdapter = manager.getAdapter();
        if (btAdapter == null) {
            showFinishingAlertDialog("Bluetooth Error", "Bluetooth not detected on device");
        } else if (!btAdapter.isEnabled()) {
            showFinishingAlertDialog("BL is Disabled", "Please enable Bluetooth and restart App");
        } else if (!btAdapter.isMultipleAdvertisementSupported()) {
            showFinishingAlertDialog("Not supported", "BLE advertising not supported on this device");
        } else {
            BluetoothLeAdvertiser adv = btAdapter.getBluetoothLeAdvertiser();
            //TODO callback when someone scans
        }
        // BT LE

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        NotificationReceiver receiver = new NotificationReceiver(wifiManager);
        WIFIContent.getInstance(null).startScan();
        IntentFilter wifiIntentFilters = new IntentFilter();
        wifiIntentFilters.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(receiver, wifiIntentFilters);

        setContentView(R.layout.activity_main);
        RecyclerView txRecyclerView = (RecyclerView) findViewById(R.id.tx_recycler_view);
        txRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager txLayoutManager = new LinearLayoutManager(this);
        txRecyclerView.setLayoutManager(txLayoutManager);
        recyclerAdapter = new TxRecyclerApapterImpl();
        txRecyclerView.setAdapter(recyclerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(MainActivity.this, AddTxnActivity.class);
                MainActivity.this.startActivity(addCard);
            }
        });

        FloatingActionButton syncFab = (FloatingActionButton) findViewById(R.id.fabSyncButton);
        syncFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(MainActivity.this, QrCodeActivity.class);
                startActivity(addCard);
            }
        });
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    // Pops an AlertDialog that quits the app on OK.
    private void showFinishingAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    private DialogInterface dialogInterface;
                    private int i;

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        this.dialogInterface = dialogInterface;
                        this.i = i;
                        finish();
                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.settings:
                Intent settingsCard = new Intent(this, SettingsActivity.class);
                MainActivity.this.startActivity(settingsCard);
                return true;
        }
        return false;
    }

    /*Loader Methods */

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (LOADER_ID != i) {
            return null;
        }
        return new CursorLoader(MainActivity.this, DatabaseContract.CONTENT_URI,
                new String[]{DatabaseContract.TableTransactions.COL_DATE, DatabaseContract.TableTransactions.COL_AMOUNT,
                        DatabaseContract.TableTransactions.COL_PARTY, DatabaseContract.TableTransactions.COL_SUMMARY,
                        DatabaseContract.TableTransactions.COL_TYPE, DatabaseContract.TableTransactions.COL_ID}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        recyclerAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerAdapter.swapCursor(null);
    }
}

