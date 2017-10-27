package ua.nure.fomenko.final_project.services;

import ua.nure.fomenko.final_project.db.entity.User;


public interface UserService {
    User getUserById(int id);

    int createUser(User user);

    boolean isUserExistByEmail(String email);

    boolean isAuthorize(User user);

    User getUserByEmail(String email);

    boolean switchBlockStatus(int id, boolean status);
}
