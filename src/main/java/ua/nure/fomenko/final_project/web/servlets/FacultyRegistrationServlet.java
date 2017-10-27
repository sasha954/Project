package ua.nure.fomenko.final_project.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.bean.Complaint;
import ua.nure.fomenko.final_project.db.entity.bean.Mark;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.services.ComplaintService;
import ua.nure.fomenko.final_project.services.FacultyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by fomenko on 17.09.2017.
 */
@WebServlet(name = "FacultyRegistrationServlet", urlPatterns = "/registrationInFaculty.do")
public class FacultyRegistrationServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(FacultyRegistrationServlet.class);

    private FacultyService facultyService;
    private ComplaintService complaintService;

    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService) getServletContext().getAttribute(Params.FACULTY_SERVICE);
        complaintService = (ComplaintService) getServletContext().getAttribute(Params.COMPLAINT_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User abiturient = (User) request.getSession().getAttribute(Params.SESSION_USER);
        Faculty regFaculty = facultyService.getFacultyById(Integer.parseInt(request.getParameter("faculty-id")));
        Complaint complaint = constructComplaintFormRequest(request, regFaculty, abiturient);

        complaint.setExpressionDate(getExpressionDate());
        if(!complaintService.isUserAlreadyRegisteredOnFaculty(complaint)) {
            complaintService.registrationUserOnFaculty(complaint);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("You have already applied for this faculty.");
        }

    }

    private Date getExpressionDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(Calendar.getInstance().getTimeInMillis()));
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        return new Date(calendar.getTimeInMillis());
    }

    private Complaint constructComplaintFormRequest(HttpServletRequest request, Faculty faculty, User user) {
        Complaint complaint = new Complaint();
        complaint.setMarksList(new ArrayList<>());
        for(Subject subject : faculty.getSubjectList()) {
            Mark userMark = new Mark(user.getId(), subject,
                    Integer.parseInt(request.getParameter(subject.getName())));
            complaint.getMarksList().add(userMark);
        }
        complaint.setUser(user);
        complaint.setFaculty(faculty);
        return complaint;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
