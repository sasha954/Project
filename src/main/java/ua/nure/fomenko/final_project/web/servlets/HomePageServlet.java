package ua.nure.fomenko.final_project.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Path;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.bean.Complaint;
import ua.nure.fomenko.final_project.services.ComplaintService;
import ua.nure.fomenko.final_project.services.FacultyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fomenko on 09.09.2017.
 */
@WebServlet(name = "HomePageServlet", urlPatterns = "/index.do")
public class HomePageServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HomePageServlet.class);
    private FacultyService facultyService;
    private ComplaintService complaintService;

    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService) getServletContext().getAttribute(Params.FACULTY_SERVICE);
        complaintService = (ComplaintService) getServletContext().getAttribute(Params.COMPLAINT_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareRequestParam(req);
        String path = Path.INDEX_PAGE;
        LOGGER.trace("Forwarding to: " + path);
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private void prepareRequestParam(HttpServletRequest request) {
        User sessionUser = (User) request.getSession().getAttribute(Params.SESSION_USER);
        if (sessionUser != null) {
            List<Faculty> regFaculty = new ArrayList<>();
            for (Complaint complaint : complaintService.getComplainsByUser(sessionUser)) {
                regFaculty.add(complaint.getFaculty());
            }

            request.setAttribute(Params.REQ_FACULTY_LIST_BY_USER, regFaculty);
        }
        List<Faculty> facultyList = null;
        if (request.getSession().getAttribute("sortedFaculties") != null) {
            facultyList = (List<Faculty>) request.getSession().getAttribute("sortedFaculties");
            request.getSession().removeAttribute("sortedFaculties");
        } else {
            facultyList = facultyService.getAllFaculty();
        }

        request.setAttribute(Params.REQ_FACULTY_LIST, facultyList);

    }
}
