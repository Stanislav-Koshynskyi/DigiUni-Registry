package ui.finders;


import entity.University;

import java.util.List;
import java.util.Optional;

public interface UniversityFinderInterface {
    public Optional<University> findByFullName();
    public List<University> findByCity() ;
    public Optional<University> findByShortName() ;
    public Optional<University> chooseUniversity(List<University> universityList) ;
    public Optional<University> findAndSelect();
}
