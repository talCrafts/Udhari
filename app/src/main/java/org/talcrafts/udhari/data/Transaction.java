package org.talcrafts.udhari.data;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {

    public static final String KEY_TRANSACTIONS = "transactions";
    public static final String KEY_DATE = "date";
    public static final String KEY_AMOUNT = "amount";

    public final String date;
    public final String amount;
    public final String currency = "RS:";

    /**
     * Create a new Transaction from discrete values
     */
    public Transaction(String date, String amount) {
        this.date = date;
        this.amount = amount;
    }

    /**
     * Create a new Transaction from a database Cursor
     */
    public Transaction(Cursor cursor) {
        int dateColumnNo = cursor.getColumnIndex(DatabaseContract.TableTransactions.COL_DATE);
        int amountColumnNo = cursor.getColumnIndex(DatabaseContract.TableTransactions.COL_AMOUNT);
        this.date = cursor.getString(dateColumnNo);
        this.amount = cursor.getString(amountColumnNo);
    }

    /**
     * Create a new Transaction from a data Parcel
     */
    protected Transaction(Parcel in) {
        this.date = in.readString();
        this.amount = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(amount);
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getDisplayString() {
        return this.date + " " + this.currency + " " + this.amount;
    }
}
