package ua.nure.fomenko.final_project.constants;

/**
 * Created by fomenko on 25.08.2017.
 */
public final class Messages {
    public static final String TRANSACTION_SUCCESS = "";
    public static final String CONNECTION_CLOSED = "";
    public static final String CANNOT_CLOSE_CONNECTION = "";
    public static final String ROLLBACK_CLOSED = "";
    public static final String CANNOT_EXECUTE_ROLLBACK = "";
    public static final String ERR_TRANSACTION = "";
    public static final String LOG4J_INIT = "";
    public static final String CANNOT_INIT_LOG4J = "";
    public static final String ERR_DATASOURCE_INIT = "";


    public static final String ERR_CANNOT_ENCRYPT_PASSWORD = "";



    //Dao implementation message

    //Role dao
    public static final String ROLE_OBTAINED = "Role object was obtained from database.";
    public static final String ERR_CANNOT_OBTAIN_ROLE = "Cannot obtain role from database.";

    //Subject dao
    public static final String SUBJECT_OBTAINED = "Subject object was obtained from database.";
    public static final String ERR_CANNOT_OBTAIN_SUBJECT = "Cannot obtain subject from database.";

    public static final String SUBJECT_CREATE_SUCCESS = "Subject was successfully created.";
    public static final String ERR_CANNOT_CREATE_SUBJECT = "Cannot create subject.";

    public static final String SUBJECT_DELETE_SUCCESS = "Subject was successfully deleted.";
    public static final String ERR_CANNOT_DELETE_SUBJECT = "Cannot delete subject.";

    //User dao
    public static final String USER_START_CREATING = "Start creating user.";
    public static final String USER_CREATE_SUCCESS = "User was successfully created.";
    public static final String ERR_CANNOT_CREATE_USER = "Cannot create user.";

    public static final String USER_OBTAINED = "User object was obtained from database.";
    public static final String ERR_CANNOT_OBTAIN_USER = "Cannot obtain user from database.";

    public static final String USER_UPDATE_SUCCESS = "User was successfully updated.";
    public static final String ERR_CANNOT_UPDATE_USER = "Cannot update user.";

    public static final String ERR_CANNOT_CHECK_USER = "Cannot check is user exist.";

    //Faculty dao
    public static final String FACULTY_OBTAINED = "Faculty object was obtained from database.";
    public static final String ERR_CANNOT_OBTAIN_FACULTY = "Cannot obtain faculty from database.";

    public static final String FACULTY_CREATE_SUCCESS = "Faculty was successfully created.";
    public static final String ERR_CANNOT_CREATE_FACULTY = "Cannot create faculty.";

    public static final String FACULTY_UPDATE_SUCCESS = "Faculty was successfully updated.";
    public static final String ERR_CANNOT_UPDATE_FACULTY = "Cannot update faculty.";

    public static final String FACULTY_DELETE_SUCCESS = "Faculty was successfully deleted.";
    public static final String ERR_CANNOT_DELETE_FACULTY = "Cannot delete faculty.";

    //Complaint dao
    public static final String COMPLAINT_CREATE_SUCCESS = "Complaint was successfully created.";
    public static final String ERR_CANNOT_CREATE_COMPLAINT = "Cannot create complaint.";

    public static final String COMPLAINT_OBTAINED = "Complaint object was obtained from database.";
    public static final String ERR_CANNOT_OBTAIN_COMPLAINT = "Cannot obtain complaint from database.";

    public static final String COMPLAINT_UPDATE_SUCCESS = "Complaint was successfully updated.";
    public static final String ERR_CANNOT_UPDATE_COMPLAINT = "Cannot update complaint.";

    public static final String ERR_CANNOT_CHECK_COMPLAINT = "Cannot check is complaint exist.";

    //Report dao
    public static final String REPORT_CREATE_SUCCESS = "Report was successfully created.";
    public static final String ERR_CANNOT_CREATE_REPORT = "Cannot create report";

    public static final String ERR_CANNOT_CHECK_REPORT = "Cannot check is report exist.";

    public static final String REPOST_OBTAINED = "Report object was obtained from database.";
    public static final String ERR_CANNOT_OBTAINT_REPORT = "Cannot obtain report.";
}
