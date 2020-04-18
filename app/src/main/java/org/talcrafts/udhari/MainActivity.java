package org.talcrafts.udhari;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.talcrafts.udhari.bt.BTCheck;
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

        BTCheck.Message finalMessage = new BTCheck.Message("Not Required", "Skipping for Now");
        //BTCheck.Message finalMessage = BTCheck.check(getApplicationContext());
        showFinishingAlertDialog(finalMessage.title, finalMessage.message);

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

