package ui.finders;

import entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherFinderInterface {
    Optional<Teacher> findAndSelect();

    List<Teacher> findByName();

    List<Teacher> findBySurname();

    List<Teacher> findByPatronymic();
    Optional<Teacher> findByContact();

    List<Teacher> findByAcademicRank();

    List<Teacher> findByAcademicDegree();

    Optional<Teacher> findByUniqueCode();

    List<Teacher> findBySalary();

    List<Teacher> advancedSearch();
}
