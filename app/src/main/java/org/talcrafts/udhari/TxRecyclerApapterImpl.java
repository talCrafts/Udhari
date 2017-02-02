package org.talcrafts.udhari;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.talcrafts.udhari.data.TxRecyclerAdapter;
import org.talcrafts.udhari.data.Transaction;

/**
 * Created by ashwaghm on 20-Jan-17.
 */

public class TxRecyclerApapterImpl extends TxRecyclerAdapter<TxRecyclerApapterImpl.CardViewHolder> {

    private Cursor mCursor;

    @Override
    public Transaction getItem(int position) {
        if (mCursor == null) {
            return null;
        }
        mCursor.moveToPosition(position);
        final Transaction transaction = new Transaction(mCursor);
        return transaction;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tx_list_content, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        final Transaction transaction = getItem(position);
        holder.textView.setText(transaction.getDisplayString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent launchAnswer = new Intent(context, AnswerActivity.class);
                launchAnswer.putExtra(AnswerActivity.SELECTED_TRANSACTION, transaction);
                context.startActivity(launchAnswer);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * Created by ashwaghm on 20-Jan-17.
     */

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Transaction item;
        public final TextView textView;

        public CardViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            textView = (TextView) itemView.findViewById(R.id.date);
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor temp = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
}
