package repository;

import entity.*;

import java.time.Year;
import java.util.List;

public interface StudentRepository extends PersonRepository<Student> {

    List<Student> findByFormOfEducation(FormOfEducation formOfEducation);

    List<Student> findByStudentStatus(StudentStatus studentStatus);

    List<Student> findByRecordBookNumber(String recordBookNumber);

    boolean existsByUniqueCode(String uniqueCode, University university);

    boolean existsByRecordBookNumber(String recordBookNumber, University university);

    List<Student> findByYearOfAdmission(Year yearOfAdmission);

    List<Student> findByCourse(int courseMin, int courseMax);

    List<Student> findByGroup(StudentGroup studentGroup);

    List<Student> findByUniversity(University university);

    List<Student> findByDepartment(Department department);

    List<Student> findByFaculty(Faculty faculty);
}
