package ui.finders;

import entity.Department;
import entity.Faculty;
import entity.StudentGroup;
import entity.University;
import entity.find_criteria.StudentGroupFind;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface StudentGroupFinderInterface {
    public Optional<StudentGroup> chooseStudentGroup(List<StudentGroup> studentGroups);
    public List<StudentGroup> findByName();
    public List<StudentGroup> findByFaculty();
    public List<StudentGroup> findByDepartment();
    public Optional<StudentGroup> findAndSelect();

    List<StudentGroup> findByUniversity();
}
