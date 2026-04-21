package service;

import entity.*;
import exception.EntityInUseException;
import lombok.extern.slf4j.Slf4j;
import repository.StudentGroupRepository;
import java.util.List;
import java.util.Optional;
@Slf4j
public class ServiceStudentGroup implements ServiceStudentGroupInterface{
    private final StudentGroupRepository studentGroupRepository;

    public ServiceStudentGroup(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public StudentGroup create(StudentGroup group) {
        if (group == null) {
            log.error("Student group is null");
            throw new IllegalArgumentException("Group can't be null");
        }
        if (group.getName() == null || group.getName().isBlank()) {
            log.error("Student group name is blank");
            throw new IllegalArgumentException("Group name can't be empty!!!");
        }
        log.info("Create student group with name {}", group.getName());
        return studentGroupRepository.save(group);
    }

    public StudentGroup update(StudentGroup group) {
        return studentGroupRepository.save(group);
    }

    public void delete(Long id) {
        StudentGroup studentGroup = studentGroupRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("Student group with id " + id + " does not exist"));
        if (!studentGroup.getStudents().isEmpty()){
            log.warn("Trying to delete student group with id {}, but has students", id);
            throw new EntityInUseException("This university has students, number: " + studentGroup.getStudents().size());
        }
        studentGroupRepository.deleteById(id);
        log.info("Student group with id {} has been deleted", id);
    }

    public List<StudentGroup> findAll() {
        return studentGroupRepository.findAll();
    }

    public List<StudentGroup> findByDepartment(Department department) {
        return studentGroupRepository.findByDepartment(department);
    }

    public List<StudentGroup> findByName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Group name can't be empty!!!");

        return studentGroupRepository.findByName(name);
    }

    public Optional<StudentGroup> findByNameAndDepartment(String name, Department department) {
        return studentGroupRepository.findByNameAndDepartment(name, department);
    }

    public Optional<StudentGroup> findByHeadOfGroup(Teacher head) {
        return studentGroupRepository.findByHeadOfGroup(head);
    }

    public Optional<StudentGroup> findByGroupLeader(Student leader) {
        return studentGroupRepository.findByGroupLeader(leader);
    }

    public Optional<StudentGroup> findById(Long id) {
        return studentGroupRepository.findById(id);
    }

    @Override
    public List<StudentGroup> findByUniversity(University university) {
        return studentGroupRepository.findByUniversity(university);
    }

    @Override
    public List<StudentGroup> findByFaculty(Faculty faculty) {
        return studentGroupRepository.findByFaculty(faculty);
    }
}
