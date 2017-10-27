package ua.nure.fomenko.final_project.web.servlets;

import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Path;
import ua.nure.fomenko.final_project.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fomenko on 24.09.2017.
 */
@WebServlet(urlPatterns = "/SwitchBlockStatus.do")
public class BlockSwitcherServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(Params.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String status = req.getParameter("blockStatus");
        if ((userId != null && !userId.isEmpty()) && (status != null && !status.isEmpty())) {
            int uId = Integer.parseInt(userId);
            boolean blockStatus = Boolean.parseBoolean(status);
            userService.switchBlockStatus(uId, blockStatus);
        }
        resp.sendRedirect(Path.PERSONAL_CABINET_SERVLET_URI+"?category=abiturients&tag=request");
    }


}
