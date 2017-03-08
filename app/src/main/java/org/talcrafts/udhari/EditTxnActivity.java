package org.talcrafts.udhari;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import org.talcrafts.udhari.data.Transaction;

public class EditTxnActivity extends AppCompatActivity {

    public static final String SELECTED_TRANSACTION = "SELECTED_TRANSACTION";

    private TextInputEditText mTxnDate;
    private TextInputEditText mTxnAmount;
    private TextInputEditText mParty;
    private TextInputEditText mSummary;
    private Spinner mType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_txn);
        Transaction card = getIntent().getParcelableExtra(SELECTED_TRANSACTION);
        if (null != card) {
            mTxnDate = (TextInputEditText) findViewById(R.id.text_date);
            mTxnDate.setText(card.date);
            mTxnAmount = (TextInputEditText) findViewById(R.id.amount);
            mTxnAmount.setText(card.amount);
            mParty = (TextInputEditText) findViewById(R.id.party);
            mParty.setText(card.party);
            mSummary = (TextInputEditText) findViewById(R.id.summary);
            mSummary.setText(card.summary);
            mType = (Spinner) findViewById(R.id.dropdown_type);
            mType.setSelection(TxnType.valueOf(card.txnType.toUpperCase()).ordinal());
        }
    }

}
