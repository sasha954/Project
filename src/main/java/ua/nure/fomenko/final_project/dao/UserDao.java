package ua.nure.fomenko.final_project.dao;

import ua.nure.fomenko.final_project.db.entity.User;

/**
 * Created by fomenko on 07.09.2017.
 */
public interface UserDao extends GenericDao<User> {

    boolean isUserExistByEmail(String email);

    User getUserByEmail(String email);

    boolean isUserExistByEmailAndPassword(User user);

    boolean switchBlockStatus(int id, boolean status);
}
