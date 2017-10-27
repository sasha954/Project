package ua.nure.fomenko.final_project.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.MessageKeys;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Path;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.dto.UserDto;
import ua.nure.fomenko.final_project.db.entity.dto.UserDtoMapper;
import ua.nure.fomenko.final_project.exception.AppException;
import ua.nure.fomenko.final_project.services.RegistrationService;
import ua.nure.fomenko.final_project.services.UserService;
import ua.nure.fomenko.final_project.util.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fomenko on 09.09.2017.
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration.do")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = Path.REGISTRATION_PAGE;
        prepareRequestParam(req);
        LOGGER.trace("Forwarding to: " + path);
        req.getRequestDispatcher(path).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = new UserDtoMapper().getUser(req);
        Map<String, String> errors = new ValidateUtil().validateRegistration(userDto);

        if (errors.isEmpty()) {
            RegistrationService registrationService = new RegistrationService();
            User regUser = registrationService.getUserFromDto(userDto);
            UserService userService = (UserService) req.getServletContext().getAttribute(Params.USER_SERVICE);
            if (userService.isUserExistByEmail(regUser.getEmail())) {
                errors.put(Params.USER_EMAIL, MessageKeys.USER_EMAIL_ALREADY_EXIST);
                setUserDtoInSession(userDto, req);
                registrationError(errors, req, resp);
            } else {
                try {
                    int userId = userService.createUser(regUser);
                    if (userId != 0) {
                        Map<String, String> messages = new HashMap<>();
                        messages.put(Params.REGISTRATION_SUCCESS, MessageKeys.REGISTRATION_SUCCESS);
                        req.getSession().setAttribute(Params.SESSION_MESSAGE, messages);
                    }
                    registrationSuccess(req, resp);
                } catch (AppException e) {
                    errors.put(Params.CREATE_USER_ERROR, MessageKeys.CANNOT_CREATE_USER);
                    registrationError(errors, req, resp);
                }
            }
        } else {
            setUserDtoInSession(userDto, req);
            registrationError(errors, req, resp);
        }
    }

    private void setUserDtoInSession(UserDto userDto, HttpServletRequest request) {
        userDto.setPassword(null);
        userDto.setRepeatPassword(null);
        request.getSession().setAttribute(Params.SESSION_USER_FILL_FIELDS, userDto);
    }

    private void registrationError(Map<String, String> err, HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.debug("Registration ERROR");
        request.getSession().setAttribute(Params.SESSION_ERRORS, err);
        String path = Path.REGISTRATION_SERVLET_URI;
        LOGGER.trace("Forwarding to: " + path);
        response.sendRedirect(path);
    }

    private void registrationSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO redirect to success registration page
        String path = Path.INDEX_SERVLET_URI;
        LOGGER.trace("Forwarding to: " + path);
        response.sendRedirect(path);
    }

    private void prepareRequestParam(HttpServletRequest req) {
        LOGGER.trace("Prepare param for page");
        HttpSession session = req.getSession();
        Object errorMap = session.getAttribute(Params.SESSION_ERRORS);
        Object userDto = session.getAttribute(Params.SESSION_USER_FILL_FIELDS);

        req.setAttribute(Params.REGISTRATION_ERRORS, errorMap);
        req.setAttribute(Params.USER_DTO_FIELDS, userDto);

        req.getSession().removeAttribute(Params.SESSION_ERRORS);
        req.getSession().removeAttribute(Params.SESSION_USER_FILL_FIELDS);
        LOGGER.debug("Parameters was prepared");
    }
}
