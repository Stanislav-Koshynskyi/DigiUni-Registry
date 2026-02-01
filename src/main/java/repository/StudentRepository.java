package repository;

import entity.*;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends PersonRepository<Student> {

    List<Student> findByFormOfEducation(FormOfEducation formOfEducation);

    List<Student> findByStudentStatus(StudentStatus studentStatus);

    Optional<Student> findByRecordBookNumber(String recordBookNumber);

    boolean existsByRecordBookNumber(String recordBookNumber);

    List<Student> findByYearOfAdmission(Year yearOfAdmission);

    List<Student> findByCourse(int course);

    List<Student> findByGroup(StudentGroup studentGroup);

}
