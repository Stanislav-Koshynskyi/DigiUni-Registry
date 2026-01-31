package repository;

import entity.Department;
import entity.StudentGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentGroupRepository extends AbstractRepositoryByLong<StudentGroup> {
    public StudentGroupRepository() {}

    public List<StudentGroup> findByDepartment(Department department) {
        Map<Long, StudentGroup> data = getData();
        return data.values().stream().filter(sg -> sg.getDepartment.equals(department)).toList();
    }
    /*
        private Long id;
    private String name;
    private Teacher headOfGroup;
    private Student groupLeader;
    private final HashSet<Student> students;
    private Department department
     */
}
