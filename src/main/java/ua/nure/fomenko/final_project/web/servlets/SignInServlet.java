package ua.nure.fomenko.final_project.web.servlets;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ua.nure.fomenko.final_project.constants.MessageKeys;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Path;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.services.LoginService;
import ua.nure.fomenko.final_project.services.UserService;
import ua.nure.fomenko.final_project.util.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by fomenko on 15.09.2017.
 */
@WebServlet("/signIn.do")
public class SignInServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SignInServlet.class);
    private UserService userService;
    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(Params.USER_SERVICE);
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareRequestParam(req, resp);
        String path = Path.LOGIN_PAGE;
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private void prepareRequestParam(HttpServletRequest req, HttpServletResponse resp) {
        Object errors = req.getSession().getAttribute(Params.SESSION_ERRORS);

        req.setAttribute(Params.AUTHORIZATION_ERROR, errors);

        req.getSession().removeAttribute(Params.SESSION_ERRORS);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User authUser = loginService.getUserFromRequest(req);
        Map<String, String> errors = new ValidateUtil().validateAuthorization(authUser);

        if (errors.isEmpty()) {
            if (userService.isAuthorize(authUser)) {
                authUser = userService.getUserByEmail(authUser.getEmail());
                authUser.setPassword(null);
                req.getSession().setAttribute(Params.SESSION_USER, authUser);
                LOGGER.debug("Authorization SUCCESS");
                String path = Path.INDEX_SERVLET_URI;
                /*JSONObject urlRedirect = new JSONObject();
                urlRedirect.put("url", path);
                resp.getWriter().write(urlRedirect.toString());*/
                resp.sendRedirect(path);
            } else {

                errors.put(Params.AUTHORIZATION_ERROR, MessageKeys.WRONG_AUTHORIZATION);
                //writeJsonError(errors, resp);
                authorizationError(errors, req, resp);
            }
        } else {
            errors.put(Params.AUTHORIZATION_ERROR, MessageKeys.WRONG_AUTHORIZATION);
            //writeJsonError(errors, resp);
            authorizationError(errors, req, resp);
        }
    }

    private void authorizationError(Map<String, String> errors, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.debug("Authorization ERROR");
        req.getSession().setAttribute(Params.SESSION_ERRORS, errors);
        String path = Path.LOGIN_SERVLET_URI;
        LOGGER.trace("Forwarding to: " + path);
        resp.sendRedirect(path);
    }


    private void writeJsonError(Map<String, String> errors, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject(errors);
        response.setContentType("json");
        response.setStatus(400);
        response.getWriter().write(jsonObject.toString());

    }
}
