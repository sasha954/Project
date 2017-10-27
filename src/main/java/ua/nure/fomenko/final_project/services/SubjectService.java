package ua.nure.fomenko.final_project.services;

import ua.nure.fomenko.final_project.db.entity.Subject;

import java.util.List;

/**
 * Created by fomenko on 13.09.2017.
 */
public interface SubjectService {

    Subject getSubjectById(int id);

    int addSubject(Subject subject);

    boolean deleteSubject(Subject subject);

    List<Subject> getAllSubjects();

}
