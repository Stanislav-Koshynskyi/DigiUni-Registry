package service;

import entity.Department;
import java.util.List;
import java.util.Optional;

public interface ServiceDepartmentInterface {

    Department create(Department department);

    Department update(Department department);

    void delete(Long id);

    List<Department> findAll();

    Optional<Department> findByUniqueCode(String code);
}
