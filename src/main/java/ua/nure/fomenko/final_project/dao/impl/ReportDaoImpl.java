/*
package ua.nure.fomenko.final_project.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.SQLQueries;
import ua.nure.fomenko.final_project.dao.FacultyDao;
import ua.nure.fomenko.final_project.dao.ReportDao;
import ua.nure.fomenko.final_project.dao.UserDao;
import ua.nure.fomenko.final_project.db.entity.Report;
import ua.nure.fomenko.final_project.db.entity.Status;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.transaction.ThreadLockHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl implements ReportDao {
    private static final Logger LOGGER = Logger.getLogger(ReportDaoImpl.class);

    @Override
    public Report get(int id) {
        return null;
    }

    @Override
    public int create(Report entity) {
        Connection connection = ThreadLockHandler.getConnection();
        int reportId = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.REPORT_CREATE_NEW,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getUser().getId());
            statement.setInt(2, entity.getFaculty().getId());
            statement.setString(3, entity.getStatus().getValue());
            statement.setDate(4, entity.getDate());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                reportId = resultSet.getInt(1);
            }
            LOGGER.debug(Messages.REPORT_CREATE_SUCCESS);
            resultSet.close();
            return reportId;
        } catch (SQLException e) {
            throw new DBException(Messages.ERR_CANNOT_CREATE_REPORT, e);
        }
    }

    @Override
    public boolean update(Report entity) {
        return false;
    }

    @Override
    public boolean delete(Report entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isReportExist(Report report) {
        Connection connection = ThreadLockHandler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.REPORT_GET_BY_USER_AND_FACULTY)) {
            statement.setInt(1, report.getUser().getId());
            statement.setInt(2, report.getFaculty().getId());
            ResultSet resultSet = statement.executeQuery();
            boolean result = resultSet.next();
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new DBException(Messages.ERR_CANNOT_CHECK_REPORT, e);
        }
    }

    @Override


    private List<Report> parseReportList(ResultSet resultSet) throws SQLException {
        List<Report> reports = new ArrayList<>();
        while (resultSet.next()) {
            reports.add(parseReport(resultSet));
        }
        return reports;
    }

    private Report parseReport(ResultSet resultSet) throws SQLException {
        Report report = new Report();
        UserDao userDao = new UserDaoImpl();
        FacultyDao facultyDao = new FacultyDaoImpl();
        report.setId(resultSet.getInt(Params.ID));
        report.setUser(userDao.get(resultSet.getInt(Params.REPORT_USER_ID)));
        report.setFaculty(facultyDao.get(resultSet.getInt(Params.REPORT_FACULTY_ID)));
        report.setStatus(Status.getStatus(Params.REPORT_STATUS));
        report.setDate(resultSet.getDate(Params.REPORT_DATE));
        report.setSumMarks(resultSet.getInt(Params.REPORT_SUM_ABITURIENT_MARK));
        return report;
    }
}
*/
