package service;

import entity.Student;
import entity.StudentGroup;
import repository.StudentRepository;
import java.util.List;
import java.util.Optional;

public class ServiceStudent implements ServiceStudentInterface{
    private final StudentRepository studentRepository;

    public ServiceStudent(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        if (studentRepository.existsByRecordBookNumber(student.getRecordBookNumber())) {
            throw new IllegalArgumentException("Student already exists!!!");
        }
        return studentRepository.save(student);
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


    public Optional<Student> findByRecordBookNumber(String recordBookNumber) {
        return studentRepository.findByRecordBookNumber(recordBookNumber);
    }


    public void transferStudent(Long studentId, StudentGroup newGroup, int newCourse) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found!!!"));

        student.setGroup(newGroup);
        student.setCourse(newCourse);

        studentRepository.save(student);
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }
}
