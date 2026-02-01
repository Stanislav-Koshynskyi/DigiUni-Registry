package repository;

import entity.AcademicDegree;
import entity.AcademicRank;
import entity.Teacher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TeacherRepository extends PersonRepository<Teacher> {
    List<Teacher> findByAcademicRank(AcademicRank academicRank);

    List<Teacher> findByAcademicDegree(AcademicDegree academicDegree);

    List<Teacher> findByDateOfEmployment(LocalDate dateOfEmployment);

    List<Teacher> findBySalary(BigDecimal salary);
}
