package ua.nure.fomenko.final_project.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by fomenko on 16.09.2017.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = "/logout.do")
public class LogoutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        LOGGER.debug("Session was invalidated.");
        String path = Path.INDEX_SERVLET_URI;
        LOGGER.trace("Forwarding to: " + path);
        response.sendRedirect(path);
    }
}
