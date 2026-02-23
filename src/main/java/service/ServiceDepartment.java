package service;

import entity.Department;
import entity.Faculty;
import entity.University;
import repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

public class ServiceDepartment implements ServiceDepartmentInterface {

    private final DepartmentRepository departmentRepository;

    public ServiceDepartment(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department create(Department department) {
        if (departmentRepository.existsByUniqueCode(department.getUniqueCode())) {
            throw new IllegalArgumentException(
                    "Department with this unique code already exists"
            );
        }
        return departmentRepository.save(department);
    }


    public Department update(Department department) {
        return departmentRepository.save(department);
    }


    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }


    public List<Department> findAll() {
        return departmentRepository.findAll();
    }


    public List<Department> findByUniqueCode(String code) {
        return departmentRepository.findByUniqueCode(code);
    }

    @Override
    public List<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public List<Department> findByFaculty(Faculty faculty) {
        return departmentRepository.findByFaculty(faculty);
    }

    @Override
    public List<Department> findByUniversity(University university) {
        return departmentRepository.findByUniversity(university);
    }

    @Override
    public List<Department> findByShortName(String shortName) {
        return departmentRepository.findByShortName(shortName);
    }

    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }
}