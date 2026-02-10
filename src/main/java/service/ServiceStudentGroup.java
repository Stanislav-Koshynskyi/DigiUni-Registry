package service;

import entity.Student;
import entity.StudentGroup;
import entity.Department;
import entity.Teacher;
import repository.StudentGroupRepository;
import java.util.List;
import java.util.Optional;

public class ServiceStudentGroup implements ServiceStudentGroupInterface{
    private final StudentGroupRepository studentGroupRepository;

    public ServiceStudentGroup(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public StudentGroup create(StudentGroup group) {
        if (group == null)
            throw new IllegalArgumentException("Group can't be null");

        if (group.getName() == null || group.getName().isBlank())
            throw new IllegalArgumentException("Group name can't be empty!!!");

        return studentGroupRepository.save(group);
    }

    public StudentGroup update(StudentGroup group) {
        return studentGroupRepository.save(group);
    }

    public void delete(Long id) {
        studentGroupRepository.deleteById(id);
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
}
