package ua.nure.fomenko.final_project.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.SQLQueries;
import ua.nure.fomenko.final_project.dao.ComplaintDao;
import ua.nure.fomenko.final_project.dao.FacultyDao;
import ua.nure.fomenko.final_project.dao.UserDao;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.Status;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.db.entity.User;
import ua.nure.fomenko.final_project.db.entity.bean.Complaint;
import ua.nure.fomenko.final_project.db.entity.bean.Mark;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.transaction.ThreadLockHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ComplaintDaoImpl implements ComplaintDao {
    private static final Logger LOGGER = Logger.getLogger(ComplaintDaoImpl.class);

    @Override
    public Complaint get(int id) {
        return null;
    }

    @Override
    public int create(Complaint entity) {
        Connection connection = ThreadLockHandler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.COMPLAINT_CREATE_NEW,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getUser().getId());
            statement.setInt(2, entity.getFaculty().getId());
            statement.setString(3, "registered");
            statement.setDate(4,new Date( entity.getExpressionDate().getTime()));
            statement.setDate(5, null);
            statement.setInt(6, getSummaryMark(entity));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            int resultId = 0;
            if (resultSet.next()) {
                resultId = resultSet.getInt(1);
                addMarkForUser(entity);
            }
            LOGGER.debug(Messages.COMPLAINT_CREATE_SUCCESS);
            resultSet.close();
            return resultId;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_CREATE_COMPLAINT, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_COMPLAINT, e);
        }
    }

    private int getSummaryMark(Complaint complaint) {
        int summaryMark = 0;
        for (Mark mark : complaint.getMarksList()) {
            summaryMark += mark.getValue();
        }
        return summaryMark;
    }

    private void addMarkForUser(Complaint complaint) {
        Connection connection = ThreadLockHandler.getConnection();
        for (Mark mark : complaint.getMarksList()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO user_subject VALUES (?,?,?)")) {
                statement.setInt(1, mark.getUserId());
                statement.setInt(2, mark.getSubject().getId());
                statement.setInt(3, mark.getValue());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DBException();
            }
        }
    }

    @Override
    public boolean update(Complaint entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Complaint entity) {
        return false;
    }

    @Override
    public List<Complaint> getAllComplaintsByUser(User user) {
        Connection connection = ThreadLockHandler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.COMPLAINT_GET_BY_USER)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Complaint> complaints = constructComplaintsList(resultSet);
            LOGGER.debug(Messages.COMPLAINT_OBTAINED);
            resultSet.close();
            return complaints;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_COMPLAINT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_COMPLAINT, e);
        }
    }

    private List<Mark> getMarksForUser(int userId) {
        Connection connection = ThreadLockHandler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT subject_mark.id_sub,subject_mark.mark,subject_mark.subject_name FROM users as u join " +
                "(select us.value as mark,us.id_subject as id_sub, s.name as subject_name,us.id_user from user_subject as us join subjects as s on us.id_subject = s.id) as subject_mark on u.id = subject_mark.id_user where u.id = ? group by id_sub")) {
            statement.setInt(1, userId);
            ResultSet  resultSet = statement.executeQuery();
            List<Mark> subjectList = new ArrayList<>();
            while (resultSet.next()){
                subjectList.add(constructMark(resultSet, userId));
            }
            resultSet.close();
            return subjectList;
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    private Mark constructMark(ResultSet resultSet, int userId) throws SQLException {
        Mark mark = new Mark();
        mark.setUserId(userId);
        Subject subject = new Subject();
        subject.setId(resultSet.getInt("id_sub"));
        subject.setName(resultSet.getString("subject_name"));
        mark.setSubject(subject);
        mark.setValue(resultSet.getInt("mark"));
        return mark;
    }

    @Override
    public List<Complaint> getAllComplaints() {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        List<Complaint> complaints = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.COMPLAINT_GET_ALL)) {
            resultSet = statement.executeQuery();
            complaints = constructComplaintsList(resultSet);
            LOGGER.debug(Messages.COMPLAINT_OBTAINED);
            resultSet.close();
            return complaints;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_COMPLAINT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
        }
    }



    private List<Complaint> constructComplaintsList(ResultSet resultSet) throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        while (resultSet.next()) {
            complaints.add(constructComplaint(resultSet, true));
        }
        return complaints;
    }

    private Complaint constructComplaint(ResultSet resultSet, boolean withMarks) throws SQLException {
        FacultyDao facultyDao = new FacultyDaoImpl();
        UserDao userDao = new UserDaoImpl();
        Complaint complaint = new Complaint();
        complaint.setUser(userDao.get(resultSet.getInt(Params.COMPLAINT_USER_ID)));
        complaint.setFaculty(facultyDao.get(resultSet.getInt(Params.COMPLAINT_FACULTY_ID)));
        complaint.setExpressionDate(resultSet.getDate(Params.COMPLAINT_EXPRESSION_DATE));
        complaint.setStatus(resultSet.getString(Params.COMPLAINT_STATUS));
        complaint.setSummaryMark(resultSet.getInt("summary_mark"));
        if(withMarks) {
            complaint.setMarksList(getMarksForUser(complaint.getUser().getId()));
        }
        return complaint;

    }

    @Override
    public boolean isComplaintAlreadyCreated(Complaint complaint) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        boolean result = false;
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.COMPLAINT_GET_BY_USER_AND_FACULTY)) {
            statement.setInt(1, complaint.getUser().getId());
            statement.setInt(2, complaint.getFaculty().getId());
            resultSet = statement.executeQuery();
            result = resultSet.next();
            resultSet.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_CHECK_COMPLAINT, e);
            throw new DBException(Messages.ERR_CANNOT_CHECK_COMPLAINT, e);
        }
    }

    @Override
    public Complaint getComplaintByUserAndFaculty(User user, Faculty faculty) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        Complaint complaint = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.COMPLAINT_GET_BY_USER_AND_FACULTY)){
            statement.setInt(1, user.getId());
            statement.setInt(2, faculty.getId());
            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                complaint = constructComplaint(resultSet, false);
            }
            LOGGER.debug(Messages.COMPLAINT_OBTAINED);
            resultSet.close();
            return complaint;
        } catch (SQLException e) {
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_COMPLAINT, e);
        }
    }

    @Override
    public boolean updateComplaintStatus(Complaint complaint) {
        Connection connection = ThreadLockHandler.getConnection();
        boolean result = false;
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.COMPLAINT_UPDATE_STATUS,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, complaint.getStatus());
            statement.setDate(2, new Date(complaint.getAcceptedDate().getTime()));
            statement.setDate(3, null);
            statement.setInt(4, complaint.getUser().getId());
            statement.setInt(5, complaint.getFaculty().getId());
            int resultSet = statement.executeUpdate();
            if(resultSet == 0) {
                result = false;
            } else {
                result = true;
            }
            LOGGER.debug(Messages.COMPLAINT_UPDATE_SUCCESS);
            return result;
        } catch (SQLException e) {
            throw new DBException(Messages.ERR_CANNOT_UPDATE_COMPLAINT, e);
        }
    }

    public List<Complaint> getAllByStatus(String status) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        List<Complaint> complaints = null;
        try(PreparedStatement statement = connection.prepareStatement(SQLQueries.COMPLAINT_GET_BY_STATUS)) {
            statement.setString(1, status);
            resultSet = statement.executeQuery();
            complaints = constructComplaintsList(resultSet);
            LOGGER.debug(Messages.COMPLAINT_OBTAINED);
            resultSet.close();
            return complaints;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_COMPLAINT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
        }
    }
}
