package repository;

import entity.Department;
import entity.Student;
import entity.StudentGroup;
import entity.Teacher;

import java.util.*;

public class InMemoryStudentGroupRepository extends AbstractRepositoryByLong<StudentGroup> implements  StudentGroupRepository {
    public InMemoryStudentGroupRepository() {}

    public List<StudentGroup> findByDepartment(Department department) {
        Map<Long, StudentGroup> data = getData();
        return data.values().stream().filter(sg -> sg.getDepartment().equals(department)).toList();
    }
    public List<StudentGroup> findByName(String name) {
        Map<Long, StudentGroup> data = getData();
        return data.values().stream().filter(sg -> sg.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<StudentGroup>findByNameAndDepartment(String name, Department department) {
        Map<Long, StudentGroup> data = getData();
        return data.values().stream().filter(sg -> sg.getName().equalsIgnoreCase(name)
                && sg.getDepartment().equals(department)).findFirst();
    }
    public Optional<StudentGroup> findByHeadOfGroup(Teacher headOfDepartment) {
        Map<Long, StudentGroup> data = getData();
        return data.values().stream().filter(sg ->
                Objects.equals(sg.getHeadOfGroup().orElse(null), headOfDepartment)).findFirst();
    }

    public Optional<StudentGroup> findByGroupLeader(Student groupLeader) {
        Map<Long, StudentGroup> data = getData();
        return data.values().stream().filter(sg ->
                Objects.equals(sg.getGroupLeader().orElse(null), groupLeader)).findFirst();
    }
}
