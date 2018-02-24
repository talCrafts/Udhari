package org.talcrafts.udhari.repository;

import org.talcrafts.udhari.domain.TransactionEntity;

import java.util.List;

/**
 * Created by sushma on 24/2/18.
 */

public interface TxnRepository {
    public List<TransactionEntity> txns();

    public TransactionEntity txn();
}
