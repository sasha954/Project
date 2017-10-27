package ua.nure.fomenko.final_project.db.entity.bean;

import ua.nure.fomenko.final_project.db.entity.Entity;
import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.Subject;
import ua.nure.fomenko.final_project.db.entity.User;


import java.util.Date;
import java.util.List;

/**
 * Created by fomenko on 08.09.2017.
 */
public class Complaint extends Entity {
    private User user;
    private Faculty faculty;
    private List<Mark> marksList;
    private Date expressionDate;
    private Date acceptedDate;
    private int summaryMark;
    private String status;

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public int getSummaryMark() {
        return summaryMark;
    }

    public void setSummaryMark(int summaryMark) {
        this.summaryMark = summaryMark;
    }

    public List<Mark> getMarksList() {
        return marksList;
    }

    public void setMarksList(List<Mark> marksList) {
        this.marksList = marksList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpressionDate() {
        return expressionDate;
    }

    public void setExpressionDate(Date expressionDate) {
        this.expressionDate = expressionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Complaint{");
        sb.append("user=").append(user);
        sb.append(", faculty=").append(faculty);
        sb.append('}');
        return sb.toString();
    }
}
