package org.talcrafts.udhari.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class TxProvider extends ContentProvider {
    private static final String TAG = TxProvider.class.getSimpleName();

    private static final int TRANSACTIONS = 200;
    private static final int TRANSACTIONS_WITH_ID = 201;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_TRANSACTIONS,
                TRANSACTIONS);

        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_TRANSACTIONS + "/#",
                TRANSACTIONS_WITH_ID);
    }

    private TxDBHelper mCardsDBHelper;

    @Override
    public boolean onCreate() {
        mCardsDBHelper = new TxDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mCardsDBHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;
        switch (match) {
            case TRANSACTIONS:
                cursor = db.query(DatabaseContract.TABLE_TRANSACTIONS, projection, selection, selectionArgs, null, null, sortOrder);

                break;
            case TRANSACTIONS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String oneSelection = DatabaseContract.TableTransactions.COL_ID + "=?";
                String[] oneSelectionArgs = new String[]{id};
                cursor = db.query(DatabaseContract.TABLE_TRANSACTIONS, projection, oneSelection, oneSelectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("This URI is not supported");
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mCardsDBHelper.getWritableDatabase();

        Uri returnUri = null;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case TRANSACTIONS: // Since insert and query all has same url
                long transactionId = db.insert(DatabaseContract.TABLE_TRANSACTIONS, null, values);
                if (transactionId > 0) {
                    returnUri = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, transactionId);
                    getContext().getContentResolver().notifyChange(uri, null);
                } else {
                    throw new SQLException("Can't create ID");
                }
                break;
            default:
                throw new UnsupportedOperationException("This URI is not supported");
        }
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("This provider does not support deletion");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mCardsDBHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri, null);
        return db.update(DatabaseContract.TABLE_TRANSACTIONS, values, selection, selectionArgs);
    }
}
