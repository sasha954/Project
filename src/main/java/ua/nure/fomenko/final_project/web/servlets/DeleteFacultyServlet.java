package ua.nure.fomenko.final_project.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.services.FacultyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fomenko on 20.09.2017.
 */
@WebServlet("/deleteFaculty.do")
public class DeleteFacultyServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DeleteFacultyServlet.class);

    private FacultyService facultyService;

    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService)getServletContext().getAttribute(Params.FACULTY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int facultyId = Integer.parseInt(request.getParameter("faculty-id"));
        Faculty faculty = facultyService.getFacultyById(facultyId);
        boolean result = facultyService.removeFaculty(faculty);
        if(result == true) {
            response.getWriter().write(Messages.FACULTY_DELETE_SUCCESS);
        } else  {
            response.getWriter().write(Messages.ERR_CANNOT_DELETE_FACULTY);
        }
    }
}
