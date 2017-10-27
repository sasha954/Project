package ua.nure.fomenko.final_project.web.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.constants.Params;
import ua.nure.fomenko.final_project.dao.*;
import ua.nure.fomenko.final_project.dao.impl.*;
import ua.nure.fomenko.final_project.exception.InitException;
import ua.nure.fomenko.final_project.services.*;
import ua.nure.fomenko.final_project.services.impl.*;
import ua.nure.fomenko.final_project.transaction.TransactionManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.File;

/**
 * Created by fomenko on 26.08.2017.
 */

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(ContextListener.class);


    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        log4jInit(servletContext);

        DataSource dataSource;
        try {
            Context context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/abiturient_system");
        }catch (NamingException e) {
            LOG.error(Messages.ERR_DATASOURCE_INIT, e);
            throw new InitException(Messages.ERR_DATASOURCE_INIT, e);
        }

        TransactionManager transactionManager = new TransactionManager(dataSource);

        UserDao userDao = new UserDaoImpl();
        SubjectDao subjectDao = new SubjectDaoImpl();
        FacultyDao facultyDao = new FacultyDaoImpl();
        ComplaintDao complaintDao = new ComplaintDaoImpl();
//        ReportDao reportDao = new ReportDaoImpl();

        UserService userService = new UserServiceImpl(transactionManager, userDao);
        SubjectService subjectService = new SubjectServiceImpl(transactionManager, subjectDao);
        FacultyService facultyService = new FacultyServiceImpl(transactionManager, facultyDao, subjectDao);
        ComplaintService complaintService = new ComplaintServiceImpl(transactionManager, complaintDao);
//        ReportService reportService = new ReportServiceImpl(transactionManager, reportDao);

        servletContext.setAttribute(Params.USER_SERVICE, userService);
        servletContext.setAttribute(Params.SUBJECT_SERVICE, subjectService);
        servletContext.setAttribute(Params.FACULTY_SERVICE, facultyService);
        servletContext.setAttribute(Params.COMPLAINT_SERVICE, complaintService);
//        servletContext.setAttribute(Params.REPORT_SERVICE, reportService);

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log("Servlet context destruction start.");
        //Nothing to do
        log("Servlet context destruction finished.");

    }

    private void log4jInit(ServletContext servletContext) {
        log("Log4j initialization start.");
        try {
            String homeDir = servletContext.getRealPath("/");
            File properties = new File(homeDir, "WEB-INF/log4j.properties");
            PropertyConfigurator.configure(properties.toString());
            LOG.info(Messages.LOG4J_INIT);
        } catch (Throwable e) {
            log(Messages.CANNOT_INIT_LOG4J);
            throw new InitException(Messages.CANNOT_INIT_LOG4J, e);
        }
    }

    private void log(String message) {
        System.out.println("CONTEXT LISTENER MESSAGE: " + message);
    }
}
