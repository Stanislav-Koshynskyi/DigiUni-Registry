package ui.pages;

import entity.*;
import service.ServiceUniversityInterface;
import ui.*;
import util.PagerBuilder;

import java.io.Console;
import java.util.List;

public class UniversityPage extends BasePage {
    private final ServiceUniversityInterface serviceUniversity;
    private final PagerBuilder pagerBuilder;
    public UniversityPage(ServiceUniversityInterface serviceUniversity,
                          InputReader inputReader, PagerBuilder pagerBuilder) {
        super(inputReader);
        this.serviceUniversity = serviceUniversity;
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "University";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
            new MenuItem("Create university", Right.ADD, this::createUniversity),
            new MenuItem("Find university", Right.FIND, pagerBuilder::getFindUniversityPage),
            new MenuItem("Show all university", Right.FIND,this::showAllUniversity)
        );
    }

    private Page createUniversity() {
        String fullName = inputReader.readString("Enter full name");
        String shortName = inputReader.readStringWithMaxLengthProbablyBlank("Enter short name",
                University.MAX_SHORT_NAME_LENGTH);
        String city = inputReader.readString("Enter city: ");
        String street = inputReader.readString("Enter street: ");
        String buildNumber = inputReader.readString("Enter building number: ");

        Address address = new Address(city, street, buildNumber);
        University university = new University(fullName, shortName, address);

        serviceUniversity.create(university);
        return this;
    }
    private Page showAllUniversity(){
        for (University university : serviceUniversity.findAll()) {
            System.out.println("id - "+ university.getId() + ", " + university);
        }
        return this;
    }
}