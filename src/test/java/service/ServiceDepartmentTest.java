package service;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.DepartmentRepository;
import repository.StudentGroupRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceDepartmentTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private StudentGroupRepository studentGroupRepository;

    @InjectMocks
    private ServiceDepartment serviceDepartment;

    private University university;
    private Faculty faculty;
    private Department department;

    @BeforeEach
    void setUp() {
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        university = new University(1L, "National University of Kyiv", "NaUKMA", address);
        Contact contact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        faculty = new Faculty(1L, "NAUKMA-FI", "Faculty of Informatics", "FI", null, contact, university);
        department = new Department(1L, "NAUKMA-1", "Computer Science", "CS", faculty, null, "315");
    }

    @Test
    void createDepartmentTest() {
        when(departmentRepository.existsByUniqueCode("NAUKMA-1")).thenReturn(false);
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = serviceDepartment.create(department);

        assertNotNull(result);
        assertEquals("Computer Science", result.getName());
        verify(departmentRepository).existsByUniqueCode("NAUKMA-1");
        verify(departmentRepository).save(department);
    }

    @Test
    void updateDepartmentTest() {
        department.setName("Computer Science Updated");
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = serviceDepartment.update(department);

        assertEquals("Computer Science Updated", result.getName());
        verify(departmentRepository).save(department);
    }

    @Test
    void deleteDepartmentTest() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(studentGroupRepository.findByDepartment(department)).thenReturn(List.of());
        serviceDepartment.delete(1L);

        verify(departmentRepository).deleteById(1L);
    }
    @Test
    void deleteIfHasConnectionTest(){
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(studentGroupRepository.findByDepartment(department)).thenReturn(List.of(new StudentGroup()));
        Assertions.assertThrows(EntityInUseException.class, () -> serviceDepartment.delete(1L));
    }
    @Test
    void createIfExistsByUniqueCodeTest() {
        when(departmentRepository.existsByUniqueCode(department.getUniqueCode())).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> serviceDepartment.create(department));
    }
}