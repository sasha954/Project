package ua.nure.fomenko.final_project.dao;

import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.bean.Complaint;

import java.util.List;

/**
 * Created by fomenko on 12.09.2017.
 */
public interface ComplaintDao extends GenericDao<Complaint> {
    List<Complaint> getAllComplaintsByUser(User user);
    boolean isComplaintAlreadyCreated(Complaint complaint);
    List<Complaint> getAllComplaints();
    Complaint getComplaintByUserAndFaculty(User user, Faculty faculty);
    boolean updateComplaintStatus(Complaint complaint);
    List<Complaint> getAllByStatus(String status);
}
