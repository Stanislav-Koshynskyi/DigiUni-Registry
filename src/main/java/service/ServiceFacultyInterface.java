package service;

import entity.Faculty;
import java.util.List;
import java.util.Optional;

public interface ServiceFacultyInterface {
    Faculty create(Faculty faculty);

    Faculty update(Faculty faculty);

    void delete(Long id);

    List<Faculty> findAll();

    Optional<Faculty> findById(Long id);
}
