package ui.pages;

import entity.Right;
import entity.University;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;

import java.util.Comparator;
import java.util.List;

public class SortUniversityPage extends BasePage {
    private List<University> universities;

    public SortUniversityPage(InputReader inputReader, List<University> universities) {
        super(inputReader);
        this.universities = universities;
    }

    public String getTitle() {return "Sort Universities";}

    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Sort university by full name", Right.FIND, this::sortByFullName),
                new MenuItem("Sort university by short name", Right.FIND, this::sortByShortName)
        );
    }

    private Page sortByShortName() {
        universities = universities.stream().sorted(Comparator.comparing(University::getShortName)).toList();
        return showUniversity();
    }

    private Page sortByFullName() {
        universities = universities.stream().sorted(Comparator.comparing(University::getFullName)).toList();
        return showUniversity();
    }

    private Page showUniversity() {
        for (University university : universities)
            inputReader.println("id - " + university.getId() + ", " + university);
        return this;
    }
}
