package repository;

import entity.AcademicDegree;
import entity.AcademicRank;
import entity.Teacher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherRepository extends AbstractPersonRepository<Teacher> {
    public TeacherRepository() {}
    public List<Teacher> findByAcademicRank(AcademicRank academicRank) {
        Map<Long, Teacher> data = getData();
        return data.values().stream().filter(t -> t.getAcademicRank().equals(academicRank)).toList();
    }

    public List<Teacher> findByAcademicDegree(AcademicDegree academicDegree) {
        Map<Long, Teacher> data = getData();
        return data.values().stream().filter(p -> p.getAcademicDegree().equals(academicDegree)).toList();
    }

    public List<Teacher> findByDateOfEmployment(LocalDate dateOfEmployment) {
        Map<Long, Teacher> data = getData();
        return data.values().stream().filter(p -> p.getDateOfEmployment().equals(dateOfEmployment)).toList();
    }

    public List<Teacher> findBySalary(BigDecimal salary) {
        Map<Long, Teacher> data = getData();
        return data.values().stream().filter(p -> p.getSalary().equals(salary)).toList();
    }
}
