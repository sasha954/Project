package ua.nure.fomenko.final_project.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.SQLQueries;
import ua.nure.fomenko.final_project.dao.SubjectDao;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.transaction.ThreadLockHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fomenko on 12.09.2017.
 */
public class SubjectDaoImpl implements SubjectDao {
    private static final Logger LOGGER = Logger.getLogger(SubjectDaoImpl.class);

    @Override
    public Subject get(int id) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        Subject subject = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SUBJECT_GET_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                subject = parseSubject(resultSet);
            }
            //LOGGER.debug(Messages.SUBJECT_OBTAINED);
            resultSet.close();
            return subject;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
        }
    }


    @Override
    public int create(Subject entity) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        int subjectId = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SUBJECT_CREATE_NEW,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                subjectId = resultSet.getInt(1);
            }
            LOGGER.debug(Messages.SUBJECT_CREATE_SUCCESS);
            resultSet.close();
            return subjectId;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_CREATE_SUBJECT, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_SUBJECT, e);
        }
    }

    @Override
    public boolean update(Subject entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Subject entity) {
        Connection connection = ThreadLockHandler.getConnection();
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SUBJECT_DELETE_BY_ID)) {
            statement.setInt(1, entity.getId());
            result = statement.executeUpdate();
            LOGGER.debug(Messages.SUBJECT_DELETE_SUCCESS);
            return result > 0;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_DELETE_SUBJECT, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_SUBJECT, e);
        }
    }

    @Override
    public Subject getByName(String name) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        Subject subject = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SUBJECT_GET_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                subject = parseSubject(resultSet);
            }
            LOGGER.debug(Messages.SUBJECT_OBTAINED);
            resultSet.close();
            return subject;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
        }
    }

    @Override
    public List<Subject> getAllSubjects() {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        List<Subject> subjectList = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.SUBJECT_GET_ALL)) {
            resultSet = statement.executeQuery();

            subjectList = parseSubjectList(resultSet);
            LOGGER.debug(Messages.SUBJECT_OBTAINED);
            resultSet.close();
            return subjectList;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SUBJECT, e);
        }
    }

    private List<Subject> parseSubjectList(ResultSet resultSet) throws SQLException {
        List<Subject> subjectList = new ArrayList<>();
        while (resultSet.next()) {
            subjectList.add(parseSubject(resultSet));
        }
        return subjectList;
    }

    private Subject parseSubject(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();

        subject.setId(resultSet.getInt(Params.ID));
        subject.setName(resultSet.getString(Params.SUBJECT_NAME));

        return subject;
    }
}
