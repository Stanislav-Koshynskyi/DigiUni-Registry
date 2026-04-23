package ui.pages;

import entity.Faculty;
import entity.Right;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;

import java.util.Comparator;
import java.util.List;

public class SortFacultyPage extends BasePage {
    private List<Faculty> faculties;

    public SortFacultyPage(InputReader inputReader, List<Faculty> faculties) {
        super(inputReader);
        this.faculties = faculties;
    }

    @Override
    public String getTitle() {return "Sort Faculties";}

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Sort faculties by name", Right.FIND, this::sortByName),
                new MenuItem("Sort faculties by short name", Right.FIND, this::sortByShortName),
                new MenuItem("Sort faculties by unique code", Right.FIND, this::sortByUniqueCode),
                new MenuItem("Sort faculties by university", Right.FIND, this::sortByUniversity)
        );
    }

    private Page sortByUniversity() {
        faculties = faculties.stream().sorted(Comparator.comparing(f -> f.getUniversity().getFullName())).toList();
        return showFaculties();
    }

    private Page sortByUniqueCode() {
        faculties = faculties.stream().sorted(Comparator.comparing(Faculty::getUniqueCode)).toList();
        return showFaculties();
    }

    private Page sortByShortName() {
        faculties = faculties.stream().sorted(Comparator.comparing(Faculty::getShortName)).toList();
        return showFaculties();
    }

    private Page sortByName() {
        faculties = faculties.stream().sorted(Comparator.comparing(Faculty::getName)).toList();
        return showFaculties();
    }

    private Page showFaculties() {
        for (Faculty faculty : faculties)
            inputReader.println("id - " + faculty.getId() + ", " + faculty);
        return this;
    }
}
