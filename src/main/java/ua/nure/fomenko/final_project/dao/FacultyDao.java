package ua.nure.fomenko.final_project.dao;

import ua.nure.fomenko.final_project.db.entity.Faculty;
import ua.nure.fomenko.final_project.db.entity.dto.FilterParam;

import java.util.List;

/**
 * Created by fomenko on 12.09.2017.
 */
public interface FacultyDao extends GenericDao<Faculty>{
    List<Faculty> getAllFaculty();
    List<Faculty> getFacultiesByConditions(FilterParam filterParam);
}
