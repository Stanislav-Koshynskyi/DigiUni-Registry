package repository;

import entity.Faculty;
import entity.Teacher;
import entity.University;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class InMemoryFacultyRepository extends AbstractRepositoryByLong<Faculty> implements FacultyRepository {
    public InMemoryFacultyRepository() {}

    public List<Faculty> findByUniqueCode(String uniqueCode) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().filter(f -> f.getUniqueCode().equals(uniqueCode)).toList();
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
        return data.values().stream().filter(f -> Objects.equals(f.getDean().orElse(null), dean)).findFirst();
    }
    public boolean existsByDean(Teacher dean) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().anyMatch(f ->
                Objects.equals(f.getDean().orElse(null), dean));
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

    public boolean existsByUniqueCode(String uniqueCode, University university) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().anyMatch(f -> f.getUniqueCode().equalsIgnoreCase(uniqueCode) && f.getUniversity().equals(university));
    }

    public boolean existsByEmail(String email) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().anyMatch(f -> f.getContact().email().equals(email));
    }

    @Override
    public List<Faculty> findByShortName(String shortName) {
        Map<Long, Faculty> data = getData();
        return data.values().stream().filter(f -> f.getShortName().equals(shortName)).toList();
    }
}
