package ua.nure.fomenko.final_project.services.impl;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.dao.ComplaintDao;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.bean.Complaint;
import ua.nure.fomenko.final_project.exception.AppException;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.services.ComplaintService;
import ua.nure.fomenko.final_project.transaction.Transaction;
import ua.nure.fomenko.final_project.transaction.TransactionManager;
import ua.nure.fomenko.final_project.util.MailHelper;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.List;


public class ComplaintServiceImpl implements ComplaintService {

    private TransactionManager transactionManager;
    private ComplaintDao complaintDao;

    public ComplaintServiceImpl(TransactionManager transactionManager, ComplaintDao complaintDao) {
        this.transactionManager = transactionManager;
        this.complaintDao = complaintDao;
    }

    @Override
    public int registrationUserOnFaculty(Complaint complaint) {
        return transactionManager.execute(new Transaction<Integer>() {
            @Override
            public Integer execute() throws SQLException {
                return complaintDao.create(complaint);
            }
        });
    }

    @Override
    public List<Complaint> getComplainsByUser(User user) {
        return transactionManager.execute(new Transaction<List<Complaint>>() {
            @Override
            public List<Complaint> execute() throws SQLException {
                return complaintDao.getAllComplaintsByUser(user);
            }
        });
    }

    @Override
    public boolean isUserAlreadyRegisteredOnFaculty(Complaint complaint) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                return complaintDao.isComplaintAlreadyCreated(complaint);
            }
        });
    }

    @Override
    public List<Complaint> getAllComplaints() {
        return transactionManager.execute(new Transaction<List<Complaint>>() {
            @Override
            public List<Complaint> execute() throws SQLException {
                return complaintDao.getAllComplaints();
            }
        });
    }

    @Override
    public Complaint getComplaintByUserAndFaculty(User user, Faculty faculty) {
        return transactionManager.execute(new Transaction<Complaint>() {
            @Override
            public Complaint execute() {
                try {
                    return complaintDao.getComplaintByUserAndFaculty(user, faculty);
                } catch (DBException e) {
                    throw new AppException(Messages.ERR_CANNOT_OBTAIN_COMPLAINT);
                }
            }
        });
    }

    @Override
    public boolean updateComplaintStatus(Complaint complaint) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() {
                try {
                    boolean res = complaintDao.updateComplaintStatus(complaint);
                    if(complaint.getStatus().equals("enrolled")) {
                        MailHelper.sendMail(complaint.getUser().getEmail(), "Congratulation", "You are was enrolled on" + complaint.getFaculty().getName());
                    }
                    return res;
                } catch (DBException e) {
                    throw new AppException("Cannot update complaint status");
                } catch (MessagingException e) {
                    throw new AppException("Cannot send mail");
                }
            }
        });
    }

    @Override
    public List<Complaint> getAllByStatus(String status) {
        return transactionManager.execute(new Transaction<List<Complaint>>() {
            @Override
            public List<Complaint> execute() throws SQLException {
                return complaintDao.getAllByStatus(status);
            }
        });
    }


}
