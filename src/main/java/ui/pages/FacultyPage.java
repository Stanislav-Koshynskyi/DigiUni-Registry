package ui.pages;

import entity.*;
import service.ServiceFacultyInterface;
import service.ServiceTeacherInterface;
import service.ServiceUniversityInterface;
import ui.*;

import java.io.Console;
import java.util.List;
import java.util.Optional;

public class FacultyPage extends BasePage {
    private final ServiceFacultyInterface serviceFaculty;
    private final ServiceUniversityInterface serviceUniversity;
    private final ServiceTeacherInterface serviceTeacher;

    public FacultyPage(ServiceFacultyInterface serviceFaculty,
                       ServiceUniversityInterface serviceUniversity, ServiceTeacherInterface serviceTeacher, InputReader inputReader) {
        super(inputReader);
        this.serviceFaculty = serviceFaculty;
        this.serviceUniversity = serviceUniversity;
        this.serviceTeacher = serviceTeacher;
    }

    @Override
    public String getTitle() {
        return "Faculty";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Create faculty", Right.ADD, this::createFaculty),
                new MenuItem("Edit faculty", Right.EDIT, this::editFaculty),
                new MenuItem("Delete faculty", Right.DELETE, this::deleteFaculty),
                new MenuItem("Show all faculties", Right.FIND, this::showAllFaculties)
        );
    }

    private Page createFaculty() {
        String uniqueCode = inputReader.readString( "Enter unique code: ");
        String name = inputReader.readString("Enter faculty name: ");
        String shortName = inputReader.readStringWithMaxLengthProbablyBlank("Enter short name", Faculty.MAX_SHORT_NAME_LENGTH);
        Contact contact = inputReader.readContact();
        Teacher dean = null;
        Long teacherId;
        while (true) {
            teacherId = inputReader.readLong("Enter teacher id(if vacant write -1): ");
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
            Long universityId = inputReader.readLong("Enter university id(-1 to exit): ");
            if (universityId.equals(Long.valueOf(-1))) return this;
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
        return this;
    }

    private Page editFaculty() {
        Long id = inputReader.readLong("Enter faculty id to edit: ");
        Optional<Faculty> optionalFaculty = serviceFaculty.findById(id);
        if (optionalFaculty.isEmpty()) {
            System.out.println("Faculty not found");
            return this;
        }
        Faculty faculty = optionalFaculty.get();

        String name = inputReader.readProbablyBlank("Enter new name: ");
        String shortName = inputReader.readStringWithMaxLengthProbablyBlank(
                "Enter new short name", Faculty.MAX_SHORT_NAME_LENGTH);
        if (!name.isBlank())
            faculty.setName(name);
        if (!shortName.isBlank())
            faculty.setShortName(shortName);
        Contact newContact = inputReader.readContactProbablyNull();
        if (newContact != null) faculty.setContact(newContact);

        serviceFaculty.update(faculty);
        return this;
    }

    private Page deleteFaculty() {
        Long id = inputReader.readLong("Enter faculty id to delete ");
        Optional<Faculty> optionalFaculty = serviceFaculty.findById(id);
        if (optionalFaculty.isEmpty()) {
            System.out.println("Faculty not found!!!");
            return this;
        }

        serviceFaculty.delete(id);
        return this;
    }

    private Page showAllFaculties() {
        for (Faculty faculty : serviceFaculty.findAll())
            System.out.println("id -" + faculty.getId() + ", " +faculty);
        return this;
    }

}
