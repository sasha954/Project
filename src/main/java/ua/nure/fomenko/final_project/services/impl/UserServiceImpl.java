package ua.nure.fomenko.final_project.services.impl;

import ua.nure.fomenko.final_project.constants.MessageKeys;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.dao.UserDao;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.exception.AppException;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.services.UserService;
import ua.nure.fomenko.final_project.transaction.Transaction;
import ua.nure.fomenko.final_project.transaction.TransactionManager;
import ua.nure.fomenko.final_project.util.Encryptor;

import java.sql.SQLException;

/**
 * Created by fomenko on 09.09.2017.
 */
public class UserServiceImpl implements UserService {
    private TransactionManager transactionManager;
    private UserDao userDao;

    public UserServiceImpl(TransactionManager transactionManager, UserDao userDao) {
        this.transactionManager = transactionManager;
        this.userDao = userDao;
    }

    @Override
    public User getUserById(int id) {
        return transactionManager.execute(new Transaction<User>() {
            @Override
            public User execute() {
                try {
                    return userDao.get(id);
                } catch (DBException e) {
                    throw new AppException("Cannot get user.");
                }
            }
        });
    }

    @Override
    public int createUser(User user) {
        return transactionManager.execute(new Transaction<Integer>() {
            @Override
            public Integer execute() {
                user.setPassword(new Encryptor().encryptPassword(user.getPassword()));
                try {
                    return userDao.create(user);
                } catch (DBException e){
                    throw new AppException(MessageKeys.CANNOT_CREATE_USER, e);
                }
            }
        });
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() {
                try {
                    return userDao.isUserExistByEmail(email);
                } catch (DBException e) {
                    throw new AppException("Not available");
                }
            }
        });
    }

    @Override
    public boolean isAuthorize(User user) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                user.setPassword(new Encryptor().encryptPassword(user.getPassword()));
                return userDao.isUserExistByEmailAndPassword(user);
            }
        });
    }

    @Override
    public User getUserByEmail(String email) {
        return transactionManager.execute(new Transaction<User>() {
            @Override
            public User execute() throws SQLException {
                return userDao.getUserByEmail(email);
            }
        });
    }

    @Override
    public boolean switchBlockStatus(int id, boolean status) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                return userDao.switchBlockStatus(id, status);
            }
        });
    }
}
