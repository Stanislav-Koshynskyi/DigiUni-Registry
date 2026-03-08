package service;

import entity.AcademicDegree;
import entity.AcademicRank;
import entity.Teacher;
import java.util.List;
import java.util.Optional;

public interface ServiceTeacherInterface {
    Teacher create(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(Long id);

    List<Teacher> findAll();

    Optional<Teacher> findById(Long id);

    List<Teacher> findByName(String name);

    List<Teacher> findBySurname(String surname);

    Optional<Teacher> findByEmail(String email);

    Optional<Teacher> findByPhone(String phone);

    List<Teacher> findByPatronymic(String patronymic);

    List<Teacher> findByAcademicRank(AcademicRank rank);

    List<Teacher> findByAcademicDegree(AcademicDegree degree);

    Optional<Teacher> findByUniqueCode(String uniqueCode);

    List<Teacher> findBySalary(Integer salaryMin, Integer salaryMax);
}
