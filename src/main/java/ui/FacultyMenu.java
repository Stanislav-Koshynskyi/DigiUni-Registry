package ui;

import entity.Faculty;
import entity.Contact;
import service.ServiceFacultyInterface;
import java.io.Console;

public class FacultyMenu {

    private final ServiceFacultyInterface serviceFaculty;

    public FacultyMenu(ServiceFacultyInterface serviceFaculty) {
        this.serviceFaculty = serviceFaculty;
    }

    public void main(Console console) {
        while (true) {
            System.out.println(
                    "Faculties \n 1 - Create Faculty\n 2 - Edit Faculty\n 3 - Delete Faculty\n 4 - Show All Faculties\n 0 - Back"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createFaculty(console);
                    break;
                case 2:
                    editFaculty(console);
                    break;
                case 3:
                    deleteFaculty(console);
                    break;
                case 4:
                    showAllFaculties();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input");
            }
        }
    }
    private int readInt(Console console) {
        try {
            return Integer.parseInt(console.readLine("Your choice: "));
        } catch (NumberFormatException e){
            return -1;
        }
    }

    private void createFaculty(Console console) {
        String name = console.readLine("Enter faculty name: ");
        String shortName = console.readLine("Enter short name: ");
        String phone = console.readLine("Enter phone: ");
        String email = console.readLine("Enter email: ");

        Contact contact = new Contact(phone, email);
        Faculty faculty = new Faculty(null, name, shortName, null, contact, null);


        serviceFaculty.create(faculty);
    }

    private void editFaculty(Console console) {
        Long id = Long.parseLong(console.readLine("Enter faculty id to edit: "));
        var idFaculty = serviceFaculty.findById(id);
        Faculty faculty = idFaculty.get();

        String name = console.readLine("Enter new name: ");
        String shortName = console.readLine("Enter new short name: ");
        String phone = console.readLine("Enter new phone: ");
        String email = console.readLine("Enter new email: ");

        if (!name.isBlank())
            faculty.setName(name);
        if (!shortName.isBlank())
            faculty.setShortName(shortName);

        serviceFaculty.update(faculty);
    }

    private void deleteFaculty(Console console) {
        Long id = Long.parseLong(console.readLine("Enter faculty id to edit: "));
        serviceFaculty.delete(id);
    }

    private void showAllFaculties() {
        for (Faculty faculty : serviceFaculty.findAll())
            System.out.println(faculty);
    }
}
