package ua.nure.fomenko.final_project.services;

import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.bean.Complaint;

import java.util.List;

/**
 * Created by fomenko on 17.09.2017.
 */
public interface ComplaintService {
    int registrationUserOnFaculty(Complaint complaint);
    List<Complaint> getComplainsByUser(User user);
    boolean isUserAlreadyRegisteredOnFaculty(Complaint complaint);
    List<Complaint> getAllComplaints();
    Complaint getComplaintByUserAndFaculty(User user, Faculty faculty);
    boolean updateComplaintStatus(Complaint complaint);
    List<Complaint> getAllByStatus(String status);
}
