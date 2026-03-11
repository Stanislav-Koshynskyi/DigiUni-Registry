package service;

import entity.Address;
import entity.Contact;
import entity.Faculty;
import entity.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.FacultyRepository;
import repository.InMemoryFacultyRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceFacultyTest {
    private FacultyRepository facultyRepository;
    private ServiceFaculty serviceFaculty;
    private University university;

    @BeforeEach
    void setUp() {
        facultyRepository = new InMemoryFacultyRepository();
        serviceFaculty = new ServiceFaculty(facultyRepository);
        Address address = new Address("Kyiv", "Hryhoriya Skovorody", "2");
        university = new University("National University of Kyiv", "NaUKMA", address);
    }

    @Test
    void createFacultyTest() {
        Contact contact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        Faculty faculty = new Faculty("NAUKMA-FI", "Faculty Informatics", "FI", null, contact, university);
        Faculty create = serviceFaculty.create(faculty);
        assertNotNull(create);
        assertEquals("Faculty Informatics", create.getName());
    }

    @Test
    void updateFacultyTest() {
        Contact contact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        Faculty faculty = new Faculty("NAUKMA-FI", "Faculty CS", "FI", null, contact, university);
        Faculty create = serviceFaculty.create(faculty);

        create.setName("Faculty Informatics");
        Faculty updated = serviceFaculty.update(create);
        assertEquals("Faculty Informatics", updated.getName());
    }

    @Test
    void deleteFacultyTest() {
        Contact contact = new Contact("+380982345678", "CompScienceFaculty@naukma.edu.ua");
        Faculty faculty = new Faculty("NAUKMA-FI", "Faculty CS", "FI", null, contact, university);
        Faculty create = serviceFaculty.create(faculty);
        serviceFaculty.delete(create.getId());
        Optional<Faculty> facultyOptional = serviceFaculty.findById(create.getId());
        assertFalse(facultyOptional.isPresent());
    }
}