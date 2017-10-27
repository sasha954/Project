package ua.nure.fomenko.final_project.web.servlets;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.exception.AppException;
import ua.nure.fomenko.final_project.services.FacultyService;
import ua.nure.fomenko.final_project.services.SubjectService;
import ua.nure.fomenko.final_project.util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fomenko on 21.09.2017.
 */
@WebServlet("/updateFaculty.do")
public class UpdateFacultyServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UpdateFacultyServlet.class);

    private FacultyService facultyService;
    private SubjectService subjectService;

    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService) getServletContext().getAttribute(Params.FACULTY_SERVICE);
        subjectService = (SubjectService) getServletContext().getAttribute(Params.SUBJECT_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("faculty-id"));
        Faculty faculty = facultyService.getFacultyById(id);
        String jsonObj = JsonConverter.objectToJson(faculty);
        resp.getWriter().write(jsonObj);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Faculty faculty = parseFacultyFromRequest(req);
        boolean result = facultyService.updateFaculty(faculty);
        if(result == true) {
            String json = facultyAndMessageToJson(faculty, Messages.FACULTY_UPDATE_SUCCESS);
            resp.getWriter().write(json);
        } else {
            resp.getWriter().write(Messages.ERR_CANNOT_UPDATE_FACULTY);
        }
    }

    private Faculty parseFacultyFromRequest(HttpServletRequest request) {
        Faculty faculty = new Faculty();
        faculty.setId(Integer.parseInt(request.getParameter(Params.ID)));
        faculty.setName(request.getParameter(Params.FACULTY_NAME));
        faculty.setCountStateFundedPlace(Integer.parseInt(request.getParameter(Params.FACULTY_SFP)));
        faculty.setAllPlace(Integer.parseInt(request.getParameter(Params.FACULTY_ALL_PLACE)));
        return faculty;
    }

    private String facultyAndMessageToJson(Faculty faculty, String message) {
        JSONObject object = new JSONObject(faculty);
        object.put("message", message);
        return object.toString();
    }

}
