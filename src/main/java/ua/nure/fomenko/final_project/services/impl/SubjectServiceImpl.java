package ua.nure.fomenko.final_project.services.impl;

import ua.nure.fomenko.final_project.dao.SubjectDao;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.services.SubjectService;
import ua.nure.fomenko.final_project.transaction.Transaction;
import ua.nure.fomenko.final_project.transaction.TransactionManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fomenko on 13.09.2017.
 */
public class SubjectServiceImpl implements SubjectService {

    private TransactionManager transactionManager;
    private SubjectDao subjectDao;

    public SubjectServiceImpl(TransactionManager transactionManager, SubjectDao subjectDao) {
        this.transactionManager = transactionManager;
        this.subjectDao = subjectDao;
    }

    @Override
    public Subject getSubjectById(int id) {
        return transactionManager.execute(new Transaction<Subject>() {
            @Override
            public Subject execute() throws SQLException {
                return subjectDao.get(id);
            }
        });
    }

    @Override
    public int addSubject(Subject subject) {
        return transactionManager.execute(new Transaction<Integer>() {
            @Override
            public Integer execute() throws SQLException {
                return subjectDao.create(subject);
            }
        });
    }

    @Override
    public boolean deleteSubject(Subject subject) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() throws SQLException {
                return subjectDao.delete(subject);
            }
        });
    }

    @Override
    public List<Subject> getAllSubjects() {
        return transactionManager.execute(new Transaction<List<Subject>>() {
            @Override
            public List<Subject> execute() throws SQLException {
                return subjectDao.getAllSubjects();
            }
        });
    }
}
