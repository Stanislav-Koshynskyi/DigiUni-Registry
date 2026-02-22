package service;

import entity.Faculty;
import entity.University;
import repository.FacultyRepository;
import java.util.List;
import java.util.Optional;

public class ServiceFaculty implements ServiceFacultyInterface{
    private final FacultyRepository facultyRepository;

    public ServiceFaculty(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        if (facultyRepository.existsByUniqueCode(faculty.getUniqueCode())) {
            throw new IllegalArgumentException(
                    "Faculty already exists!!!"
            );
        }
        return facultyRepository.save(faculty);
    }

    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> findById(Long id) {
        return facultyRepository.findById(id);
    }

    public List<Faculty> findByUniqueCode(String uniqueCode) {
        return facultyRepository.findByUniqueCode(uniqueCode);
    }

    @Override
    public List<Faculty> findByName(String name) {
        return facultyRepository.findByName(name);
    }

    @Override
    public List<Faculty> findByShortName(String shortName) {
        return facultyRepository.findByShortName(shortName);
    }

    @Override
    public Optional<Faculty> findByEmail(String email) {
        return facultyRepository.findByEmail(email);
    }

    @Override
    public Optional<Faculty> findByPhoneNumber(String phoneNumber) {
        return findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Faculty> findByUniversity(University university) {
        return facultyRepository.findByUniversity(university);
    }
}
