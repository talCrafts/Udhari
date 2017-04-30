package org.talcrafts.udhari.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.talcrafts.udhari.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TxDBHelper extends SQLiteOpenHelper {
    private static final String TAG = TxDBHelper.class.getSimpleName();

    public static final String DB_NAME = "udhari1.db";
    public static final int DB_VERSION = 2;

    private static final String SQL_CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " +
            DatabaseContract.TABLE_TRANSACTIONS + " (" +
            DatabaseContract.TableTransactions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.TableTransactions.COL_DATE + " TEXT NOT NULL," +
            DatabaseContract.TableTransactions.COL_PARTY + " TEXT NOT NULL," +
            DatabaseContract.TableTransactions.COL_SUMMARY + " TEXT," +
            DatabaseContract.TableTransactions.COL_TYPE + " TEXT," + // TODO change to Date
            DatabaseContract.TableTransactions.COL_AMOUNT + " TEXT NOT NULL )"; // TODO change to BigInt

    private Resources mResources;

    public TxDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TRANSACTIONS);
        /*try {
            //readTransactionsFromResources(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TRANSACTIONS);
        onCreate(db);
    }

    private void readTransactionsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.transactions);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        //Parse resource into key/values
        JSONObject root = new JSONObject(builder.toString());
        JSONArray transactions = root.getJSONArray(Transaction.KEY_TRANSACTIONS);
        //Add each element to the database
        for (int i = 0; i < transactions.length(); i++) {
            JSONObject item = transactions.getJSONObject(i);
            ContentValues values = new ContentValues(2);

            values.put(DatabaseContract.TableTransactions.COL_DATE,
                    item.getString(Transaction.KEY_DATE));

            values.put(DatabaseContract.TableTransactions.COL_AMOUNT,
                    item.getString(Transaction.KEY_AMOUNT));

            db.insert(DatabaseContract.TABLE_TRANSACTIONS, null, values);
        }
    }
}
