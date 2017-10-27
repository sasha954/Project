package ua.nure.fomenko.final_project.db.entity.bean;

import ua.nure.fomenko.final_project.db.entity.Subject;

/**
 * Created by fomenko on 25.09.2017.
 */
public class Mark {
    private int userId;
    private Subject subject;
    private int value;

    public Mark(int userId, Subject subject, int value) {
        this.userId = userId;
        this.subject = subject;
        this.value = value;
    }

    public Mark() {
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
