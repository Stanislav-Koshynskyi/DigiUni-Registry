package ui;

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
        System.out.println("Universities \n 1 - Create University \n 0 - Back");

        int userSelect = readInt(console);
        switch (userSelect) {
            case 1:
                createUniversity(console);
                break;
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
        String fullName = console.readLine("Enter full name: ");
        String shortName = console.readLine("Enter short name: ");
        String city = console.readLine("Enter city: ");
        String street = console.readLine("Enter street: ");
        String buildNumber = console.readLine("Enter building number: ");

        Address address = new Address(city, street, buildNumber);
        University university = new University(fullName, shortName, address);

        serviceUniversity.create(university);
    }
}
