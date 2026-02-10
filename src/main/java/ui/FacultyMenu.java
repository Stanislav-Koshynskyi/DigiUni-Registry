package ui;

import entity.Faculty;
import entity.Contact;
import entity.University;
import entity.Teacher;
import service.ServiceFacultyInterface;
import service.ServiceUniversityInterface;
import service.ServiceTeacherInterface;
import java.io.Console;
import java.util.Optional;

public class FacultyMenu {

    private final ServiceFacultyInterface serviceFaculty;
    private final ServiceUniversityInterface serviceUniversity;
    private final ServiceTeacherInterface serviceTeacher;

    public FacultyMenu(ServiceFacultyInterface serviceFaculty, ServiceUniversityInterface serviceUniversity, ServiceTeacherInterface serviceTeacher) {
        this.serviceFaculty = serviceFaculty;
        this.serviceUniversity = serviceUniversity;
        this.serviceTeacher = serviceTeacher;
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
        String uniqueCode = console.readLine("Enter unique code: ");
        String name = console.readLine("Enter faculty name: ");
        String shortName = console.readLine("Enter short name: ");
        String phone = console.readLine("Enter phone: ");
        String email = console.readLine("Enter email: ");
        Teacher dean = null;
        String deanInput = console.readLine("Enter teacher id: ");
        if (!deanInput.isBlank()) {
            Long deanId = Long.parseLong(deanInput);
            Optional<Teacher> optionalDean = serviceTeacher.findById(deanId);
            if (optionalDean.isEmpty()) {
                System.out.println("Teacher not found!");
                return;
            }
            dean = optionalDean.get();
        }
        Long universityId = Long.parseLong(console.readLine("Enter university id: "));
        Optional<University> optionalUniversity = serviceUniversity.findById(universityId);
        if (optionalUniversity.isEmpty()) {
            System.out.println("University not found");
            return;
        }

        University university = optionalUniversity.get();
        Contact contact = new Contact(phone, email);
        Faculty faculty = new Faculty(uniqueCode, name, shortName, dean, contact, university);

        serviceFaculty.create(faculty);
    }

    private void editFaculty(Console console) {
        Long id = Long.parseLong(console.readLine("Enter faculty id to edit: "));
        Optional<Faculty> optionalFaculty = serviceFaculty.findById(id);
        if (optionalFaculty.isEmpty()) {
            System.out.println("Faculty not found");
            return;
        }
        Faculty faculty = optionalFaculty.get();

        String name = console.readLine("Enter new name: ");
        String shortName = console.readLine("Enter new short name: ");
        if (!name.isBlank())
            faculty.setName(name);
        if (!shortName.isBlank())
            faculty.setShortName(shortName);

        String phone = console.readLine("Enter new phone: ");
        String email = console.readLine("Enter new email: ");
        Contact old = faculty.getContact();
        String newPhone = old.phone();
        String newEmail = old.email();
        if (!phone.isBlank())
            newPhone = phone;
        if (!email.isBlank())
            newEmail = email;
        faculty.setContact(new Contact(newPhone, newEmail));

        serviceFaculty.update(faculty);
    }

    private void deleteFaculty(Console console) {
        Long id = Long.parseLong(console.readLine("Enter faculty id to edit: "));
        Optional<Faculty> optionalFaculty = serviceFaculty.findById(id);
        if (optionalFaculty.isEmpty()) {
            System.out.println("Faculty not found!!!");
            return;
        }

        serviceFaculty.delete(id);
    }

    private void showAllFaculties() {
        for (Faculty faculty : serviceFaculty.findAll())
            System.out.println("id -" + faculty.getId() + ", " +faculty);
    }
}
