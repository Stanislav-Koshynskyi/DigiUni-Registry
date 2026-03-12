package service;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceStudentTest {
    private StudentRepository studentRepository;
    private FacultyRepository facultyRepository;
    private DepartmentRepository departmentRepository;
    private ServiceStudent serviceStudent;
    private StudentGroup group;

    @BeforeEach
    void setUp() {
        studentRepository = new InMemoryStudentRepository();
        serviceStudent = new ServiceStudent(studentRepository);
        facultyRepository = new InMemoryFacultyRepository();
        departmentRepository = new InMemoryDepartmentRepository();
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        University university = new University("Kyiv-Mohyla Academy", "NaUKMA", address);
        Contact contact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        Faculty faculty = new Faculty("NAUKMA-FI", "Faculty Informatics", "FI", null, contact, university);
        facultyRepository.save(faculty);
        Department department = new Department("NAUKMA-1", "Computer Science", "CS", faculty, null, "315");
        departmentRepository.save(department);
        group = new StudentGroup("Software engineers", department);
    }

    @Test
    void createStudentTest() {
        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        Student student = new Student("Barbara-1", "RB001", fullName, LocalDate.of(1959,2,9),contact, FormOfEducation.BUDGET, StudentStatus.STUDIES, Year.of(2020), 1, group);

        Student save = serviceStudent.create(student);
        assertNotNull(save.getId());
        assertEquals("Barbara-1", save.getUniqueCode());
        assertEquals(group, save.getGroup());
    }

    @Test
    void updateStudentTest() {
        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        Student student = new Student("Barbara-1", "RB001", fullName, LocalDate.of(1959,2,9),contact, FormOfEducation.BUDGET, StudentStatus.STUDIES, Year.of(2020), 1, group);
        Student save = serviceStudent.create(student);

        save.setCourse(2);
        Student update = serviceStudent.update(save);
        assertEquals(2, update.getCourse());
    }

    @Test
    void deleteStudentTest() {
        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        Student student = new Student("Barbara-1", "RB001", fullName, LocalDate.of(1959,2,9),contact, FormOfEducation.BUDGET, StudentStatus.STUDIES, Year.of(2020), 1, group);
        Student save = serviceStudent.create(student);

        serviceStudent.delete(save.getId());
        Optional<Student> delete = serviceStudent.findById(save.getId());
        assertFalse(delete.isPresent());
    }
}
