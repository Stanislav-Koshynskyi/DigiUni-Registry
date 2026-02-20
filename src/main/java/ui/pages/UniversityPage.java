package ui.pages;

import entity.*;
import service.ServiceUniversityInterface;
import ui.BasePage;
import ui.ConsoleMenu;
import ui.MenuItem;
import ui.Page;
import util.Reader;

import java.io.Console;
import java.util.List;

public class UniversityPage extends BasePage {
    private final ServiceUniversityInterface serviceUniversity;
    public UniversityPage(Console console, ServiceUniversityInterface serviceUniversity) {
        super(console);
        this.serviceUniversity = serviceUniversity;
    }

    @Override
    public String getTitle() {
        return "University";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
            new MenuItem("Create university", Right.ADD, this::createUniversity),
            new MenuItem("Show all university", Right.FIND,this::showAllUniversity)
        );
    }

    private Page createUniversity() {
        String fullName = Reader.readString(console, "Enter full name");
        String shortName = Reader.readStringWithMaxLength(console, "Enter short name",
                University.MAX_SHORT_NAME_LENGTH);
        String city = Reader.readString(console,"Enter city: ");
        String street = Reader.readString(console,"Enter street: ");
        String buildNumber = Reader.readString(console,"Enter building number: ");

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



