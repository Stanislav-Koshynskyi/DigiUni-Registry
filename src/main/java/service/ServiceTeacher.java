package service;

import entity.Teacher;
import repository.TeacherRepository;
import java.util.List;

public class ServiceTeacher implements ServiceTeacherInterface {
    private final TeacherRepository teacherRepository;

    public ServiceTeacher(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
}
