package service;

import entity.Student;
import entity.StudentGroup;
import java.util.List;
import java.util.Optional;

public interface ServiceStudentInterface {
    Student create(Student student);

    Student update(Student student);

    void delete(Long studentId);

    List<Student> findAll();

    Optional<Student> findByRecordBookNumber(String recordBookNumber);

    void transferStudent(Long studentId, StudentGroup newGroup, int newCourse);
}
