package org.talcrafts.udhari.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class contains the public contract for the database and
 * content provider. No changes should be made to the code here.
 */
public class DatabaseContract {

    // Database schema information
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final class TableTransactions implements BaseColumns {
        public static final String COL_ID = "_id";
        public static final String COL_DATE = "date";
        public static final String COL_AMOUNT = "amount";
        public static final String COL_PARTY= "party";
        public static final String COL_SUMMARY = "summary";
        public static final String COL_TYPE = "type";
    }

    // Unique authority string for the content provider
    public static final String CONTENT_AUTHORITY = "org.talcrafts.udhari";
    // Default sort for query results
    public static final String DEFAULT_SORT_TRANSACTIONS = TableTransactions.COL_ID;

    // Base content Uri for accessing the provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_TRANSACTIONS)
            .build();
}
