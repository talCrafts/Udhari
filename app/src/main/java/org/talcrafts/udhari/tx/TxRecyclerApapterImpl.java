package org.talcrafts.udhari.tx;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.talcrafts.udhari.R;
import org.talcrafts.udhari.data.CommonRecyclerAdapter;
import org.talcrafts.udhari.data.Transaction;

/**
 * Created by ashwaghm on 20-Jan-17.
 */

public class TxRecyclerApapterImpl extends CommonRecyclerAdapter<Transaction, TxRecyclerApapterImpl.CardViewHolder> {

    private Cursor mCursor;

    @Override
    public Transaction getItem(int position) {
        if (mCursor == null) {
            return null;
        }
        mCursor.moveToPosition(position);
        return new Transaction(mCursor);
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
        holder.mDate.setText(transaction.getAmountStr());
        holder.mParty.setText(transaction.getPartyString());
        holder.mSummary.setText(transaction.getSummaryString());
        String string = transaction.getTypeString();

        if (string.equalsIgnoreCase(TxnType.LENT.toString())) {
            holder.mDate.setTextColor(Color.parseColor("Green"));
            holder.mType.setTextColor(Color.parseColor("Green"));
            holder.mType.setText("You lent");
        } else {
            holder.mDate.setTextColor(Color.parseColor("Red"));
            holder.mType.setTextColor(Color.parseColor("Red"));
            holder.mType.setText("You Borrowed");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent launchAnswer = new Intent(context, AddTxnActivity.class);
                launchAnswer.putExtra(AddTxnActivity.SELECTED_TRANSACTION, transaction);
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
        public final TextView mDate;
        public final TextView mParty;
        public final TextView mSummary;
        public final TextView mType;

        public CardViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mDate = (TextView) itemView.findViewById(R.id.text_date);
            mParty = (TextView) itemView.findViewById(R.id.party_id);
            mSummary = (TextView) itemView.findViewById(R.id.summary);
            mType = (TextView) itemView.findViewById(R.id.type_of_transaction);
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
