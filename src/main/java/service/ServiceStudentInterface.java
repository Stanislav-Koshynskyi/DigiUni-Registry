package service;

import entity.*;

import java.util.List;
import java.util.Optional;

public interface ServiceStudentInterface {
    Student create(Student student);

    Student update(Student student);

    void delete(Long studentId);

    List<Student> findAll();

    List<Student> findByRecordBookNumber(String recordBookNumber);

    void transferStudent(Long studentId, StudentGroup newGroup, int newCourse);

    Optional<Student> findById(Long id);

    List<Student> findByFormOfEducation(FormOfEducation formOfEducation);

    List<Student> findByStudentStatus(StudentStatus status);

    List<Student> findByCourse(int courseMin, int courseMax);

    List<Student> findByStudentGroup(StudentGroup studentGroup);

    List<Student> findByUniqueCode(String uniqueCode);

    List<Student> findByName(String name);

    List<Student> findBySurname(String surname);

    List<Student> findByPatronymic(String patronymic);

    List<Student> findByUniversity(University university);

    List<Student> findByDepartment(Department department);

    List<Student> findByFaculty(Faculty faculty);

    Optional<Student> findByEmail(String email);

    Optional<Student> findByPhone(String phone);

    List<Student> findAllSortedByCourse();

    List<Student> StudentsByFacultySortedSurname(Long facultyId);

    List<Student> StudentsByDepartmentSortedByCourse(Long departmentId);

    List<Student> StudentsByDepartmentSortedBySurname(Long departmentId);

    boolean existsByUniqueCode(String uniqueCode, University university);

    boolean existsByRecordBookNumber(String recordBookNumber, University university);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
