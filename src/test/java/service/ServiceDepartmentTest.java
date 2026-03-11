package service;

import entity.Department;
import entity.Faculty;
import entity.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DepartmentRepository;
import repository.InMemoryDepartmentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceDepartmentTest {
    private DepartmentRepository departmentRepository;
    private ServiceDepartment serviceDepartment;
    private University university;
    private Faculty faculty;

    @BeforeEach
    void setUp() {
        departmentRepository = new InMemoryDepartmentRepository();
        serviceDepartment = new ServiceDepartment(departmentRepository);
        university = new University();
        faculty = new Faculty();
        faculty.setId(1111L);
        faculty.setUniversity(university);
    }

    @Test
    void createDepartmentTest() {
        Department department = new Department("NAUKMA-1", "Computer Science", "CS", faculty, null, "315");
        Department create = serviceDepartment.create(department);
        assertNotNull(create);
        assertEquals("Computer Science", create.getName());
    }

    @Test
    void updateDepartmentTest() {
        Department department = new Department("NAUKMA-1", "Science Computer", "CS", faculty, null, "315");
        Department create = serviceDepartment.create(department);
        create.setName("Computer Science");
        Department updated = serviceDepartment.update(create);
        assertEquals("Computer Science", updated.getName());
    }

    @Test
    void deleteDepartmentTest() {
        Department department = new Department("NAUKMA-1", "Computer Science", "CS", faculty, null, "315");
        Department create = serviceDepartment.create(department);
        serviceDepartment.delete(create.getId());
        Optional<Department> found = departmentRepository.findById(create.getId());
        assertFalse(found.isPresent());
    }
}
