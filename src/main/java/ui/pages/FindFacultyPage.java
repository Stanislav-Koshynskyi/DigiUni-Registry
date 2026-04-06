package ui.pages;

import entity.Faculty;
import entity.Right;
import ui.*;
import ui.finders.FacultyFinder;
import ui.finders.FacultyFinderInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindFacultyPage extends BasePage {
    private final FacultyFinderInterface facultyFinder;

    public FindFacultyPage(InputReader inputReader, FacultyFinderInterface facultyFinder) {
        super(inputReader);
        this.facultyFinder = facultyFinder;
    }

    @Override
    public String getTitle() {
        return "Faculty Finder";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Find by unique code", Right.FIND, this::findByUniqueCode ),
                new MenuItem("Find by Name", Right.FIND, this::findByName ),
                new MenuItem("Find by short name", Right.FIND, this::findByShortName ),
                new MenuItem("Find by email", Right.FIND,this::findByEmail),
                new MenuItem("Find by number", Right.FIND, this::findByNumber),
                new MenuItem("Find by university", Right.FIND, this::findByUniversity),
                new MenuItem("Advanced search", Right.FIND, this::advancedSearch)

        );
    }
    private Page findByUniqueCode() {
        List<Faculty> faculties = facultyFinder.findByUniqueCode();
        if  (faculties.isEmpty()) {
            System.out.println("No faculties found");
            return this;
        }
        return new SortFacultyPage(inputReader, faculties);
    }
    private Page findByName(){
        List<Faculty> faculties = facultyFinder.findByName();
        if(faculties.isEmpty()) {
            System.out.println("No faculties found");
        }
        else{
            faculties.forEach(System.out::println);
        }
        return this;
    }
    private Page findByShortName(){
        List<Faculty> faculties = facultyFinder.findByShortName();
        if(faculties.isEmpty()) {
            System.out.println("No faculties found");
        }
        else{
            faculties.forEach(System.out::println);
        }
        return this;
    }
    private Page findByEmail(){
        Optional<Faculty> faculty = facultyFinder.findByEmail();
        if(faculty.isEmpty()) {
            System.out.println("No faculties found");
        }
        else{
            System.out.println(faculty);
        }
        return this;
    }
    private Page findByNumber(){
        Optional<Faculty> faculty = facultyFinder.findByPhoneNumber();
        if(faculty.isEmpty()) {
            System.out.println("No faculties found");
        }
        else{
            System.out.println(faculty);
        }
        return this;
    }
    private Page findByUniversity(){
        List<Faculty> faculties = facultyFinder.findByUniversity();
        if(faculties.isEmpty()) {
            System.out.println("No faculties found");
        }
        else{
            faculties.forEach(System.out::println);
        }
        return this;
    }
    private Page advancedSearch(){
        List<Faculty> faculties = new ArrayList<>();
        faculties = facultyFinder.advancedSearch();
        if(faculties.isEmpty()) {
            System.out.println("No faculties found");
        }
        else{
            faculties.forEach(System.out::println);
        }
        return this;
    }
}
