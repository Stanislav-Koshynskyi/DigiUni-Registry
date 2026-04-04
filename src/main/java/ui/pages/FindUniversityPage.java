package ui.pages;

import entity.Right;
import entity.University;
import service.ServiceUniversityInterface;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;
import ui.finders.UniversityFinderInterface;

import java.util.List;
import java.util.Optional;

public class FindUniversityPage extends BasePage {
    private final UniversityFinderInterface universityFinder;
    public FindUniversityPage(InputReader inputReader,
                              UniversityFinderInterface universityFinder) {
        super(inputReader);
        this.universityFinder = universityFinder;
    }

    @Override
    public String getTitle() {
        return "Find University";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("By name", Right.FIND,this::findUniversityByName),
                new MenuItem("By short name", Right.FIND,this::findUniversityByShortName ),
                new MenuItem("By city", Right.FIND,this::findUniversityByCity)
        );
    }
    public Page findUniversityByName() {
       Optional<University> university = universityFinder.findByFullName();
        if (university.isEmpty()){
            System.out.println("There is no university with that name");
        }
        else{
            System.out.println(university.get());
        }
        return this;
    }
    public Page findUniversityByShortName() {
        Optional<University> university = universityFinder.findByShortName();
        if (university.isEmpty()){
            System.out.println("There is no university with that name");
        }
        else{
            System.out.println(university.get());
        }
        return this;
    }
    public Page findUniversityByCity() {
        List<University> universityList = universityFinder.findByCity();
        if (universityList.isEmpty()){
            System.out.println("There is no university with that name");
        }
        else{
            //тут можна зробити перехід на сторінку сортування
            universityList.forEach(System.out::println);
        }
        return this;
    }
}
