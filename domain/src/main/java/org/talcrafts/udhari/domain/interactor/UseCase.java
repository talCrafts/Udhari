package org.talcrafts.udhari.domain.interactor;

/**
 * Created by sushma on 24/2/18.
 */

public abstract class UseCase<Params> {
    public abstract void execute(Params params);
}
