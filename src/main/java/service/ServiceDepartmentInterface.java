package service;

import entity.Department;
import entity.Faculty;
import entity.University;

import java.util.List;
import java.util.Optional;

public interface ServiceDepartmentInterface {

    Department create(Department department);

    Department update(Department department);

    void delete(Long id);

    List<Department> findAll();

    List<Department> findByUniqueCode(String code);
    List<Department> findByName(String name);
    List<Department> findByFaculty(Faculty faculty);
    List<Department> findByUniversity(University university);
    List<Department> findByShortName(String shortName);



    Optional<Department> findById(Long id);

    boolean existsByUniqueCode(String uniqueCode, University university);
}
