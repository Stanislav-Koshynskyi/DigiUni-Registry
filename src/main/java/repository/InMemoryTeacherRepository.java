package repository;

import entity.AcademicDegree;
import entity.AcademicRank;
import entity.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class InMemoryTeacherRepository extends AbstractPersonRepository<Teacher> implements TeacherRepository {
    public InMemoryTeacherRepository() {
    }

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

    public List<Teacher> findBySalary(Integer salaryMin, Integer salaryMax) {
        Map<Long, Teacher> data = getData();
        return data.values().stream().filter(
                t -> t.getSalary().intValue() >= salaryMin && t.getSalary().intValue() <= salaryMax
        ).toList();
    }
}
