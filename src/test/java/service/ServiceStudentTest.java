package service;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.StudentRepository;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceStudentTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private ServiceStudent serviceStudent;

    private University university;
    private StudentGroup group;
    private Student student;

    @BeforeEach
    void setUp() {
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        university = new University(1L, "Kyiv-Mohyla Academy", "NaUKMA", address);
        Contact facultyContact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        Faculty faculty = new Faculty(1L, "NAUKMA-FI", "Faculty Informatics", "FI", null, facultyContact, university);
        Department department = new Department(1L, "NAUKMA-1", "Computer Science", "CS", faculty, null, "315");
        group = new StudentGroup(1L, "Software engineers", department);

        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        student = new Student(1L, "Barbara-1", "RB001", fullName, LocalDate.of(1959, 2, 9), contact,
                FormOfEducation.BUDGET, StudentStatus.STUDIES, Year.of(2020), 1, group);
        group.addStudent(student);
    }

    @Test
    void createStudentTest() {
        when(studentRepository.existsByUniqueCode("Barbara-1", university)).thenReturn(false);
        when(studentRepository.existsByRecordBookNumber("RB001", university)).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);

        Student result = serviceStudent.create(student);

        assertNotNull(result);
        assertEquals("Barbara-1", result.getUniqueCode());
        assertEquals(group, result.getGroup());
        verify(studentRepository).existsByUniqueCode("Barbara-1", university);
        verify(studentRepository).existsByRecordBookNumber("RB001", university);
        verify(studentRepository).save(student);
    }

    @Test
    void updateStudentTest() {
        student.setCourse(2);
        when(studentRepository.save(student)).thenReturn(student);

        Student result = serviceStudent.update(student);

        assertEquals(2, result.getCourse());
        verify(studentRepository).save(student);
    }

    @Test
    void deleteStudentTest() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        serviceStudent.delete(1L);

        verify(studentRepository).deleteById(1L);
    }
}
