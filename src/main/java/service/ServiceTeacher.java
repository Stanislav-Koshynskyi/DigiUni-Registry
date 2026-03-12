package service;

import entity.AcademicDegree;
import entity.AcademicRank;
import entity.Teacher;
import repository.TeacherRepository;
import java.util.List;
import java.util.Optional;

public class ServiceTeacher implements ServiceTeacherInterface {
    private final TeacherRepository teacherRepository;

    public ServiceTeacher(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher create(Teacher teacher) {
        if (findByUniqueCode(teacher.getUniqueCode()).isPresent())
            throw new IllegalArgumentException("Teacher already exists with unique code: " + teacher.getUniqueCode());
        if (findByEmail(teacher.getContact().email()).isPresent())
            throw new IllegalArgumentException("Contact teacher already exists with email: " + teacher.getContact().email());
        if (findByPhone(teacher.getContact().phone()).isPresent())
            throw new IllegalArgumentException("Contact teacher already exists with phone: " + teacher.getContact().phone());

        return teacherRepository.save(teacher);
    }

    public Teacher update(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public List<Teacher> findByName(String name) {
        return teacherRepository.findByName(name);
    }

    @Override
    public List<Teacher> findBySurname(String surname) {
        return teacherRepository.findBySurname(surname);
    }

    @Override
    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public Optional<Teacher> findByPhone(String phone) {
        return teacherRepository.findByPhone(phone);
    }

    @Override
    public List<Teacher> findByPatronymic(String patronymic) {
        return teacherRepository.findByPatronymic(patronymic);
    }

    @Override
    public List<Teacher> findByAcademicRank(AcademicRank rank) {
        return teacherRepository.findByAcademicRank(rank);
    }

    @Override
    public List<Teacher> findByAcademicDegree(AcademicDegree degree) {
        return teacherRepository.findByAcademicDegree(degree);
    }

    @Override
    public Optional<Teacher> findByUniqueCode(String uniqueCode) {
        return teacherRepository.findByUniqueCode(uniqueCode).stream().findFirst();
    }

    @Override
    public List<Teacher> findBySalary(Integer salaryMin, Integer salaryMax) {
        return teacherRepository.findBySalary(salaryMin,salaryMax);
    }

    @Override
    public boolean existsByUniqueCode(String uniqueCode) {
        return teacherRepository.findByUniqueCode(uniqueCode).stream().findAny().isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return teacherRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean existsByPhone(String phone) {
        return teacherRepository.findByPhone(phone).isPresent();
    }
}
