package org.talcrafts.udhari;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.talcrafts.udhari.data.CommonRecyclerAdapter;
import org.talcrafts.udhari.neighbours.model.WIFIContent;
import org.talcrafts.udhari.neighbours.model.WIFIEndPoint;

/**
 * Created by ashwaghm on 20-Jan-17.
 */

public class NeighboursRecyclerApapterImpl extends CommonRecyclerAdapter<WIFIEndPoint,NeighboursRecyclerApapterImpl.NeighbourViewHolder> {

    private WIFIContent content;

    public NeighboursRecyclerApapterImpl(WIFIContent content) {
        this.content=content;
    }

    @Override
    public WIFIEndPoint getItem(int position) {
        return content.getItem(position);
    }

    @Override
    public NeighbourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.neighbours_list_content, parent, false);
        return new NeighbourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NeighbourViewHolder holder, final int position) {
        final WIFIEndPoint endPoint = content.getItem(position);
        holder.mName.setText(endPoint.content);
        String string = endPoint.type;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent qrLauncher = new Intent(context, QrCodeActivity.class);
                qrLauncher.putExtra(QrCodeActivity.SELECTED_NEIGHBOUR, position);
                context.startActivity(qrLauncher);
            }
        });
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    /**
     * Created by ashwaghm on 20-Jan-17.
     */

    public class NeighbourViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public WIFIEndPoint item;
        public final TextView mName;
        public final TextView mId;

        public NeighbourViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mName = (TextView) itemView.findViewById(R.id.neighbour_name);
            mId = (TextView) itemView.findViewById(R.id.neighbour_id);
        }
    }

}
