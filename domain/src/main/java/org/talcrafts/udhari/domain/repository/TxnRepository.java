package org.talcrafts.udhari.domain.repository;

import org.talcrafts.udhari.domain.TransactionEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by sushma on 24/2/18.
 */

public interface TxnRepository {
    public Observable<List<TransactionEntity>> txns();

    public Observable<TransactionEntity> txn();
}
