package repository;

import entity.University;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryUniversityRepository extends AbstractRepositoryByLong<University> implements  UniversityRepository {
    public InMemoryUniversityRepository() {}

    public Optional<University> findByFullName(String fullName) {
        Map<Long, University> data = getData();
        return data.values().stream().filter(u -> u.getFullName().equals(fullName)).findFirst();
    }

    public boolean existsByFullName(String fullName) {
        Map<Long, University> data = getData();
        return data.values().stream().anyMatch(u -> u.getFullName().equalsIgnoreCase(fullName));
    }

    public Optional<University> findByShortName(String shortName) {
        Map<Long, University> data = getData();
        return data.values().stream().filter(u -> u.getShortName().equalsIgnoreCase(shortName)).findFirst();
    }

    public  boolean existsByShortName(String shortName) {
        Map<Long, University> data = getData();
        return data.values().stream().anyMatch(u -> u.getShortName().equalsIgnoreCase(shortName));
    }

    public List<University> findByCity(String city){
        Map<Long, University> data = getData();
        return data.values().stream().filter(u -> u.getAddress().city().equalsIgnoreCase(city)).toList();
    }
}
