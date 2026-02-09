package service;

import entity.Department;
import entity.Student;
import entity.StudentGroup;
import entity.Teacher;
import java.util.List;
import java.util.Optional;

public interface ServiceStudentGroupInterface {
    StudentGroup create(StudentGroup group);

    StudentGroup update(StudentGroup group);

    void delete(Long id);

    List<StudentGroup> findAll();

    List<StudentGroup> findByDepartment(Department department);

    List<StudentGroup> findByName(String name);

    Optional<StudentGroup> findByNameAndDepartment(String name, Department department);

    Optional<StudentGroup> findByHeadOfGroup(Teacher head);

    Optional<StudentGroup> findByGroupLeader(Student leader);
}
