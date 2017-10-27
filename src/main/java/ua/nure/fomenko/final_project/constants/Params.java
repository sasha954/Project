package ua.nure.fomenko.final_project.constants;

/**
 * Created by fomenko on 25.08.2017.
 */
public final class Params {

    public static final String CATEGORY_FACULTIES = "faculties";
    public static final String CATEGORY_ABITURIENTS = "abiturients";
    public static final String TAB_MY_FACULTIES = "my_faculties";
    public static final String TAB_USERS_ON_FACULTY_REPORT = "request";
    public static final String TAB_APPROVED_ABITURIENTS = "enrolled";

    public static final String AUTHORIZATION_ERROR = "authorizationError";

    //Session params
    public static final String SESSION_USER = "sessionUser";
    public static final String SESSION_ERRORS = "sessionError";
    public static final String SESSION_USER_FILL_FIELDS = "sessionUserDto";
    public static final String SESSION_MESSAGE = "sessionMessages";

    //Request params
    public static final String REQ_FACULTY_LIST = "facultyList";
    public static final String USER_REPEAT_PASSWORD = "repeat_password";
    public static final String REGISTRATION_SUCCESS = "regSuccess";
    public static final String REGISTRATION_ERRORS = "regErrors";
    public static final String USER_DTO_FIELDS = "userDto";
    public static final String TAB_NAME = "tab";
    public static final String CATEGORY_NAME = "category";
    public static final String REQ_FACULTY_LIST_BY_USER = "userFacultyList";
    public static final String SUBJECTS_LIST = "subjectsList";
    public static final String USERS_ON_FACULTY_REPORT = "usersOnFaculty";
    public static final String CREATE_USER_ERROR = "createUserError";
    public static final String REQ_COMPLAINTS_LIST_BY_USER = "complaintsList";
    public static final String APPORVED_ABITURIENTS = "approvedAbiturients";

    //Service param
    public static final String USER_SERVICE = "userService";
    public static final String SUBJECT_SERVICE = "subjectService";
    public static final String FACULTY_SERVICE = "facultyService";
    public static final String COMPLAINT_SERVICE = "complaintService";
    public static final String REPORT_SERVICE = "reportService";



    //Role field
    public static final String ROLE_NAME = "role_name";

    //Subject field
    public static final String SUBJECT_NAME = "name";

    //User fields
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_MIDDLE_NAME = "middle_name";
    public static final String USER_REGION = "region";
    public static final String USER_CITY = "city";
    public static final String USER_SCHOOL = "school";
    public static final String USER_ROLE = "role";
    public static final String USER_CERTIFICATE = "certificate";
    public static final String USER_IS_BLOCK = "is_block";

    //Faculty fields
    public static final String FACULTY_NAME = "name";
    public static final String FACULTY_FIRST_SUBJECT = "first_subject";
    public static final String FACULTY_SECOND_SUBJECT = "second_subject";
    public static final String FACULTY_THIRD_SUBJECT = "third_subject";
    public static final String FACULTY_SFP = "count_state_funded_place";
    public static final String FACULTY_ALL_PLACE = "count_all_place";

    //Complaint fields
    public static final String COMPLAINT_FACULTY_ID = "faculty_id";
    public static final String COMPLAINT_USER_ID = "user_id";
    public static final String COMPLAINT_FIRST_SUBJECT_MARK = "first_subject_mark";
    public static final String COMPLAINT_SECOND_SUBJECT_MARK = "second_subject_mark";
    public static final String COMPLAINT_THIRD_SUBJECT_MARK = "third_subject_mark";
    public static final String COMPLAINT_EXPRESSION_DATE = "expresion_date";
    public static final String COMPLAINT_CERTIFICATE_MARK = "certificate_mark";
    public static final String COMPLAINT_STATUS = "status";

    //Report fields
    public static final String REPORT_USER_ID = "users_id";
    public static final String REPORT_FACULTY_ID = "faculties_id";
    public static final String REPORT_STATUS = "status";
    public static final String REPORT_DATE = "date";
    public static final String REPORT_SUM_ABITURIENT_MARK = "sum_marks";


    //Generic field
    public static final String ID = "id";

    public static final String ADMINISTRATOR = "Administrator";
    public static final String ABITURIENT = "Abiturient";
}
