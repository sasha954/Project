package ua.nure.fomenko.final_project.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.MessageKeys;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.SQLQueries;
import ua.nure.fomenko.final_project.dao.RoleDao;
import ua.nure.fomenko.final_project.dao.UserDao;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.transaction.ThreadLockHandler;

import java.sql.*;

/**
 * Created by fomenko on 07.09.2017.
 */
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User get(int id) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_GET_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = parseUser(resultSet);
            }
            LOGGER.debug(Messages.USER_OBTAINED);
            resultSet.close();
            return user;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_USER, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER, e);
        }
    }

    @Override
    public int create(User entity) {
        LOGGER.debug(Messages.USER_START_CREATING);
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        int resultId = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_CREATE_NEW,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getEmail());
            statement.setInt(2, entity.getRole().getId());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getFirstName());
            statement.setString(5, entity.getLastName());
            statement.setString(6, entity.getMiddleName());
            statement.setString(7, entity.getRegion());
            statement.setString(8, entity.getCity());
            statement.setString(9, entity.getSchool());
            statement.setString(10, entity.getCertificateUrl());
            statement.setBoolean(11, entity.isBlock());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                resultId = resultSet.getInt(1);
            }
            resultSet.close();
            LOGGER.debug(Messages.USER_CREATE_SUCCESS);
            return resultId;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_CREATE_USER, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_USER, e);
        }
    }

    @Override
    public boolean update(User entity) {
        Connection connection = ThreadLockHandler.getConnection();
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_UPDATE,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setString(5, entity.getMiddleName());
            statement.setString(6, entity.getRegion());
            statement.setString(7, entity.getCity());
            statement.setString(8, entity.getSchool());
            statement.setString(9, entity.getCertificateUrl());
            statement.setBoolean(10, entity.isBlock());
            statement.setInt(11, entity.getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                result = true;
            }
            LOGGER.debug(Messages.USER_UPDATE_SUCCESS);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_UPDATE_USER, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, e);
        }
    }

    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        Connection connection = ThreadLockHandler.getConnection();
         try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_GET_BY_EMAIL)){
             statement.setString(1, email);
             ResultSet resultSet = statement.executeQuery();
             boolean result = resultSet.next();
             resultSet.close();
             return result;
         } catch (SQLException e) {
             LOGGER.error(Messages.ERR_CANNOT_CHECK_USER, e);
             throw new DBException(Messages.ERR_CANNOT_CHECK_USER, e);
         }
    }

    @Override
    public User getUserByEmail(String email) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_GET_BY_EMAIL)) {
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = parseUser(resultSet);
            }
            LOGGER.debug(Messages.USER_OBTAINED);
            resultSet.close();
            return user;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER);
        }
    }

    @Override
    public boolean isUserExistByEmailAndPassword(User user) {
        Connection connection = ThreadLockHandler.getConnection();

        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.USER_GET_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.next();
            resultSet.close();
            return result;
        } catch (SQLException e) {
           LOGGER.error(Messages.ERR_CANNOT_CHECK_USER, e);
           throw new DBException(Messages.ERR_CANNOT_CHECK_USER, e);
        }
    }

    @Override
    public boolean switchBlockStatus(int id, boolean status) {
        Connection connection = ThreadLockHandler.getConnection();
        //TODO insert sql query
        try(PreparedStatement statement = connection.prepareStatement("UPDATE users SET is_block=? WHERE id=?")) {
            statement.setBoolean(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
            return false;
        } catch (SQLException e) {
            throw new DBException("Cannot switch block status");
        }
    }

    private User parseUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        RoleDao roleDao = new RoleDaoImpl();
        user.setId(resultSet.getInt(Params.ID));
        user.setEmail(resultSet.getString(Params.USER_EMAIL));
        user.setRole(roleDao.get(resultSet.getInt(Params.USER_ROLE)));
        user.setPassword(resultSet.getString(Params.USER_PASSWORD));
        user.setFirstName(resultSet.getString(Params.USER_FIRST_NAME));
        user.setLastName(resultSet.getString(Params.USER_LAST_NAME));
        user.setMiddleName(resultSet.getString(Params.USER_MIDDLE_NAME));
        user.setRegion(resultSet.getString(Params.USER_REGION));
        user.setCity(resultSet.getString(Params.USER_CITY));
        user.setSchool(resultSet.getString(Params.USER_SCHOOL));
        user.setCertificateUrl(resultSet.getString(Params.USER_CERTIFICATE));
        user.setBlock(Boolean.parseBoolean(resultSet.getString(Params.USER_IS_BLOCK)));
        return user;
    }
}
