package ua.nure.fomenko.final_project.dao;

import ua.nure.fomenko.final_project.db.entity.Subject;

import java.util.List;

/**
 * Created by fomenko on 12.09.2017.
 */
public interface SubjectDao extends GenericDao<Subject> {

    List<Subject> getAllSubjects();
    Subject getByName(String name);
}
