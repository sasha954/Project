package ua.nure.fomenko.final_project.constants;


public final class SQLQueries {
    //Role
    public static final String ROLE_GET_BY_ID = "SELECT * FROM roles WHERE id=?";

    //Users
    public static final String USER_GET_BY_ID = "SELECT * FROM users WHERE id=?";

    public static final String USER_CREATE_NEW = "INSERT INTO users(email, role, password, first_name, last_name, middle_name, region, city, school, certificate, is_block)" +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

    public static final String USER_GET_BY_EMAIL = "SELECT * FROM users WHERE email=?";

    public static final String USER_GET_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email=? AND password=?";

    public static final String USER_UPDATE = "UPDATE users SET email=?, role=?, password=? first_name=?, last_name=?, middle_name=?, region=?, city=?, school=?, certificate=?, is_block=? WHERE id=?";

    //Subject
    public static final String SUBJECT_GET_BY_ID = "SELECT * FROM subjects WHERE id=?";

    public static final String SUBJECT_CREATE_NEW = "INSERT INTO subjects(name) VALUES(?)";

    public static final String SUBJECT_DELETE_BY_ID = "DELETE FROM subjects WHERE id=?";

    public static final String SUBJECT_GET_ALL = "SELECT * FROM subjects";
    public static final String SUBJECT_GET_BY_NAME = "SELECT * FROM subjects where name=?";


    //Faculty
    public static final String FACULTY_GET_BY_ID = "SELECT * FROM faculties WHERE id=?";

    public static final String FACULTY_GET_ALL = "SELECT * FROM faculties";

    public static final String FACULTY_CREATE_NEW = "INSERT INTO faculties(name, count_state_funded_place, count_all_place) VALUES(?,?,?)";

    public static final String FACULTY_UPDATE = "UPDATE faculties SET name=?, count_state_funded_place=?, count_all_place=? WHERE id=?";

    public static final String FACULTY_DELETE_BY_ID = "DELETE FROM faculties WHERE id=?";

    //Complaint
    public static final String COMPLAINT_CREATE_NEW = "INSERT INTO users_faculties VALUES (DEFAULT,?,?,?,?,?,?)";

    public static final String COMPLAINT_GET_BY_USER = "SELECT f.id, f.`name`, uf.* FROM users_faculties uf LEFT JOIN faculties f ON f.id = uf.faculty_id WHERE uf.user_id = ?";

    public static final String COMPLAINT_GET_BY_USER_AND_FACULTY = "SELECT * FROM users_faculties WHERE user_id=? AND faculty_id=?";

    public static final String COMPLAINT_GET_ALL = "SELECT * FROM users_faculties order by summary_mark desc";

    public static final String COMPLAINT_GET_BY_STATUS = "SELECT * FROM users_faculties WHERE status = ?";


    public static final String COMPLAINT_UPDATE_STATUS = "UPDATE users_faculties SET status=?, accepted_date=?, expresion_date=? WHERE user_id=? AND faculty_id=?";

    //Report
    public static final String REPORT_CREATE_NEW = "INSERT INTO report VALUES(DEFAULT,?,?,?,?)";

    public static final String REPORT_GET_BY_USER_AND_FACULTY = "SELECT * FROM report WHERE users_id=? AND faculties_id=?";

    public static final String REPORT_GET_ALL_BY_STATUS = "SELECT r.*, uf.first_subject_mark + uf.second_subject_mark + uf.third_subject_mark + uf.certificate_mark AS sum_marks FROM report r LEFT JOIN users_faculties uf ON r.users_id = uf.user_id AND r.faculties_id = uf.faculty_id AND r.`status`=?";
}
