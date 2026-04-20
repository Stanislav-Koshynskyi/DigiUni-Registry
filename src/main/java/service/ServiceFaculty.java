package service;

import entity.Department;
import entity.Faculty;
import entity.University;
import exception.EntityInUseException;
import repository.DepartmentRepository;
import repository.FacultyRepository;
import java.util.List;
import java.util.Optional;

public class ServiceFaculty implements ServiceFacultyInterface{
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;

    public ServiceFaculty(FacultyRepository facultyRepository, DepartmentRepository departmentRepository) {
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
    }

    public Faculty create(Faculty faculty) {
        University university = faculty.getUniversity();
        if (facultyRepository.existsByUniqueCode(faculty.getUniqueCode(), university))
            throw new IllegalArgumentException("Faculty already exists!!!");
        return facultyRepository.save(faculty);
    }

    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        Faculty faculty = facultyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Faculty with id: " + id + " not found!"));
        List<Department> departments = departmentRepository.findByFaculty(faculty);
        if (!departments.isEmpty()) {
            throw new EntityInUseException("This university has departments, number: " + departments.size());
        }
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
        return facultyRepository.findByPhone(phoneNumber);
    }

    @Override
    public List<Faculty> findByUniversity(University university) {
        return facultyRepository.findByUniversity(university);
    }

    public boolean existsByUniqueCode(String uniqueCode, University university) {
        return facultyRepository.existsByUniqueCode(uniqueCode, university);
    }

    @Override
    public boolean existsByEmail(String email) {
        return facultyRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return facultyRepository.existsByPhone(phone);
    }
}
