package service;

import entity.Teacher;
import java.util.List;
import java.util.Optional;

public interface ServiceTeacherInterface {
    Teacher create(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(Long id);

    List<Teacher> findAll();

    Optional<Teacher> findById(Long id);
}
