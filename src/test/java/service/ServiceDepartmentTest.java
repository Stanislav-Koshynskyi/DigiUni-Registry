package service;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DepartmentRepository;
import repository.FacultyRepository;
import repository.InMemoryDepartmentRepository;
import repository.InMemoryFacultyRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceDepartmentTest {
    private DepartmentRepository departmentRepository;
    private ServiceDepartment serviceDepartment;
    private FacultyRepository facultyRepository;
    private University university;
    private Faculty faculty;

    @BeforeEach
    void setUp() {
        departmentRepository = new InMemoryDepartmentRepository();
        serviceDepartment = new ServiceDepartment(departmentRepository);
        facultyRepository = new InMemoryFacultyRepository();
        university = new University();
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        university = new University("National University of Kyiv", "NaUKMA", address);
        Contact contact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        faculty = new Faculty("NAUKMA-FI", "Faculty of Informatics", "FI", null, contact, university);
        facultyRepository.save(faculty);
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
