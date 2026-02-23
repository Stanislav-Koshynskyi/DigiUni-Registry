package repository;

import entity.Department;
import entity.Faculty;
import entity.Teacher;
import entity.University;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryDepartmentRepository extends AbstractRepositoryByLong<Department> implements  DepartmentRepository {
    public InMemoryDepartmentRepository() {}

    public List<Department> findByUniqueCode(String code){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d -> d.getUniqueCode().equals(code)).toList();
    }

    public boolean existsByUniqueCode(String code){
        Map<Long, Department> data = getData();
        return data.values().stream().anyMatch(d -> d.getUniqueCode().equals(code));
    }

    public List<Department> findByName(String name){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d -> d.getName().equalsIgnoreCase(name)).toList();
    }

    public Optional<Department> findByNameAndFaculty(String name, Faculty faculty){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d ->
                d.getName().equalsIgnoreCase(name) &&
                        d.getFaculty().equals(faculty)).findFirst();
    }

    public List<Department> findByFaculty(Faculty faculty){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d -> d.getFaculty().equals(faculty)).toList();
    }

    public Optional<Department> findByHeadOfDepartment(Teacher headOfDepartment){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d -> Objects.equals(d.getHeadOfDepartment().orElse(null), headOfDepartment)).findFirst();
    }

    public boolean existsByHeadOfDepartment(Teacher headOfDepartment){
        Map<Long, Department> data = getData();
        return data.values().stream().anyMatch(d -> Objects.equals(d.getHeadOfDepartment().orElse(null), headOfDepartment));
    }

    @Override
    public List<Department> findByUniversity(University university) {
        Map<Long, Department> data = getData();
        return data.values().stream().filter(
                d -> d.getFaculty().getUniversity().equals(university))
                .toList();
    }

    @Override
    public List<Department> findByShortName(String shortName) {
        Map<Long, Department> data = getData();
        return data.values().stream().filter(
                d -> d.getShortName().equals(shortName)
        ).toList();
    }
}

