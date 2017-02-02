package org.talcrafts.udhari;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import org.talcrafts.udhari.data.AddTxService;
import org.talcrafts.udhari.data.DatabaseContract;

public class AddCardActivity extends AppCompatActivity {
    private TextInputEditText mTextInputQuestion;
    private TextInputEditText mTextInputAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        mTextInputQuestion = (TextInputEditText) findViewById(R.id.text_input_question);
        mTextInputAnswer = (TextInputEditText) findViewById(R.id.text_input_answer);

        mTextInputAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        String date = mTextInputQuestion.getText().toString().trim();

        //TODO  get string from user and move it resolve date picker

        String amount = mTextInputAnswer.getText().toString().trim();
        //TODO numbers are getting



        if (date.length() == 0) {
            mTextInputQuestion.setError(getString(R.string.error_input_question));
        }

        if (amount.length() == 0) {
            mTextInputAnswer.setError(getString(R.string.error_input_answer));
        }

        if (date.length() > 0 && amount.length() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.TableTransactions.COL_DATE, date); // bug
            contentValues.put(DatabaseContract.TableTransactions.COL_AMOUNT, amount); // bug

            AddTxService.insertNewCard(getApplicationContext(), contentValues);
            //Uri uri = getContentResolver().insert(DatabaseContract.CONTENT_URI, contentValues);
//            if (uri == null) {
//                Toast.makeText(this, R.string.error_adding_card, Toast.LENGTH_LONG).show();
//            } else {
            finish();
//            }
        } else {
            Snackbar.make(findViewById(R.id.date), getString(R.string.complete_form)
                    , Snackbar.LENGTH_SHORT).show();
        }
    }
}
