package org.talcrafts.udhari;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.zxing.qrcode.encoder.QRCode;

import org.talcrafts.udhari.R;
import org.talcrafts.udhari.neighbours.model.WIFIContent;

public class NeighbourActivity extends AppCompatActivity {

    private NeighboursRecyclerApapterImpl recyclerAdapter;
    private static final int NEIGHBOUR_LOADER_ID=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour);
        RecyclerView neighbourRecyclerView = (RecyclerView) findViewById(R.id.neighbours_list);
        neighbourRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager txLayoutManager = new LinearLayoutManager(this);
        neighbourRecyclerView.setLayoutManager(txLayoutManager);
        recyclerAdapter = new NeighboursRecyclerApapterImpl(WIFIContent.getInstance(null));
        neighbourRecyclerView.setAdapter(recyclerAdapter);

//        Intent addCard = new Intent(NeighbourActivity.this, QrCodeActivity.class);
//        //addCard.putExtra("data", "Item Details:" + date + ":Amount:" + amount);
//        startActivity(addCard);

    }
}
