package org.talcrafts.udhari.data;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* Process DB insert on a background thread */
public class AddTxService extends IntentService {
    private static final String TAG = AddTxService.class.getSimpleName();

    public static final String ACTION_INSERT = TAG + ".INSERT";
    public static final String ACTION_UPDATE = TAG + ".UPDATE";

    public static final String EXTRA_VALUES = TAG + ".ContentValues";

    public static void insertNewTx(Context context, ContentValues values) {
        Intent intent = new Intent(context, AddTxService.class);
        intent.setAction(ACTION_INSERT);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }

    public static void updateNewTx(Context context, ContentValues values) {
        Intent intent = new Intent(context, AddTxService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_VALUES, values);
        context.startService(intent);
    }


    public AddTxService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ACTION_INSERT.equals(intent.getAction())) {
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performInsert(values);
        }
        if (ACTION_UPDATE.equals(intent.getAction())) {
            ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
            performUpdate(values);
        }
    }

    private void performInsert(ContentValues values) {
        if (getContentResolver().insert(DatabaseContract.CONTENT_URI, values) != null) {
            Log.d(TAG, "Inserted new Transaction");
        } else {
            Log.w(TAG, "Error inserting new Transaction");
        }
    }

    private void performUpdate(ContentValues values) {
        StringBuilder where = new StringBuilder();
        where.append(DatabaseContract.TableTransactions.COL_ID)
                .append(" = ?");
        String id = values.getAsString(DatabaseContract.TableTransactions.COL_ID);
        values.remove(DatabaseContract.TableTransactions.COL_ID);
        if (getContentResolver().update(DatabaseContract.CONTENT_URI, values, where.toString(), new String[]{id}) > 0) {
            Log.d(TAG, "Updated new Transaction");
        } else {
            Log.w(TAG, "Error inserting new Transaction");
        }
    }
}
