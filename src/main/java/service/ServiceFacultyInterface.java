package service;

import entity.Faculty;
import java.util.List;

public interface ServiceFacultyInterface {
    Faculty create(Faculty faculty);

    Faculty update(Faculty faculty);

    void delete(Long id);

    List<Faculty> findAll();
}
