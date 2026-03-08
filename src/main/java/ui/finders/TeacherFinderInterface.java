package ui.finders;

import entity.Teacher;

import java.util.Optional;

public interface TeacherFinderInterface {
    Optional<Teacher> findAndSelect();
}
