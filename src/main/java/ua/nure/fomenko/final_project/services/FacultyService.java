package ua.nure.fomenko.final_project.services;

import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.dto.FilterParam;

import java.util.List;

/**
 * Created by fomenko on 12.09.2017.
 */
public interface FacultyService {
    Faculty getFacultyById(int id);
    int createNewFaculty(Faculty faculty);
    boolean removeFaculty(Faculty faculty);
    boolean updateFaculty(Faculty faculty);
    List<Faculty> getAllFaculty();
    List<Faculty> getFacultiesByConditions(FilterParam filterParam);
}
