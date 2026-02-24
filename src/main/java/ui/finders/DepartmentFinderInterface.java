package ui.finders;

import entity.Department;
import entity.Faculty;
import entity.University;
import entity.find_criteria.DepartmentFind;
import entity.find_criteria.DepartmentForAdvancedFind;
import entity.find_criteria.FacultyFind;
import entity.find_criteria.FacultyForAdvancedFind;

import java.util.*;

public interface DepartmentFinderInterface {
    Optional<Department> chooseDepartment(List<Department> departments);

    public List<Department> findByUniqueCode();

    List<Department> findByName();

    List<Department> findByShortName();

    List<Department> findByFaculty();

    List<Department> findByUniversity();

    Optional<Department> findAndSelect();

    List<Department> advancedSearch();
}