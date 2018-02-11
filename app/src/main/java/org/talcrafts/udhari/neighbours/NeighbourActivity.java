package org.talcrafts.udhari.neighbours;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.talcrafts.udhari.R;
import org.talcrafts.udhari.wifi.WIFIContent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourActivity extends AppCompatActivity {

    private NeighboursRecyclerApapterImpl recyclerAdapter;

    @BindView(R.id.neighbours_list)
    RecyclerView neighbourRecyclerView;
    private static final int NEIGHBOUR_LOADER_ID=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour);
        ButterKnife.bind(this);
        neighbourRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager txLayoutManager = new LinearLayoutManager(this);
        neighbourRecyclerView.setLayoutManager(txLayoutManager);
        recyclerAdapter = new NeighboursRecyclerApapterImpl(WIFIContent.getInstance(null));
        neighbourRecyclerView.setAdapter(recyclerAdapter);
    }
}
