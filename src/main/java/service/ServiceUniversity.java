package service;

import entity.Faculty;
import entity.University;
import exception.EntityInUseException;
import repository.FacultyRepository;
import repository.UniversityRepository;
import java.util.List;
import java.util.Optional;

public class ServiceUniversity implements ServiceUniversityInterface{

    private final UniversityRepository universityRepository;
    private final FacultyRepository facultyRepository;

    public ServiceUniversity(UniversityRepository universityRepository, FacultyRepository facultyRepository) {
        this.universityRepository = universityRepository;
        this.facultyRepository = facultyRepository;
    }

    public University create(University university) {

        if (universityRepository.existsByFullName(university.getFullName())) {
            throw new IllegalArgumentException("This full name already exists!!!");
        }

        if (universityRepository.existsByShortName(university.getShortName())) {
            throw new IllegalArgumentException("This short name already exists!!!");
        }

        return universityRepository.save(university);
    }

    public University update(University university) {
        return universityRepository.save(university);
    }

    public void delete(Long id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("University with id " + id + " not found!"));
        List<Faculty> faculties = facultyRepository.findByUniversity(university);
        if (!faculties.isEmpty()) {
            throw new EntityInUseException("This university has faculties, number: " + faculties.size());
        }
        universityRepository.deleteById(id);
    }

    public List<University> findAll() {
        return universityRepository.findAll();
    }

    public Optional<University> findByFullName(String fullName) {
        return universityRepository.findByFullName(fullName);
    }

    public Optional<University> findByShortName(String shortName) {
        return universityRepository.findByShortName(shortName);
    }

    public List<University> findByCity(String city) {
        return universityRepository.findByCity(city);
    }

    public Optional<University> findById(Long id) {
        return universityRepository.findById(id);
    }

    @Override
    public boolean existsByFullName(String fullName) {
        return universityRepository.existsByFullName(fullName);
    }

    @Override
    public boolean existsByShortName(String shortName) {
        return universityRepository.existsByShortName(shortName);
    }

    @Override
    public boolean existsByCity(String city) {
        return universityRepository.existsByCity(city);
    }
}
