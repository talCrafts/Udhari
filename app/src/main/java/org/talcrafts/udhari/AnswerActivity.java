package org.talcrafts.udhari;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.talcrafts.udhari.data.Transaction;

public class AnswerActivity extends AppCompatActivity {

    public static final String SELECTED_TRANSACTION = "SELECTED_TRANSACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Transaction card = getIntent().getParcelableExtra(SELECTED_TRANSACTION);
        if (null != card) {
            TextView questionView = (TextView) findViewById(R.id.question);
            questionView.setText(card.date);
            TextView answerView = (TextView) findViewById(R.id.answer);
            answerView.setText(card.amount);
        }
    }

}
