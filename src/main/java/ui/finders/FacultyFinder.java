package ui.finders;

import entity.Faculty;
import entity.University;
import entity.find_criteria.FacultyFind;
import entity.find_criteria.FacultyForAdvancedFind;
import service.ServiceFacultyInterface;
import ui.InputReader;

import java.util.*;

public class FacultyFinder implements FacultyFinderInterface {
    private final InputReader inputReader;
    private final ServiceFacultyInterface serviceFaculty;
    private final UniversityFinderInterface universityFinder;

    public FacultyFinder(ServiceFacultyInterface serviceFaculty, InputReader inputReader,
                         UniversityFinderInterface universityFinder) {
        this.inputReader = inputReader;
        this.serviceFaculty = serviceFaculty;
        this.universityFinder = universityFinder;
    }

    public List<Faculty> findByUniqueCode() {
        String uniqueCode = inputReader.readString("Enter unique code");
        return serviceFaculty.findByUniqueCode(uniqueCode);
    }

    public List<Faculty> findByName() {
        String name = inputReader.readString("Enter name");
        return serviceFaculty.findByName(name);
    }

    public List<Faculty> findByShortName() {
        String shortName = inputReader.readString("Enter short name");
        return serviceFaculty.findByShortName(shortName);
    }

    public Optional<Faculty> findByPhoneNumber() {
        String phoneNumber = inputReader.readString("Enter phone number");
        return serviceFaculty.findByPhoneNumber(phoneNumber);
    }

    public Optional<Faculty> findByEmail() {
        String email = inputReader.readString("Enter email");
        return serviceFaculty.findByEmail(email);
    }

    public List<Faculty> findByUniversity() {
        Optional<University> optionalUniversity = universityFinder.findAndSelect();
        if (optionalUniversity.isEmpty()) return List.of();
        return serviceFaculty.findByUniversity(optionalUniversity.get());
    }

    private List<Faculty> advancedFilter(List<FacultyForAdvancedFind> facultyFinds) {
        List<Faculty> faculties = serviceFaculty.findAll();
        for (FacultyForAdvancedFind facultyFind : facultyFinds) {
            switch (facultyFind) {
                case UNIVERSITY -> faculties = filterByUniversity(faculties);
                case NAME -> faculties = filterByName(faculties);
                case SHORT_NAME -> faculties = filterByShortName(faculties);
                case UNIQUE_CODE -> faculties = filterByUniqueCode(faculties);
                case null -> {
                    return List.of();
                }
            }
        }
        return faculties;
    }

    private List<Faculty> filterByUniqueCode(List<Faculty> faculties) {
        String uniqueCode = inputReader.readString("Enter unique code");
        return faculties.stream().filter(f -> f.getUniqueCode().equals(uniqueCode)).toList();
    }

    private List<Faculty> filterByName(List<Faculty> faculties) {
        String name = inputReader.readString("Enter name");
        return faculties.stream().filter(f -> f.getName().equals(name)).toList();
    }

    private List<Faculty> filterByShortName(List<Faculty> faculties) {
        String shortName = inputReader.readString("Enter short name");
        return faculties.stream().filter(f -> f.getShortName().equals(shortName)).toList();
    }

    private List<Faculty> filterByUniversity(List<Faculty> faculties) {
        Optional<University> optionalUniversity = universityFinder.findAndSelect();
        if (optionalUniversity.isEmpty()) return List.of();
        else {
            return faculties.stream().filter(f ->
                    f.getUniversity().equals(optionalUniversity.get())).toList();
        }
    }

    public Optional<Faculty> chooseFaculty(List<Faculty> faculties) {
        if (faculties.isEmpty()) {
            System.out.println("No faculty found");
            return Optional.empty();
        } else {
            return Optional.ofNullable(
                    inputReader.readChoose(faculties, "Choose faculty")
            );
        }

    }

    public Optional<Faculty> findAndSelect() {
        System.out.println("Choose faculty");
        FacultyFind facultyFind;
        Optional<Faculty> result = Optional.empty();
        while (true) {
            facultyFind = inputReader.readChoose(List.of(FacultyFind.values()),
                    "Choose criteria");
            switch (facultyFind) {
                case UNIVERSITY -> {
                    result = chooseFaculty(findByUniversity());
                }
                case UNIQUE_CODE -> {
                    result = chooseFaculty(findByUniqueCode());
                }
                case PHONE -> {
                    result = findByPhoneNumber();
                }
                case EMAIL -> {
                    result = findByEmail();
                }
                case NAME -> {
                    result = chooseFaculty(findByName());
                }
                case SHORT_NAME -> {
                    result = chooseFaculty(findByShortName());
                }
                case CANCEL -> {
                    return Optional.empty();
                }
                case SHOW_ALL -> {
                    result = chooseFaculty(serviceFaculty.findAll());
                }
                case ADVANCED -> {
                    result = chooseFaculty(advancedSearch());
                }
            }
            if (result.isPresent()) {
                return result;
            } else {
                System.out.println("No faculty found, try again");
            }
        }
    }

    public List<Faculty> advancedSearch() {
        Set<FacultyForAdvancedFind> facultyForAdvancedFinds = new LinkedHashSet<>();
        while (true) {
            int i = 1;
            for (FacultyForAdvancedFind facultyForAdvancedFind : FacultyForAdvancedFind.values()) {
                System.out.println(i++ + ": " + facultyForAdvancedFind);
            }
            System.out.println(i++ + ": Reset setting");
            System.out.println(i++ + ": Searching for faculty");
            System.out.println(i + ": Cancel");
            System.out.println("Active filters: " + facultyForAdvancedFinds);
            int choose = inputReader.readIntInRange("Choose param or start search",
                    1, i);
            if (choose <= i - 3) {
                facultyForAdvancedFinds.add(FacultyForAdvancedFind.values()[choose - 1]);
            } else if (choose == i - 2) {
                facultyForAdvancedFinds = new LinkedHashSet<>();
            } else if (choose == i - 1) {
                return advancedFilter(facultyForAdvancedFinds.stream().toList());
            } else {
                return new ArrayList<>();
            }
        }
    }
}

