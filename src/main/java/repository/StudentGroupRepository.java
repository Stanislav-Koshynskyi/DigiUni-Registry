package repository;

import entity.Department;
import entity.Student;
import entity.StudentGroup;
import entity.Teacher;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface StudentGroupRepository extends Repository<StudentGroup, Long> {
    List<StudentGroup> findByDepartment(Department department);
    List<StudentGroup> findByName(String name);

    Optional<StudentGroup> findByNameAndDepartment(String name, Department department);
    Optional<StudentGroup> findByHeadOfGroup(Teacher headOfDepartment);

    Optional<StudentGroup> findByGroupLeader(Student groupLeader);
}
