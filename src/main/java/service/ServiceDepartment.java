package service;

import entity.Department;
import entity.Faculty;
import entity.StudentGroup;
import entity.University;
import exception.EntityInUseException;
import lombok.extern.slf4j.Slf4j;
import repository.DepartmentRepository;
import repository.StudentGroupRepository;

import java.util.List;
import java.util.Optional;
@Slf4j
public class ServiceDepartment implements ServiceDepartmentInterface {

    private final DepartmentRepository departmentRepository;
    private final StudentGroupRepository studentGroupRepository;

    public ServiceDepartment(DepartmentRepository departmentRepository, StudentGroupRepository studentGroupRepository) {
        this.departmentRepository = departmentRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    public Department create(Department department) {
        if (departmentRepository.existsByUniqueCode(department.getUniqueCode())) {
            log.warn("Department with code {} already exists", department.getUniqueCode());
            throw new IllegalArgumentException(
                    "Department with this unique code already exists"
            );
        }
        Department result = departmentRepository.save(department);
        log.info("Department with id {} created", result.getId());
        return result;
    }


    public Department update(Department department) {
        return departmentRepository.save(department);
    }


    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Trying delete department with id {} buy not found", id);
                    return new IllegalArgumentException("Department with id " + id + " does not exist");
                });
        List<StudentGroup> studentGroups = studentGroupRepository.findByDepartment(department);
        if (!studentGroups.isEmpty()) {
            log.warn("Trying delete department with id {}, but it has studentGroups", id);
            throw new EntityInUseException("This department has student groups, number: " + studentGroups.size());
        }
        log.info("Department {} deleted, id: {}", department.getName(), department.getId());
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

    @Override
    public boolean existsByUniqueCode(String uniqueCode, University university) {
        return departmentRepository.existsByUniqueCode(uniqueCode, university);
    }
}