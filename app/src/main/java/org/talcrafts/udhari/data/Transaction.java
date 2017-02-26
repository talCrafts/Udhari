package org.talcrafts.udhari.data;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Transaction implements Parcelable {

    public static final String KEY_TRANSACTIONS = "transactions";
    public static final String KEY_DATE = "date";
    public static final String KEY_AMOUNT = "amount";
    public static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public final String date;
    public final String amount;
    public final String currency = "RS:";
    public final String party;
    public final String summary;
    public final String txnType;

    /**
     * Create a new Transaction from discrete values
     */
    public Transaction(String date, String amount, String party, String summary, String txnType) {
        this.date = date;
        this.amount = amount;
        this.party = party;
        this.summary = summary;
        this.txnType = txnType;
    }

    /**
     * Create a new Transaction from a database Cursor
     */
    public Transaction(Cursor cursor) {
        int dateColumnNo = cursor.getColumnIndex(DatabaseContract.TableTransactions.COL_DATE);
        int amountColumnNo = cursor.getColumnIndex(DatabaseContract.TableTransactions.COL_AMOUNT);
        int partyColNo = cursor.getColumnIndex(DatabaseContract.TableTransactions.COL_PARTY);
        int summaryColNo = cursor.getColumnIndex(DatabaseContract.TableTransactions.COL_SUMMARY);
        int typeColNo = cursor.getColumnIndex(DatabaseContract.TableTransactions.COL_TYPE);
        this.date = cursor.getString(dateColumnNo);
        this.amount = cursor.getString(amountColumnNo);
        this.party = cursor.getString(partyColNo);
        this.summary = cursor.getString(summaryColNo);
        this.txnType = cursor.getString(typeColNo);
    }

    /**
     * Create a new Transaction from a data Parcel
     */
    protected Transaction(Parcel in) {
        this.date = in.readString();
        this.amount = in.readString();
        this.party = in.readString();
        this.summary = in.readString();
        this.txnType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(amount);
        dest.writeString(party);
        dest.writeString(summary);
        dest.writeString(txnType);
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

    public String getDateString() {
        return this.date;
    }

    public String getPartyString() {
        return this.party;
    }

    public String getSummaryString() {
        return this.summary;
    }

    public String getAmountStr() {
        return this.amount;
    }

    public String getTypeString() {
        return this.txnType;
    }
}
