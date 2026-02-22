package ui.finders;

import entity.Faculty;
import entity.University;
import entity.find_criteria.FacultyFind;
import entity.find_criteria.FacultyForAdvancedFind;
import service.ServiceFacultyInterface;
import ui.InputReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface FacultyFinderInterface {

    public List<Faculty> findByUniqueCode() ;

    public List<Faculty> findByName() ;
    public List<Faculty> findByShortName() ;
    public Optional<Faculty> findByPhoneNumber() ;
    public Optional<Faculty> findByEmail() ;
    public List<Faculty> findByUniversity() ;

    public Optional<Faculty> chooseFaculty(List<Faculty> faculties);
    public Optional<Faculty> findAndSelect();

    public List<Faculty> advancedSearch() ;
}
