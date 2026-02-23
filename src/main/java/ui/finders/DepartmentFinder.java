package ui.finders;

import entity.Department;
import entity.Faculty;
import entity.University;
import entity.find_criteria.DepartmentFind;
import entity.find_criteria.DepartmentForAdvancedFind;
import entity.find_criteria.FacultyFind;
import service.ServiceDepartmentInterface;
import ui.InputReader;

import java.util.*;

public class DepartmentFinder {
    private final InputReader inputReader;
    private final UniversityFinderInterface universityFinder;
    private final FacultyFinderInterface facultyFinder;
    private final ServiceDepartmentInterface serviceDepartment;
    public DepartmentFinder(InputReader inputReader, UniversityFinderInterface universityFinder,
                            FacultyFinderInterface facultyFinder, ServiceDepartmentInterface serviceDepartment) {
        this.inputReader = inputReader;
        this.universityFinder = universityFinder;
        this.facultyFinder = facultyFinder;
        this.serviceDepartment = serviceDepartment;
    }
    Optional<Department> chooseDepartment(List<Department> departments) {
        if (departments == null || departments.isEmpty()) {
            System.out.println("No Department found");
            return Optional.empty();
        }
        else{
            return Optional.of(inputReader.readChoose(departments, "Choose department"));
        }
    }
    List<Department> findByUniqueCode() {
        String code = inputReader.readString("Enter unique code");
        return serviceDepartment.findByUniqueCode(code);
    }
    List<Department> findByName() {
        String name = inputReader.readString("Enter name");
        return serviceDepartment.findByName(name);
    }
    List<Department> findByShortName() {
        String shortName = inputReader.readString("Enter short name");
        return serviceDepartment.findByShortName(shortName);
    }
    List<Department> findByFaculty() {
        Optional<Faculty> faculty = facultyFinder.findAndSelect();
        if (faculty.isEmpty()) {
            return Collections.emptyList();
        }
        return serviceDepartment.findByFaculty(faculty.get());
    }
    List<Department> findByUniversity() {
        Optional<University> university = universityFinder.findAndSelect();
        if (university.isEmpty()) {
            return Collections.emptyList();
        }
        return serviceDepartment.findByUniversity(university.get());
    }
    Optional<Department> findAndSelect() {
        Optional<Department> departmentOptional;
        while (true) {
            System.out.println("Choose department");
            DepartmentFind departmentFind = inputReader.readChoose(
                    Arrays.stream(DepartmentFind.values()).toList(), "Choose criteria"
            );
            switch (departmentFind) {

                case SHOW_ALL -> {
                    departmentOptional = chooseDepartment(serviceDepartment.findAll());
                }
                case CANCEL -> {
                    return Optional.empty();
                }
                case FACULTY -> {
                    departmentOptional = chooseDepartment(findByFaculty());
                }
                case UNIVERSITY -> {
                    departmentOptional = chooseDepartment(findByUniversity());
                }
                case UNIQUE_CODE -> {
                    departmentOptional = chooseDepartment(findByUniqueCode());
                }
                case NAME -> {
                    departmentOptional = chooseDepartment(findByName());
                }
                case SHORT_NAME -> {
                    departmentOptional = chooseDepartment(findByShortName());
                }
                case null, default -> {
                    departmentOptional = Optional.empty();
                }
            }
            if (departmentOptional.isEmpty()) {
                System.out.println("try again");
            }
            else{
                return departmentOptional;
            }
        }
    }
    List<Department> advancedSearch() {
        Set<DepartmentForAdvancedFind> activeFilters = new LinkedHashSet<>();
        while (true) {
            int i = 1;

            for (DepartmentForAdvancedFind find : DepartmentForAdvancedFind.values()) {
                System.out.println(i++ + ": " + find);
            }
            System.out.println(i++ + ": Reset setting");
            System.out.println(i++ + ": Searching for faculty");
            System.out.println(i + ": Cancel");
            System.out.println("Active filters: " + activeFilters);
            int choose = inputReader.readIntInRange("Choose param or start search", 1, i);
            if (choose == i) {
                return Collections.emptyList();
            } else if (choose == i - 1) {
                return advancedFilter(activeFilters.stream().toList());
            } else if (choose == i - 2) {
                activeFilters = new LinkedHashSet<>();
            } else {
                activeFilters.add(DepartmentForAdvancedFind.values()[choose - 1]);
            }
        }
    }
    List<Department> advancedFilter(List<DepartmentForAdvancedFind> filters) {
        List<Department> departments = serviceDepartment.findAll();
        for (DepartmentForAdvancedFind find : filters) {
            if (departments.isEmpty()) return departments;
            switch (find) {
                case UNIVERSITY -> {
                    departments = filterByUniversity(departments);
                }
                case FACULTY -> {
                    departments = filterByFaculty(departments);
                }
                case SHORT_NAME -> {
                    departments = filterByShortName(departments);
                }
                case UNIQUE_CODE -> {
                    departments = filterByUniqueCode(departments);
                }
                case NAME -> {
                    departments = filterByName(departments);
                }
                case null, default ->{
                    departments = Collections.emptyList();
                }
            }
        }
        return departments;
    }
    List<Department> filterByUniqueCode(List<Department> departments) {
        String uniqueCode = inputReader.readString("Enter unique code");
        return departments.stream().filter(
                d -> d.getUniqueCode().equals(uniqueCode)
        ).toList();
    }
    List<Department> filterByName(List<Department> departments) {
        String name = inputReader.readString("Enter name");
        return departments.stream().filter(
                d -> d.getName().equals(name)
        ).toList();
    }
    List<Department> filterByShortName(List<Department> departments) {
        String shortName = inputReader.readString("Enter short name");
        return departments.stream()
                .filter(d -> d.getShortName().equals(shortName))
                .toList();
    }
    List<Department> filterByFaculty(List<Department> departments) {
        Optional<Faculty> facultyOptional = facultyFinder.findAndSelect();
        if (facultyOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Faculty faculty = facultyOptional.get();
        return departments.stream().filter(
                f -> f.getFaculty().equals(faculty)
        ).toList();
    }
    List<Department> filterByUniversity(List<Department> departments) {
        Optional<University> optionalUniversity = universityFinder.findAndSelect();
        if (optionalUniversity.isEmpty()) {
            return Collections.emptyList();
        }
        University university = optionalUniversity.get();
        return departments.stream().filter(
                f -> f.getFaculty().getUniversity().equals(university)
        ).toList();
    }

}
