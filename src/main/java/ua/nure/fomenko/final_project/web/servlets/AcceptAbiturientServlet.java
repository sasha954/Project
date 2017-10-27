package ua.nure.fomenko.final_project.web.servlets;

import ua.nure.fomenko.final_project.constants.MessageKeys;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.Path;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.Status;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.bean.Complaint;
import ua.nure.fomenko.final_project.exception.AppException;
import ua.nure.fomenko.final_project.services.ComplaintService;
import ua.nure.fomenko.final_project.services.FacultyService;
import ua.nure.fomenko.final_project.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AcceptAbiturientServlet", urlPatterns = "/acceptAbiturient.do")
public class AcceptAbiturientServlet extends HttpServlet {

    private UserService userService;
    private FacultyService facultyService;

    private ComplaintService complaintService;
    private Map<String, String> errors;

    @Override
    public void init() throws ServletException {
        errors = new HashMap<>();
        userService = (UserService) getServletContext().getAttribute(Params.USER_SERVICE);
        facultyService = (FacultyService) getServletContext().getAttribute(Params.FACULTY_SERVICE);
        complaintService = (ComplaintService) getServletContext().getAttribute(Params.COMPLAINT_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute(Params.SESSION_ERRORS) != null) {
            request.getSession().removeAttribute(Params.SESSION_ERRORS);
        }
        if (request.getParameter("userId") == null || request.getParameter("facultyId") == null) {
            errors.put("acceptError", MessageKeys.EMPTY_PARAMS);
        }

        if (errors.isEmpty()) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int facultyId = Integer.parseInt(request.getParameter("facultyId"));
            User user = userService.getUserById(userId);
            Faculty faculty = facultyService.getFacultyById(facultyId);
            Complaint complaint = complaintService.getComplaintByUserAndFaculty(user, faculty);
            if(complaint != null) {
                complaint.setAcceptedDate(new Date());
                complaint.setExpressionDate(null);
                complaint.setStatus("enrolled");
                complaintService.updateComplaintStatus(complaint);
                /*setComplaintStatusAccepted(user, faculty, request, response);*/
                acceptedSuccess(response);
            } else {
                errors.put("acceptUserError", MessageKeys.ACCEPT_USER_ERROR);
                errorAcceptAbiturient(errors, request, response);
            }
        } else {
            errorAcceptAbiturient(errors, request, response);
        }

    }

    private void setComplaintStatusAccepted(User user, Faculty faculty, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Complaint complaint = complaintService.getComplaintByUserAndFaculty(user, faculty);
        complaint.setStatus("Accepted");
        try {
            complaintService.updateComplaintStatus(complaint);
        } catch (AppException e) {
            errors.put("updateStatusError", MessageKeys.WRONG_UPDATE_COMPLAINT_STATUS);
            errorAcceptAbiturient(errors, request, response);
        }

    }

    private void errorAcceptAbiturient(Map<String, String> errors, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(Params.SESSION_ERRORS, errors);
        response.sendRedirect(Path.PERSONAL_CABINET_SERVLET_URI+"?category=abiturients&tag=report");
    }

    private void acceptedSuccess(HttpServletResponse response) throws IOException {
        response.sendRedirect(Path.PERSONAL_CABINET_SERVLET_URI+"?category=abiturients&tag=report");
    }




}
