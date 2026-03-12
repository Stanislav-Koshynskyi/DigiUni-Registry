package service;

import entity.Faculty;
import entity.University;

import java.util.List;
import java.util.Optional;

public interface ServiceFacultyInterface {
    Faculty create(Faculty faculty);

    Faculty update(Faculty faculty);

    void delete(Long id);

    List<Faculty> findAll();

    Optional<Faculty> findById(Long id);
    
    List<Faculty> findByUniqueCode(String uniqueCode);

    boolean existsByUniqueCode(String uniqueCode, University university);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<Faculty> findByName(String name);

    List<Faculty> findByShortName(String shortName);

    Optional<Faculty> findByEmail(String email);

    Optional<Faculty> findByPhoneNumber(String phoneNumber);

    List<Faculty> findByUniversity(University university);
}
