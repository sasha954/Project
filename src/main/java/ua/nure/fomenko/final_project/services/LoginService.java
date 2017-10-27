package ua.nure.fomenko.final_project.services;

import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.db.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fomenko on 15.09.2017.
 */
public class LoginService {

    public User getUserFromRequest(HttpServletRequest request) {
        User user = new User();
        user.setEmail(request.getParameter(Params.USER_EMAIL));
        user.setPassword(request.getParameter(Params.USER_PASSWORD));
        return user;
    }
}
