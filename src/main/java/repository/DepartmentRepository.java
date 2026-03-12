package repository;

import entity.Department;
import entity.Faculty;
import entity.Teacher;
import entity.University;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends Repository<Department, Long> {

    List<Department> findByUniqueCode(String code);
    
    
    
    
    boolean existsByUniqueCode(String code);

    List<Department> findByName(String name);

    Optional<Department> findByNameAndFaculty(String name, Faculty faculty);

    List<Department> findByFaculty(Faculty faculty);

    Optional<Department> findByHeadOfDepartment(Teacher headOfDepartment);

    boolean existsByHeadOfDepartment(Teacher headOfDepartment);

    List<Department> findByUniversity(University university);

    List<Department> findByShortName(String shortName);

    boolean existsByUniqueCode(String uniqueCode, University university);
}
