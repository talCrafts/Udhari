package org.talcrafts.udhari;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.talcrafts.udhari.data.AddTxService;
import org.talcrafts.udhari.data.DatabaseContract;
import org.talcrafts.udhari.data.Transaction;

import java.util.Calendar;

public class AddTxnActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private TextInputEditText mTxnDate;
    private TextInputEditText mTxnAmount;
    private TextInputEditText mParty;
    private TextInputEditText mSummary;
    private Spinner mType;
    private long mDueDate = Long.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_txn);

        mTxnDate = (TextInputEditText) findViewById(R.id.text_date);
        mTxnDate.setOnClickListener(this);
        mTxnAmount = (TextInputEditText) findViewById(R.id.amount);
        mParty = (TextInputEditText) findViewById(R.id.party);
        mSummary = (TextInputEditText) findViewById(R.id.summary);
        mType = (Spinner) findViewById(R.id.dropdown_type);
        mTxnAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        String date = mTxnDate.getText().toString().trim();

        //TODO  get string from user and move it resolve date picker

        String amount = mTxnAmount.getText().toString().trim();
        //TODO numbers are getting


        if (date.length() == 0) {
            mTxnDate.setError(getString(R.string.error_input_question));
        }

        if (amount.length() == 0) {
            mTxnAmount.setError(getString(R.string.error_input_answer));
        }

        String party = mParty.getText().toString().trim();
        String summary = mSummary.getText().toString().trim();
        String type = mType.getSelectedItem().toString();
        if (date.length() > 0 && amount.length() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.TableTransactions.COL_DATE, date); // bug
            contentValues.put(DatabaseContract.TableTransactions.COL_AMOUNT, amount); // bug
            contentValues.put(DatabaseContract.TableTransactions.COL_PARTY, party);
            contentValues.put(DatabaseContract.TableTransactions.COL_SUMMARY, summary);
            contentValues.put(DatabaseContract.TableTransactions.COL_TYPE, type);

            AddTxService.insertNewCard(getApplicationContext(), contentValues);
            //Uri uri = getContentResolver().insert(DatabaseContract.CONTENT_URI, contentValues);
//            if (uri == null) {
//                Toast.makeText(this, R.string.error_adding_card, Toast.LENGTH_LONG).show();
//            } else {
            finish();
//            }
            Intent addCard = new Intent(AddTxnActivity.this, QrCodeActivity.class);
            addCard.putExtra("data", "Item Details:" + date + ":Amount:" + amount);
            startActivity(addCard);
        } else {
            Snackbar.make(findViewById(R.id.party_id), getString(R.string.complete_form)
                    , Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        DatePickerFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //Set to noon on the selected day
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        setDateSelection(c.getTimeInMillis());
    }

    /* Manage the selected date value */
    public void setDateSelection(long selectedTimestamp) {
        mDueDate = selectedTimestamp;
        updateDateDisplay();
    }

    private void updateDateDisplay() {
        if (getDateSelection() == Long.MAX_VALUE) {
            mTxnDate.setText(getString(R.string.date_empty));
        } else {
            CharSequence formatted = Transaction.dateFormat.format(mDueDate);
            String format = DateFormat.getDateFormat(getBaseContext()).format(mDueDate);
            mTxnDate.setText(format);
        }
    }

    public long getDateSelection() {
        return mDueDate;
    }
}
