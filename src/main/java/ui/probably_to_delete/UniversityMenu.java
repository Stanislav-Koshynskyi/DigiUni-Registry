package ui.probably_to_delete;

import entity.Address;
import entity.University;
import service.ServiceUniversityInterface;
import java.io.Console;

public class UniversityMenu {
    private final ServiceUniversityInterface serviceUniversity;

    public UniversityMenu(ServiceUniversityInterface serviceUniversity) {
        this.serviceUniversity = serviceUniversity;
    }

    public void main(Console console){
        System.out.println("Universities \n 1 - Create University \n 2 - Show all university \n 0 - Back");

        int userSelect = readInt(console);
        switch (userSelect) {
            case 1:
                createUniversity(console);
                break;
            case 2:
                showAllUniversity();
            case 0:
                return;
            default:
                System.out.println("Invalid input");
        }
    }

    private int readInt(Console console) {
        try {
            return Integer.parseInt(console.readLine("Your choice: "));
        } catch (NumberFormatException e){
            return -1;
        }
    }

    private void createUniversity(Console console) {
        String fullName = ConsoleMenu.readRequiredString(console, "Enter full name: ");
        String shortName;
        while (true) {
            shortName = ConsoleMenu.readRequiredString(console, "Enter short name: ");
            if (shortName.length() <= University.MAX_SHORT_NAME_LENGTH) break;
            else{
                System.out.println("Short name must be shorter than "+ University.MAX_SHORT_NAME_LENGTH);
            }
        }
        String city = ConsoleMenu.readRequiredString(console,"Enter city: ");
        String street = ConsoleMenu.readRequiredString(console,"Enter street: ");
        String buildNumber = ConsoleMenu.readRequiredString(console,"Enter building number: ");

        Address address = new Address(city, street, buildNumber);
        University university = new University(fullName, shortName, address);

        serviceUniversity.create(university);
    }
    private void showAllUniversity(){
        for (University university : serviceUniversity.findAll()) {
            System.out.println("id - "+ university.getId() + ", " + university);
        }
    }
}
