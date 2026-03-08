package repository;

import entity.*;

import java.util.List;
import java.util.Optional;

public interface StudentGroupRepository extends Repository<StudentGroup, Long> {
    List<StudentGroup> findByDepartment(Department department);
    List<StudentGroup> findByName(String name);

    Optional<StudentGroup> findByNameAndDepartment(String name, Department department);
    Optional<StudentGroup> findByHeadOfGroup(Teacher headOfDepartment);

    Optional<StudentGroup> findByGroupLeader(Student groupLeader);

    List<StudentGroup> findByUniversity(University university);

    List<StudentGroup> findByFaculty(Faculty faculty);
}
