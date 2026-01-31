package repository;

import entity.Department;
import entity.Faculty;
import entity.Teacher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DepartmentRepository extends AbstractRepositoryByLong<Department> {
    public DepartmentRepository() {}

    public Optional<Department> findByUniqueCode(String code){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d -> d.getUniqueCode().equals(code)).findFirst();
    }

    public boolean existsByUniqueCode(String code){
        Map<Long, Department> data = getData();
        return data.values().stream().anyMatch(d -> d.getUniqueCode().equals(code));
    }

    public List<Department> findByName(String name){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d -> d.getName().equals(name)).toList();
    }

    public Optional<Department> findByNameAndFaculty(String name, Faculty faculty){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d ->
                d.getName().equals(name) &&
                        d.getFaculty().equals(faculty)).findFirst();
    }

    public List<Department> findByFaculty(Faculty faculty){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d -> d.getFaculty().equals(faculty)).toList();
    }

    public Optional<Department> findByHeadOfDepartment(Teacher headOfDepartment){
        Map<Long, Department> data = getData();
        return data.values().stream().filter(d -> d.getHeadOfDepartment().orElse(null).equals(headOfDepartment)).findFirst();
    }

    public boolean existsByHeadOfDepartment(Teacher headOfDepartment){
        Map<Long, Department> data = getData();
        return data.values().stream().anyMatch(d -> d.getHeadOfDepartment().orElse(null).equals(headOfDepartment));
    }
}

