package service;

import entity.*;
import repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ServiceStudent implements ServiceStudentInterface{
    private final StudentRepository studentRepository;

    public ServiceStudent(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        if (studentRepository.existsByUniqueCode(student.getUniqueCode())) {
            throw new IllegalArgumentException("Student with this unique code already exists!");
        }
        if (studentRepository.existsByRecordBookNumber(student.getRecordBookNumber())) {
            throw new IllegalArgumentException("Student with this record book number already exists!");
        }

        Student student1 = studentRepository.save(student);
        student1.getGroup().addStudent(student1);
        return student1;
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }


    public void delete(Long studentId) {
        studentRepository.deleteById(studentId);
    }


    public List<Student> findAll() {
        return studentRepository.findAll();
    }


    public List<Student> findByRecordBookNumber(String recordBookNumber) {
        return studentRepository.findByRecordBookNumber(recordBookNumber);
    }


    public void transferStudent(Long studentId, StudentGroup newGroup, int newCourse) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found!!!"));

        student.setGroup(newGroup);
        student.getGroup().addStudent(student);
        student.setCourse(newCourse);

        studentRepository.save(student);
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findByFormOfEducation(FormOfEducation formOfEducation) {
        return studentRepository.findByFormOfEducation(formOfEducation);
    }

    @Override
    public List<Student> findByStudentStatus(StudentStatus status) {
        return studentRepository.findByStudentStatus(status);
    }

    @Override
    public List<Student> findByCourse(int courseMin, int courseMax) {
        return studentRepository.findByCourse(courseMin, courseMax);
    }

    @Override
    public List<Student> findByStudentGroup(StudentGroup studentGroup) {
        return studentRepository.findByGroup(studentGroup);
    }

    @Override
    public List<Student> findByUniqueCode(String uniqueCode) {
        return studentRepository.findByUniqueCode(uniqueCode);
    }

    @Override
    public List<Student> findByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<Student> findBySurname(String surname) {
        return studentRepository.findBySurname(surname);
    }

    @Override
    public List<Student> findByPatronymic(String patronymic) {
        return studentRepository.findByPatronymic(patronymic);
    }

    @Override
    public List<Student> findByUniversity(University university) {
        return studentRepository.findByUniversity(university);
    }

    @Override
    public List<Student> findByDepartment(Department department) {
        return studentRepository.findByDepartment(department);
    }

    @Override
    public List<Student> findByFaculty(Faculty faculty) {
        return studentRepository.findByFaculty(faculty);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public Optional<Student> findByPhone(String phone) {
        return studentRepository.findByPhone(phone);
    }

    public List<Student> findAllSortedByCourse() {
        return studentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Student::getCourse))
                .toList();
    }

    public List<Student> StudentsByFacultySortedSurname(Long facultyId) {

        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getGroup().getDepartment().getFaculty().getId().equals(facultyId))
                .sorted(Comparator.comparing(s -> s.getFullName().surname()))
                .toList();
    }

    public List<Student> StudentsByDepartmentSortedByCourse(Long departmentId) {
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getGroup().getDepartment().getId().equals(departmentId))
                .sorted(Comparator.comparing(Student::getCourse))
                .toList();
    }

    public List<Student> StudentsByDepartmentSortedBySurname(Long departmentId) {
        return studentRepository.findAll()
                .stream()
                .filter(student ->student.getGroup().getDepartment().getId().equals(departmentId))
                .sorted(Comparator.comparing(s -> s.getFullName().surname()))
                .toList();
    }

    public boolean existsByUniqueCode(String uniqueCode) {
        return studentRepository.existsByUniqueCode(uniqueCode);
    }

    public boolean existsByRecordBookNumber(String recordBookNumber) {return studentRepository.existsByRecordBookNumber(recordBookNumber);}
}
