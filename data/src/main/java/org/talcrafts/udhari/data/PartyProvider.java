package org.talcrafts.udhari.data;

import org.talcrafts.udhari.domain.PartyEntity;
import org.talcrafts.udhari.domain.interactor.GetAllParties;

import java.util.List;

/**
 * Created by sushma on 2/3/18.
 */

public class PartyProvider implements GetAllParties {

    //TODO get Party from DB

    @Override
    public List<PartyEntity> getAllParties() {
        return null;
    }
}
