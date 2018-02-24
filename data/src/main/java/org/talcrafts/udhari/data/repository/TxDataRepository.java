package org.talcrafts.udhari.data.repository;

import org.talcrafts.udhari.domain.TransactionEntity;
import org.talcrafts.udhari.domain.repository.TxnRepository;

import java.util.List;


/**
 * Created by sushma on 24/2/18.
 */

public class TxDataRepository implements TxnRepository {
    @Override
    public List<TransactionEntity> txns() {
        return null;
    }

    @Override
    public TransactionEntity txn() {
        return;
    }
}
