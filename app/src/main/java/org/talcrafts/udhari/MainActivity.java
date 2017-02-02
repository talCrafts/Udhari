package org.talcrafts.udhari;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 0;
    private TxRecyclerApapterImpl recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView cardRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        cardRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(this);
        cardRecyclerView.setLayoutManager(cardLayoutManager);
        recyclerAdapter = new TxRecyclerApapterImpl();
        cardRecyclerView.setAdapter(recyclerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(MainActivity.this, AddCardActivity.class);
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
                new String[]{DatabaseContract.TableTransactions.COL_DATE, DatabaseContract.TableTransactions.COL_AMOUNT}, null, null, null);
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

