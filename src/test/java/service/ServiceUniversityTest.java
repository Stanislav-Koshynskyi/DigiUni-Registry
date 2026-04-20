package service;

import entity.Address;
import entity.Faculty;
import entity.University;
import exception.EntityInUseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.FacultyRepository;
import repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceUniversityTest {

    @Mock
    private UniversityRepository universityRepository;
    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private ServiceUniversity serviceUniversity;

    private University university;

    @BeforeEach
    void setUp() {
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        university = new University(1L, "Kyiv-Mohyla Academy", "NaUKMA", address);
    }

    @Test
    void createUniversityTest() {
        when(universityRepository.existsByFullName("Kyiv-Mohyla Academy")).thenReturn(false);
        when(universityRepository.existsByShortName("NaUKMA")).thenReturn(false);
        when(universityRepository.save(university)).thenReturn(university);

        University result = serviceUniversity.create(university);

        assertEquals("Kyiv-Mohyla Academy", result.getFullName());
        assertEquals("NaUKMA", result.getShortName());
        verify(universityRepository).save(university);
    }

    @Test
    void updateUniversityTest() {
        university.setFullName("Kyiv-Mohyla Academy Updated");
        when(universityRepository.save(university)).thenReturn(university);
        when(universityRepository.findById(university.getId())).thenReturn(Optional.of(university));

        serviceUniversity.update(university);
        Optional<University> result = serviceUniversity.findById(university.getId());

        assertTrue(result.isPresent());
        assertEquals("Kyiv-Mohyla Academy Updated", result.get().getFullName());
        verify(universityRepository).save(university);
    }

    @Test
    void deleteUniversityTest() {
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
        when(facultyRepository.findByUniversity(university)).thenReturn(List.of());
        serviceUniversity.delete(1L);

        verify(universityRepository).deleteById(1L);
    }
    @Test
    void createWhenNameExistsTest() {
        when(universityRepository.existsByFullName("Kyiv-Mohyla Academy")).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> serviceUniversity.create(university));
        verify(universityRepository, never()).save(any());
    }
    @Test
    void deleteWhenHasConnectionTest(){
        when(universityRepository.findById(1L)).thenReturn(Optional.of(university));
        when(facultyRepository.findByUniversity(university)).thenReturn(List.of(new Faculty()));
        Assertions.assertThrows(EntityInUseException.class, () -> serviceUniversity.delete(1L));
        verify(universityRepository, never()).deleteById(any());
    }
}