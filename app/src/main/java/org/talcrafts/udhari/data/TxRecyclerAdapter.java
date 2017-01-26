package org.talcrafts.udhari.data;

import android.support.v7.widget.RecyclerView;

/**
 * Base class for the RecyclerView adapter to display flashcards.
 */
public abstract class TxRecyclerAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    /**
     * Return a {@link Transaction} represented by this item in the adapter.
     *
     * @param position Adapter item position
     * @return A new {@link Transaction} filled with this position's attributes.
     * @throws IllegalArgumentException if position is out of the adapter's bounds.
     */
    public abstract Transaction getItem(int position);

}
