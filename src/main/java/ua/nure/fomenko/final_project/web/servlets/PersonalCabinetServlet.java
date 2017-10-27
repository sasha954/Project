package ua.nure.fomenko.final_project.web.servlets;

import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Path;
import ua.nure.fomenko.final_project.db.entity.*;
import ua.nure.fomenko.final_project.db.entity.bean.Complaint;
import ua.nure.fomenko.final_project.services.ComplaintService;
import ua.nure.fomenko.final_project.services.FacultyService;

import ua.nure.fomenko.final_project.services.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "PersonalCabinetServlet", urlPatterns = "/personalCabinet.do")
public class PersonalCabinetServlet extends HttpServlet {

    private FacultyService facultyService;
    private SubjectService subjectService;
    private ComplaintService complaintService;


    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService) getServletContext().getAttribute(Params.FACULTY_SERVICE);
        subjectService = (SubjectService) getServletContext().getAttribute(Params.SUBJECT_SERVICE);
        complaintService = (ComplaintService) getServletContext().getAttribute(Params.COMPLAINT_SERVICE);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        prepareRequestParams(request);
        String path = Path.PERSONAL_CABINET_PAGE;
        request.getRequestDispatcher(path).forward(request, response);
    }

    private void prepareRequestParams(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Params.SESSION_USER);
        String tabName = null;
        String categoryName = null;
        if (user.getRole().getName().equals(Params.ADMINISTRATOR)) {
            categoryName = prepareAdminCategoryParam(request);


            List<Faculty> faculties = facultyService.getAllFaculty();
            request.setAttribute("facultyList", faculties);
            List<Subject> subjectList = subjectService.getAllSubjects();
            request.getServletContext().setAttribute(Params.SUBJECTS_LIST, subjectList);
            String facId = request.getParameter("faculty-id");
            int facultetId = 0;
            if (facId != null && !facId.isEmpty()) {
                facultetId = Integer.parseInt(facId);
            } else {
                facultetId = faculties != null ? faculties.get(0).getId() : 0;
            }
            tabName = prepareAdminParam(request);


        } else if ((user.getRole().getName().equals(Params.ABITURIENT))) {
            tabName = prepareAbiturientParam(request, user);
        }

        request.setAttribute(Params.TAB_NAME, tabName);
        request.setAttribute(Params.CATEGORY_NAME, categoryName);
    }

    private String prepareAdminParam(HttpServletRequest request) {
            String tabName = request.getParameter(Params.TAB_NAME);
        //int facultyId = Integer.parseInt(request.getParameter("facultyId"));
        if (tabName == null) {
            tabName = "";
        } else if (tabName.equals(Params.TAB_USERS_ON_FACULTY_REPORT) ) {
            List<Complaint> complaints = complaintService.getAllComplaints();
            request.setAttribute(Params.USERS_ON_FACULTY_REPORT, complaints);
            if (request.getSession().getAttribute(Params.SESSION_ERRORS) != null) {
                Object errors = request.getSession().getAttribute(Params.SESSION_ERRORS);
                request.setAttribute("acceptedError", errors);
                request.getSession().removeAttribute(Params.SESSION_ERRORS);
            }
        } else if (tabName.equals(Params.TAB_APPROVED_ABITURIENTS)) {
            List<Complaint> reportList = complaintService.getAllByStatus("enrolled");
            request.setAttribute(Params.APPORVED_ABITURIENTS, reportList);
        }
        return tabName;
    }


    private String prepareAbiturientParam(HttpServletRequest request, User user) {
        String tabName = request.getParameter(Params.TAB_NAME);
        if (tabName.equals(Params.TAB_MY_FACULTIES)) {
            List<Complaint> complaints = complaintService.getComplainsByUser(user);
            request.setAttribute(Params.REQ_COMPLAINTS_LIST_BY_USER, complaints);
        }
        return tabName;
    }

    private String prepareAdminCategoryParam(HttpServletRequest request) {
        String categoryName = request.getParameter(Params.CATEGORY_NAME);
        if (categoryName.equals(Params.CATEGORY_FACULTIES)) {
            List<Faculty> faculties = facultyService.getAllFaculty();
            request.setAttribute(Params.REQ_FACULTY_LIST, faculties);
        }
        return categoryName;
    }
}
