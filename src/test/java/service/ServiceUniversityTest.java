package service;

import entity.Address;
import entity.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryUniversityRepository;
import repository.UniversityRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceUniversityTest {
    private UniversityRepository universityRepository;
    private ServiceUniversity serviceUniversity;

    @BeforeEach
    void setUp() {
        universityRepository = new InMemoryUniversityRepository();
        serviceUniversity = new ServiceUniversity(universityRepository);
    }

    @Test
    void createUniversityTest() {
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        University university = new University("Kyiv-Mohyla Academy", "NaUKMA", address);
        University create = serviceUniversity.create(university);

        assertEquals("Kyiv-Mohyla Academy", create.getFullName());
        assertEquals("NaUKMA", create.getShortName());
    }

    @Test
    void updateUniversityTest() {
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        University university = new University("National University of Kyiv", "NaUKMA", address);
        University create = serviceUniversity.create(university);

        create.setFullName("Kyiv-Mohyla Academy");
        serviceUniversity.update(create);
        Optional<University> update = serviceUniversity.findById(create.getId());
        assertTrue(update.isPresent());
        assertEquals("Kyiv-Mohyla Academy", update.get().getFullName());
    }

    @Test
    void deleteUniversityTest() {
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        University university = new University("National University of Kyiv", "NaUKMA", address);
        University create = serviceUniversity.create(university);

        serviceUniversity.delete(create.getId());
        Optional<University> delete = serviceUniversity.findById(create.getId());
        assertFalse(delete.isPresent());
    }
}
