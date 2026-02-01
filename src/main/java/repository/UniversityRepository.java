package repository;

import entity.University;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UniversityRepository extends Repository<University, Long> {
    Optional<University> findByFullName(String fullName);
    boolean existsByFullName(String fullName);

    Optional<University> findByShortName(String shortName);

    boolean existsByShortName(String shortName);

    List<University> findByCity(String city);
}
