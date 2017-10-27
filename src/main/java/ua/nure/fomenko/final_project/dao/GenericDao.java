package ua.nure.fomenko.final_project.dao;

import ua.nure.fomenko.final_project.db.entity.Entity;

/**
 * Created by fomenko on 25.08.2017.
 */
public interface GenericDao <T extends Entity> {

    T get(int id);

    int create(T entity);

    boolean update(T entity);

    boolean delete(T entity);

}
