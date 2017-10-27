package ua.nure.fomenko.final_project.transaction;

import java.sql.SQLException;

/**
 * Created by fomenko on 25.08.2017.
 */
public interface Transaction<T> {

    T execute() throws SQLException;
}
