package repository;

import entity.Faculty;
import entity.Teacher;
import entity.University;

import java.util.List;

import java.util.Optional;

public interface FacultyRepository extends  Repository<Faculty, Long> {
    Optional<Faculty> findByUniqueCode(String uniqueCode);

    boolean existsByUniqueCode(String uniqueCode);

    List<Faculty> findByName(String name);

    List<Faculty> findByUniversity(University university);

    Optional<Faculty> findByDean(Teacher dean);

    boolean existsByDean(Teacher dean);

    Optional<Faculty> findByPhone(String phone);
    boolean existsByPhone(String phone);
    Optional<Faculty> findByEmail(String email);

    boolean existsByEmail(String email);
}
