package ui.pages;

import entity.*;
import exception.EntityInUseException;
import service.ServiceUniversityInterface;
import ui.*;
import ui.finders.UniversityFinder;
import ui.finders.UniversityFinderInterface;
import util.Annotations;
import util.PagerBuilder;

import java.io.Console;
import java.util.List;
import java.util.Optional;

public class UniversityPage extends BasePage {
    private final ServiceUniversityInterface serviceUniversity;
    private final PagerBuilder pagerBuilder;
    private final UniversityFinderInterface universityFinder;
    public UniversityPage(ServiceUniversityInterface serviceUniversity,
                          InputReader inputReader,UniversityFinderInterface universityFinder, PagerBuilder pagerBuilder) {
        super(inputReader);
        this.serviceUniversity = serviceUniversity;
        this.universityFinder = universityFinder;
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "University";
    }

//    @Override
//    public List<MenuItem> getMenuItems() {
//        return List.of(
//            new MenuItem("Create university", Right.ADD, this::createUniversity),
//            new MenuItem("Find university", Right.FIND, pagerBuilder::getFindUniversityPage),
//            new MenuItem("Show all university", Right.FIND,this::showAllUniversity),
//            new MenuItem("Delete university", Right.DELETE, this::delete)
//        );
//    }
    @Annotations(name = "Find university", right = Right.FIND, order = 2)
    private Page findUniversity() {
        return pagerBuilder.getFindUniversityPage(inputReader);
    }
    @Annotations(name = "Delete university", right = Right.ADD, order = 1)
    private Page delete() {
    Optional <University> universityOptional= universityFinder.findAndSelect();
    if (universityOptional.isPresent()) {
        try{
            University university = universityOptional.get();
            serviceUniversity.delete(university.getId());
            inputReader.println("Deleted university with id: " + university.getId());
        }catch (EntityInUseException e){
            inputReader.println(e.getMessage());
        }
        catch (IllegalArgumentException e){
            inputReader.println("Error deleting university");
        }
    }
    return this;
    }
    @Annotations(name = "Create university", right = Right.ADD, order = 1)
    private Page createUniversity() {
        String fullName = inputReader.readString("Enter full name");
        String shortName = inputReader.readStringWithMaxLengthProbablyBlank("Enter short name",
                University.MAX_SHORT_NAME_LENGTH);
        String city = inputReader.readString("Enter city: ");
        String street = inputReader.readString("Enter street: ");
        String buildNumber = inputReader.readString("Enter building number: ");

        Address address = new Address(city, street, buildNumber);
        try {
            University university = new University(fullName, shortName, address);
            serviceUniversity.create(university);
        }catch (IllegalArgumentException e){
            inputReader.println(e.getMessage());
            inputReader.println("University not created");
        }
        return this;
    }
    @Annotations(name = "Show all university", right = Right.FIND, order = 3)
    private Page showAllUniversity(){
        List<University> universities = serviceUniversity.findAll();
        return new SortUniversityPage(inputReader, universities);
    }
}