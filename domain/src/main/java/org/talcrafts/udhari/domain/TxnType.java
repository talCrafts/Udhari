package org.talcrafts.udhari.domain;

/**
 * Created by ubuntu on 26/2/17.
 */
public enum TxnType {

    LENT("Lent"),
    BORROW("Borrow");

    private final String mString;

    TxnType(String type) {
        this.mString = type;
    }

    @Override
    public String toString() {
        return mString;
    }
}
