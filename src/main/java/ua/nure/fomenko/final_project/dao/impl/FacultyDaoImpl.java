package ua.nure.fomenko.final_project.dao.impl;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.constants.SQLQueries;
import ua.nure.fomenko.final_project.dao.FacultyDao;
import ua.nure.fomenko.final_project.dao.builder.QueryBuilder;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.db.entity.dto.FilterParam;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.transaction.ThreadLockHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fomenko on 12.09.2017.
 */
public class FacultyDaoImpl implements FacultyDao {
    private static final Logger LOGGER = Logger.getLogger(FacultyDaoImpl.class);

    @Override
    public Faculty get(int id) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        Faculty faculty = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.FACULTY_GET_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                faculty = parseFaculty(resultSet);
            }
            LOGGER.debug(Messages.FACULTY_OBTAINED);
            resultSet.close();
            return faculty;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_FACULTY, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FACULTY, e);
        }
    }


    @Override
    public int create(Faculty entity) {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        int facultyId = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.FACULTY_CREATE_NEW, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getCountStateFundedPlace());
            statement.setInt(3, entity.getAllPlace());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                facultyId = resultSet.getInt(1);
                entity.setId(facultyId);
                addSubjectToFaculty(entity);
            }
            LOGGER.debug(Messages.FACULTY_CREATE_SUCCESS);
            resultSet.close();

            return facultyId;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_CREATE_FACULTY, e);
            throw new DBException(Messages.ERR_CANNOT_CREATE_FACULTY, e);
        }
    }

    private void addSubjectToFaculty(Faculty faculty) {
        Connection connection = ThreadLockHandler.getConnection();
        for (Subject subject : faculty.getSubjectList()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO faculty_subject VALUES (DEFAULT,?,?)")) {
                statement.setInt(1, faculty.getId());
                statement.setInt(2, subject.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DBException();
            }
        }
    }

    @Override
    public List<Faculty> getAllFaculty() {
        Connection connection = ThreadLockHandler.getConnection();
        ResultSet resultSet = null;
        List<Faculty> faculties = null;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.FACULTY_GET_ALL)) {
            resultSet = statement.executeQuery();
            faculties = parseFacultyList(resultSet);
            LOGGER.debug(Messages.FACULTY_OBTAINED);
            resultSet.close();
            return faculties;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_OBTAIN_FACULTY, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_FACULTY, e);
        }
    }

    @Override
    public boolean update(Faculty entity) {
        Connection connection = ThreadLockHandler.getConnection();
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.FACULTY_UPDATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getCountStateFundedPlace());
            statement.setInt(3, entity.getAllPlace());
            statement.setInt(4, entity.getId());
            int resultSet = statement.executeUpdate();

            if (resultSet == 0) {
                result = false;
            } else {
                result = true;
            }
            LOGGER.debug(Messages.FACULTY_UPDATE_SUCCESS);
            return result;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_UPDATE_FACULTY, e);
            throw new DBException(Messages.ERR_CANNOT_UPDATE_FACULTY, e);
        }
    }

    @Override
    public boolean delete(Faculty entity) {
        Connection connection = ThreadLockHandler.getConnection();
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLQueries.FACULTY_DELETE_BY_ID)) {
            statement.setInt(1, entity.getId());
            result = statement.executeUpdate();
            LOGGER.debug(Messages.FACULTY_DELETE_SUCCESS);
            return result > 0;
        } catch (SQLException e) {
            LOGGER.error(Messages.ERR_CANNOT_DELETE_FACULTY, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_FACULTY, e);
        }
    }

    private Faculty parseFaculty(ResultSet resultSet) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setId(resultSet.getInt(Params.ID));
        faculty.setName(resultSet.getString(Params.FACULTY_NAME));
        faculty.setCountStateFundedPlace(resultSet.getInt(Params.FACULTY_SFP));
        faculty.setAllPlace(resultSet.getInt(Params.FACULTY_ALL_PLACE));
        faculty.setSubjectList(getFacultySubject(faculty.getId()));
        return faculty;
    }

    private List<Faculty> parseFacultyList(ResultSet resultSet) throws SQLException {
        List<Faculty> faculties = new ArrayList<>();
        while (resultSet.next()) {
            faculties.add(parseFaculty(resultSet));
        }
        return faculties;
    }

    private List<Subject> getFacultySubject(int idFaculty) {
        Connection connection = ThreadLockHandler.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("select sub.id,sub.subject_name from faculties as f join " +
                "(select fs.id_faculty, fs.id_subject as id, s.name as subject_name " +
                "from faculty_subject as fs join subjects as s on fs.id_subject = s.id) as sub on f.id =  sub.id_faculty where f.id = ?")) {
            statement.setInt(1, idFaculty);
            ResultSet resultSet = statement.executeQuery();
            List<Subject> subjectList = new ArrayList<>();
            while (resultSet.next()) {
                subjectList.add(parseFacultySubject(resultSet));
            }
            resultSet.close();
            return subjectList;
        } catch (SQLException e) {
            throw new DBException();
        }
    }

    private Subject parseFacultySubject(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();
        subject.setId(resultSet.getInt("id"));
        subject.setName(resultSet.getString("subject_name"));
        return subject;
    }

    @Override
    public List<Faculty> getFacultiesByConditions(FilterParam filterParam) {
        Connection connection = ThreadLockHandler.getConnection();
        QueryBuilder builder = new QueryBuilder();
        builder.setFilterParam(filterParam);
        builder.build();
        String query = builder.toString();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Faculty> faculties = parseFacultyList(resultSet);
            resultSet.close();
            return faculties;
        } catch (SQLException e) {
            throw new DBException();
        }
    }
}
