package ua.nure.fomenko.final_project.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.SQLQueries;
import ua.nure.fomenko.final_project.dao.RoleDao;
import ua.nure.fomenko.final_project.db.entity.Role;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.transaction.ThreadLockHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fomenko on 07.09.2017.
 */
public class RoleDaoImpl implements RoleDao {
    private static final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class);

    @Override
    public Role get(int id) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        Role role = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.ROLE_GET_BY_ID)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                role = parseRole(resultSet);
            }
            LOGGER.debug(Messages.ROLE_OBTAINED);
            resultSet.close();
            return role;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_ROLE, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ROLE, e);
        }
    }

    private Role parseRole(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getInt(Params.ID));
        role.setName(resultSet.getString(Params.ROLE_NAME));
        return role;
    }

    @Override
    public int create(Role entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Role entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Role entity) {
        throw new UnsupportedOperationException();
    }
}
