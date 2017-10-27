package ua.nure.fomenko.final_project.transaction;

import java.sql.Connection;

/**
 * Created by fomenko on 25.08.2017.
 */
public class ThreadLockHandler {

    private static final ThreadLocal<Connection> CONNECTION_POOL = new ThreadLocal<Connection>();

    public static void setConnection(Connection connection) {
        CONNECTION_POOL.set(connection);
    }

    public static Connection getConnection(){
        return CONNECTION_POOL.get();
    }

}
