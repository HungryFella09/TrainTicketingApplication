package repositories;


import domain.Entity;
import exceptions.RepositoryException;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {

    E findOne(ID id);

    List<E> findAll();

    E save(E entity);

    void delete(E entity);

    E update(E entity);
}


