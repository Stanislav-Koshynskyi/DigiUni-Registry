package service;

import entity.University;
import java.util.List;
import java.util.Optional;

public interface ServiceUniversityInterface {
    University create(University university);

    University update(University university);

    void delete(Long id);

    List<University> findAll();

    Optional<University> findByFullName(String fullName);

    Optional<University> findByShortName(String shortName);

    List<University> findByCity(String city);
}
