package ua.nure.fomenko.final_project.services.impl;

import ua.nure.fomenko.final_project.dao.FacultyDao;
import ua.nure.fomenko.final_project.dao.SubjectDao;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.db.entity.dto.FilterParam;
import ua.nure.fomenko.final_project.services.FacultyService;
import ua.nure.fomenko.final_project.services.SubjectService;
import ua.nure.fomenko.final_project.transaction.Transaction;
import ua.nure.fomenko.final_project.transaction.TransactionManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fomenko on 12.09.2017.
 */
public class FacultyServiceImpl implements FacultyService {
    private TransactionManager transactionManager;
    private FacultyDao facultyDao;
    private SubjectDao subjectDao;

    public FacultyServiceImpl(TransactionManager transactionManager, FacultyDao facultyDao,SubjectDao subjectDao) {
        this.transactionManager = transactionManager;
        this.facultyDao = facultyDao;
        this.subjectDao = subjectDao;
    }

    @Override
    public Faculty getFacultyById(int id) {
        return transactionManager.execute(new Transaction<Faculty>() {
            @Override
            public Faculty execute() throws SQLException {
                return facultyDao.get(id);
            }
        });
    }

    @Override
    public int createNewFaculty(Faculty faculty) {
        return transactionManager.execute(new Transaction<Integer>() {
            @Override
            public Integer execute() throws SQLException {
                Subject certificate = subjectDao.getByName("Certificate");
                faculty.getSubjectList().add(certificate);
                return facultyDao.create(faculty);
            }
        });
    }

    @Override
    public boolean removeFaculty(Faculty faculty) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                return facultyDao.delete(faculty);
            }
        });
    }

    @Override
    public boolean updateFaculty(Faculty faculty) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                return facultyDao.update(faculty);
            }
        });
    }

    @Override
    public List<Faculty> getAllFaculty() {
        return transactionManager.execute(new Transaction<List<Faculty>>() {
            @Override
            public List<Faculty> execute() throws SQLException {
                return facultyDao.getAllFaculty();
            }
        });
    }

    @Override
    public List<Faculty> getFacultiesByConditions(FilterParam filterParam) {
        return transactionManager.execute(new Transaction<List<Faculty>>() {
            @Override
            public List<Faculty> execute() throws SQLException {
                return facultyDao.getFacultiesByConditions(filterParam);
            }
        });
    }
}
