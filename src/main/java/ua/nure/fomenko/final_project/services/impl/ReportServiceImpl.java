/*
package ua.nure.fomenko.final_project.services.impl;

import ua.nure.fomenko.final_project.constants.MessageKeys;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.dao.ReportDao;
import ua.nure.fomenko.final_project.db.entity.Report;
import ua.nure.fomenko.final_project.db.entity.Status;
import ua.nure.fomenko.final_project.exception.AppException;
import ua.nure.fomenko.final_project.exception.DBException;
import ua.nure.fomenko.final_project.services.ReportService;
import ua.nure.fomenko.final_project.transaction.Transaction;
import ua.nure.fomenko.final_project.transaction.TransactionManager;

import java.util.List;

*/
/**
 * Created by fomenko on 23.09.2017.
 *//*

public class ReportServiceImpl implements ReportService {
    private TransactionManager transactionManager;
    private ReportDao reportDao;

    public ReportServiceImpl(TransactionManager transactionManager, ReportDao reportDao) {
        this.transactionManager = transactionManager;
        this.reportDao = reportDao;
    }

    @Override
    public int acceptAbiturientOnFaculty(Report report) {
        return transactionManager.execute(new Transaction<Integer>() {
            @Override
            public Integer execute() {
                try {
                    return reportDao.create(report);
                } catch (DBException e) {
                    throw new AppException(MessageKeys.CANNOT_ACCEPT_ABITURIENT);
                }
            }
        });
    }

    @Override
    public boolean isReportExist(Report report) {
        return transactionManager.execute(new Transaction<Boolean>() {
            @Override
            public Boolean execute() {
                try {
                    return reportDao.isReportExist(report);
                } catch (DBException e) {
                    throw new AppException(MessageKeys.CANNOT_ACCEPT_ABITURIENT);
                }
            }
        });
    }

    @Override
    public List<Report> getAllByStatus(Status status) {
        return transactionManager.execute(new Transaction<List<Report>>() {
            @Override
            public List<Report> execute() {
                try {
                    return reportDao.getAllByStatus(status);
                } catch (DBException e) {
                    throw new AppException(Messages.ERR_CANNOT_OBTAINT_REPORT);
                }
            }
        });
    }
}
*/
