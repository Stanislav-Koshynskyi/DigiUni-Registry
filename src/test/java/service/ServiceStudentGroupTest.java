package service;

import entity.*;
import exception.EntityInUseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.StudentGroupRepository;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceStudentGroupTest {
    @Mock
    private StudentGroupRepository studentGroupRepository;
    private StudentGroup studentGroup1;
    private Student student1;
    private University university;
    private Faculty faculty;
    private Department department;
    @InjectMocks
    private ServiceStudentGroup serviceStudentGroup;
    @BeforeEach
    void setUp() {
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        university = new University(1L, "National University of Kyiv", "NaUKMA", address);
        Contact contact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        faculty = new Faculty(1L, "NAUKMA-FI", "Faculty of Informatics", "FI", null, contact, university);
        department = new Department(1L, "NAUKMA-1", "Computer Science", "CS", faculty, null, "315");

        studentGroup1 = new StudentGroup("asd", department);
        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact1 = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        student1 = new Student(1L, "Barbara-1", "RB001", fullName, LocalDate.of(1959, 2, 9), contact1,
                FormOfEducation.BUDGET, StudentStatus.STUDIES, Year.of(2020), 1, studentGroup1);

    }
    @Test
    void deleteIfHasConnection(){
        when(studentGroupRepository.findById(1L)).thenReturn(Optional.of(studentGroup1));
        studentGroup1.addStudent(student1);
        Assertions.assertThrows(EntityInUseException.class, () -> serviceStudentGroup.delete(1L));
        verify(studentGroupRepository, never()).deleteById(any());
    }
}
