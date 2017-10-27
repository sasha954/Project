package ua.nure.fomenko.final_project.db.entity;

import java.util.List;

/**
 * Created by fomenko on 08.09.2017.
 */
public class Faculty extends Entity {

    private String name;
    private int countStateFundedPlace;
    private int allPlace;
    private List<Subject> subjectList;

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Faculty{");
        sb.append("name='").append(name).append('\'');
        sb.append(", countStateFundedPlace=").append(countStateFundedPlace);
        sb.append(", allPlace=").append(allPlace);
        sb.append('}');
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getCountStateFundedPlace() {
        return countStateFundedPlace;
    }

    public void setCountStateFundedPlace(int countStateFundedPlace) {
        this.countStateFundedPlace = countStateFundedPlace;
    }

    public int getAllPlace() {
        return allPlace;
    }

    public void setAllPlace(int allPlace) {
        this.allPlace = allPlace;
    }
}
