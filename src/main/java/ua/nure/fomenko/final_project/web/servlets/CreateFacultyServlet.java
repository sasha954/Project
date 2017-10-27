package ua.nure.fomenko.final_project.web.servlets;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.exception.AppException;
import ua.nure.fomenko.final_project.services.FacultyService;
import ua.nure.fomenko.final_project.services.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fomenko on 20.09.2017.
 */
@WebServlet(name = "CreateFacultyServlet", urlPatterns = "/addFaculty.do")
public class CreateFacultyServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CreateFacultyServlet.class);

    private FacultyService facultyService;
    private SubjectService subjectService;

    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService)getServletContext().getAttribute(Params.FACULTY_SERVICE);
        subjectService = (SubjectService)getServletContext().getAttribute(Params.SUBJECT_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Faculty faculty = parseFacultyFromRequest(req);
        int facultyId = facultyService.createNewFaculty(faculty);
        Faculty createdFaculty = facultyService.getFacultyById(facultyId);
        if(facultyId > 0) {
            faculty.setId(facultyId);
            resp.getWriter().write(facultyJson(createdFaculty));
        }
    }

    private String facultyJson(Faculty faculty) {
        JSONObject jsonObject = new JSONObject(faculty);
        return jsonObject.toString();
    }

    private Faculty parseFacultyFromRequest(HttpServletRequest request) {
        Faculty faculty = new Faculty();
        faculty.setName(request.getParameter(Params.FACULTY_NAME));
        faculty.setSubjectList(new ArrayList<>());
        faculty.getSubjectList().add(constructSubject(request.getParameter(Params.FACULTY_FIRST_SUBJECT)));
        faculty.getSubjectList().add(constructSubject(request.getParameter(Params.FACULTY_SECOND_SUBJECT)));
        faculty.getSubjectList().add(constructSubject(request.getParameter(Params.FACULTY_THIRD_SUBJECT)));
        faculty.setCountStateFundedPlace(Integer.parseInt(request.getParameter(Params.FACULTY_SFP)));
        faculty.setAllPlace(Integer.parseInt(request.getParameter(Params.FACULTY_ALL_PLACE)));
        return faculty;
    }

    private Subject constructSubject(String reqId){
        if(reqId != null && !reqId.isEmpty()) {
            Subject subject = new Subject();
            subject.setId(Integer.parseInt(reqId));
            return subject;
        }
        throw new AppException();
    }



}
