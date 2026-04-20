package service;

import entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.TeacherRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTeacherTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private ServiceTeacher serviceTeacher;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        FullName fullName = new FullName("Barbara", "Millicent", "Roberts");
        Contact contact = new Contact("+380982345678", "BarbaraMillicent@naukma.edu.ua");
        teacher = new Teacher(1L, "Barbara-1", fullName, LocalDate.of(1959, 2, 9), contact,
                AcademicDegree.CANDIDATE_OF_SCIENCES, AcademicRank.ASSOCIATE_PROFESSOR,
                LocalDate.of(2021, 10, 13), BigDecimal.valueOf(50000));
    }

    @Test
    void createTeacherTest() {
        when(teacherRepository.findByUniqueCode("Barbara-1")).thenReturn(List.of());
        when(teacherRepository.findByEmail("BarbaraMillicent@naukma.edu.ua")).thenReturn(Optional.empty());
        when(teacherRepository.findByPhone("+380982345678")).thenReturn(Optional.empty());
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = serviceTeacher.create(teacher);

        assertNotNull(result);
        assertEquals("Barbara-1", result.getUniqueCode());
        verify(teacherRepository).save(teacher);
    }

    @Test
    void updateTeacherTest() {
        teacher.setSalary(BigDecimal.valueOf(100000));
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = serviceTeacher.update(teacher);

        assertEquals(BigDecimal.valueOf(100000), result.getSalary());
        verify(teacherRepository).save(teacher);
    }

    @Test
    void deleteTeacherTest() {
        serviceTeacher.delete(1L);

        verify(teacherRepository).deleteById(1L);
    }

    @Test
    void createIfUniqueCodeIsPresentTest() {
        when(teacherRepository.findByUniqueCode("Barbara-1")).thenReturn(List.of(teacher));
        Assertions.assertThrows(IllegalArgumentException.class, () -> serviceTeacher.create(teacher));
        verify(teacherRepository, never()).save(any());
    }

    @Test
    void createTeacherIfEmailExistsTest() {
        when(teacherRepository.findByUniqueCode("Barbara-1")).thenReturn(List.of());
        when(teacherRepository.findByEmail("BarbaraMillicent@naukma.edu.ua")).thenReturn(Optional.of(teacher));
        Assertions.assertThrows(IllegalArgumentException.class, () -> serviceTeacher.create(teacher));
        verify(teacherRepository, never()).save(any());
    }

    @Test
    void createTeacherIfNumberExistsTest() {
        when(teacherRepository.findByUniqueCode("Barbara-1")).thenReturn(List.of());
        when(teacherRepository.findByEmail("BarbaraMillicent@naukma.edu.ua")).thenReturn(Optional.empty());
        when(teacherRepository.findByPhone("+380982345678")).thenReturn(Optional.of(teacher));
        Assertions.assertThrows(IllegalArgumentException.class, () -> serviceTeacher.create(teacher));
        verify(teacherRepository, never()).save(any());
    }
}
