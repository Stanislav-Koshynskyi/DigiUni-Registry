package service;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryTeacherRepository;
import repository.TeacherRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTeacherTest {
    private TeacherRepository teacherRepository;
    private ServiceTeacher serviceTeacher;

    @BeforeEach
    void setUp() {
        teacherRepository = new InMemoryTeacherRepository();
        serviceTeacher = new ServiceTeacher(teacherRepository);
    }

    @Test
    void createTeacherTest() {
        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        Teacher teacher = new Teacher("Barbara-1", fullName, LocalDate.of(1959,2,9), contact, AcademicDegree.CANDIDATE_OF_SCIENCES, AcademicRank.ASSOCIATE_PROFESSOR, LocalDate.of(2021,10,13), BigDecimal.valueOf(50000));
        Teacher create = serviceTeacher.create(teacher);
        assertNotNull(create);
        assertEquals("Barbara-1", create.getUniqueCode());
    }

    @Test
    void updateTeacherTest() {
        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        Teacher teacher = new Teacher("Barbara-1", fullName, LocalDate.of(1959,2,9), contact, AcademicDegree.CANDIDATE_OF_SCIENCES, AcademicRank.ASSOCIATE_PROFESSOR, LocalDate.of(2021,10,13), BigDecimal.valueOf(50000));
        Teacher create = serviceTeacher.create(teacher);

        create.setSalary(BigDecimal.valueOf(100000));
        Teacher update = serviceTeacher.update(create);
        assertEquals(BigDecimal.valueOf(100000), update.getSalary());
    }

    @Test
    void deleteTeacherTest() {
        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        Teacher teacher = new Teacher("Barbara-1", fullName, LocalDate.of(1959,2,9), contact, AcademicDegree.CANDIDATE_OF_SCIENCES, AcademicRank.ASSOCIATE_PROFESSOR, LocalDate.of(2021,10,13), BigDecimal.valueOf(50000));
        Teacher create = serviceTeacher.create(teacher);

        serviceTeacher.delete(create.getId());
        Optional<Teacher> teacherOptional = serviceTeacher.findById(create.getId());
        assertFalse(teacherOptional.isPresent());
    }
}
