package repository;

import entity.Department;
import entity.Faculty;
import entity.Teacher;
import entity.University;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class FacultyRepository extends AbstractRepositoryByLong<Faculty> {
    public FacultyRepository() {}

    public Optional<Faculty> findByUniqueCode(String uniqueCode) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().filter(f -> f.getUniqueCode().equals(uniqueCode)).findFirst();
    }

    public boolean existsByUniqueCode(String uniqueCode) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().anyMatch(f -> f.getUniqueCode().equals(uniqueCode));
    }

    public List<Faculty> findByName(String name) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().filter(f -> f.getName().equalsIgnoreCase(name)).toList();
    }


    public List<Faculty> findByUniversity(University university) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().filter(f -> f.getUniversity().equals(university)).toList();
    }

    public Optional<Faculty> findByDean(Teacher dean) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().filter(f -> f.getDean().orElse(null).equals(dean)).findFirst();
    }
    public boolean existsByDean(Teacher dean) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().anyMatch(f ->
                f.getDean().orElse(null).equals(dean));
    }

    public Optional<Faculty> findByPhone(String phone) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().filter(f -> f.getContact().phone().equals(phone)).findFirst();
    }

    public boolean existsByPhone(String phone) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().anyMatch(f -> f.getContact().phone().equals(phone));
    }

    public Optional<Faculty> findByEmail(String email) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().filter(f -> f.getContact().email().equals(email)).findFirst();
    }

    public boolean existsByEmail(String email) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().anyMatch(f -> f.getContact().email().equals(email));
    }
}
