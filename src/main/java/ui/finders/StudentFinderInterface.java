package ui.finders;

import entity.*;
import entity.find_criteria.ContactFind;
import entity.find_criteria.IdsFind;
import entity.find_criteria.OrgStructure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface StudentFinderInterface {
    Optional<Student> chooseStudent(List<Student> students);

    List<Student> findByFormOfEducation();

    List<Student> findByRecordBook();

    List<Student> findByStudentStatus();

    List<Student> findByCourse();

    List<Student> findByStudentGroup();

    List<Student> findByUniqueCode();

    List<Student> findByName();

    List<Student> findBySurname();

    List<Student> findByPatronymic();

    List<Student> findByUniversity();

    List<Student> findByFaculty();

    List<Student> findByDepartment();

    Optional<Student> findByEmail();

    Optional<Student> findByPhone();

    List<Student> findByIds();

    Optional<Student> findByContact();

    List<Student> findByOrgStructure();

    Optional<Student> findAndSelect();

    List<Student> advancedSearch();

}
