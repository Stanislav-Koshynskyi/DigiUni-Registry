
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
import repository.DepartmentRepository;
import repository.FacultyRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceFacultyTest {

    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ServiceFaculty serviceFaculty;

    private University university;
    private Faculty faculty;

    @BeforeEach
    void setUp() {
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        university = new University(1L, "National University of Kyiv", "NaUKMA", address);
        Contact contact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        faculty = new Faculty(1L, "NAUKMA-FI", "Faculty Informatics", "FI", null, contact, university);
    }

    @Test
    void createFacultyTest() {
        when(facultyRepository.existsByUniqueCode("NAUKMA-FI", university)).thenReturn(false);
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty result = serviceFaculty.create(faculty);

        assertNotNull(result);
        assertEquals("Faculty Informatics", result.getName());
        verify(facultyRepository).existsByUniqueCode("NAUKMA-FI", university);
        verify(facultyRepository).save(faculty);
    }


    @Test
    void updateFacultyTest() {
        faculty.setName("Faculty CS Updated");
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty result = serviceFaculty.update(faculty);

        assertEquals("Faculty CS Updated", result.getName());
        verify(facultyRepository).save(faculty);
    }

    @Test
    void deleteFacultyTest() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(departmentRepository.findByFaculty(faculty)).thenReturn(List.of());
        serviceFaculty.delete(1L);
        verify(facultyRepository).deleteById(1L);
    }

    @Test
    void deleteIfHasConnection(){
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(departmentRepository.findByFaculty(faculty)).thenReturn(List.of(new Department()));
        Assertions.assertThrows(EntityInUseException.class, () -> serviceFaculty.delete(1L));
        verify(facultyRepository, never()).deleteById(any());
    }
    @Test
    void createIfExistsByUniqueCodeTest() {
        when(facultyRepository.existsByUniqueCode("NAUKMA-FI", university)).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> serviceFaculty.create(faculty));
        verify(departmentRepository, never()).save(any());
    }
}