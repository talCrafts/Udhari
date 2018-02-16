package org.talcrafts.udhari.mapper;

/**
 * Created by sushma on 17/2/18.
 */

public interface Mapper<E, D> {
    D mapFromEntity(E entity);

    E mapToEntity(D data);
}
