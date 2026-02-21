package ui.probably_to_delete;

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
        String uniqueCode = ConsoleMenu.readRequiredString(console, "Enter unique code: ");
        String name = ConsoleMenu.readRequiredString(console,"Enter faculty name: ");
        String shortName;
        while (true) {
            shortName = ConsoleMenu.readRequiredString(console,"Enter short name: ");
            if (shortName.length() <= Faculty.MAX_SHORT_NAME_LENGTH) break;
            else {
                System.out.println("Short name must be shorter than "+ Faculty.MAX_SHORT_NAME_LENGTH);
            }
        }
        Contact contact;
        while (true) {
            try {
                String phone = console.readLine("Enter phone(10-13 numbers with optional +): ");
                String email = console.readLine("Enter email(in format text@text.text): ");
                contact = new Contact(phone, email);
                break;
            }catch ( IllegalArgumentException e) {
                System.out.println("Invalid email or phone format, use text.text@text for email and 10-13 numbers for phone");
            }
        }
        Teacher dean = null;
        Long teacherId;
        while (true) {
            teacherId = ConsoleMenu.readRequiredLong(console, "Enter teacher id(if vacant write -1): ");
            if (teacherId.equals(Long.valueOf(-1))) break;
            Optional<Teacher> optionalTeacher = serviceTeacher.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                dean = optionalTeacher.get();
                break;
            } else {
                System.out.println("Teacher not found!!!");
            }

        }
        University university;
        while (true) {
            Long universityId = ConsoleMenu.readRequiredLong(console, "Enter university id(-1 to exit): ");
            if (universityId.equals(Long.valueOf(-1))) return;
            Optional<University> optionalUniversity = serviceUniversity.findById(universityId);
            if  (optionalUniversity.isPresent()) {
                university = optionalUniversity.get();
                break;
            }
            else {
                System.out.println("University not found");
            }
        }
        Faculty faculty = new Faculty(uniqueCode, name, shortName, dean, contact, university);

        serviceFaculty.create(faculty);
    }

    private void editFaculty(Console console) {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter faculty id to edit: ");
        Optional<Faculty> optionalFaculty = serviceFaculty.findById(id);
        if (optionalFaculty.isEmpty()) {
            System.out.println("Faculty not found");
            return;
        }
        Faculty faculty = optionalFaculty.get();

        String name = console.readLine("Enter new name: ");
        String shortName;
        while (true) {
            shortName = console.readLine("Enter new short name: ");
            if (shortName.length() <= Faculty.MAX_SHORT_NAME_LENGTH) break;
            else {
                System.out.println("Short name must  be shorter than "+ Faculty.MAX_SHORT_NAME_LENGTH);
            }
        }
        if (!name.isBlank())
            faculty.setName(name);
        if (!shortName.isBlank())
            faculty.setShortName(shortName);
        while (true) {
            try {
                String phone = console.readLine("Enter new phone(10-13 numbers with optional +): ");
                String email = console.readLine("Enter new email(in format text@text.text): ");
                Contact old = faculty.getContact();
                String newPhone = old.phone();
                String newEmail = old.email();
                if (!phone.isBlank())
                    newPhone = phone;
                if (!email.isBlank())
                    newEmail = email;
                faculty.setContact(new Contact(newPhone, newEmail));
                break;
            }catch (IllegalArgumentException e) {
                System.out.println("Invalid phone or email");
            }
        }

        serviceFaculty.update(faculty);
    }

    private void deleteFaculty(Console console) {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter faculty id to delete ");
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
