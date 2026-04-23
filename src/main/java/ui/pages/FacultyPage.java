package ui.pages;

import entity.*;
import exception.EntityInUseException;
import service.ServiceFacultyInterface;
import service.ServiceTeacherInterface;
import service.ServiceUniversityInterface;
import ui.*;
import ui.finders.FacultyFinderInterface;
import ui.finders.TeacherFinderInterface;
import ui.finders.UniversityFinderInterface;
import util.Annotations;
import util.PagerBuilder;

import java.io.Console;
import java.util.List;
import java.util.Optional;

public class FacultyPage extends BasePage {
    private final ServiceFacultyInterface serviceFaculty;
    private final TeacherFinderInterface teacherFinder;
    private final UniversityFinderInterface universityFinder;
    private final FacultyFinderInterface facultyFinder;
    private final PagerBuilder pagerBuilder;

    public FacultyPage(ServiceFacultyInterface serviceFaculty, TeacherFinderInterface teacherFinder,
                       InputReader inputReader, UniversityFinderInterface universityFinder, FacultyFinderInterface facultyFinder,
                       PagerBuilder pagerBuilder) {
        super(inputReader);
        this.serviceFaculty = serviceFaculty;
        this.teacherFinder = teacherFinder;
        this.universityFinder = universityFinder;
        this.facultyFinder = facultyFinder;
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "Faculty";
    }

//    @Override
//    public List<MenuItem> getMenuItems() {
//        return List.of(
//                new MenuItem("Create faculty", Right.ADD, this::createFaculty),
//                new MenuItem("Edit faculty", Right.EDIT, this::editFaculty),
//                new MenuItem("Delete faculty", Right.DELETE, this::deleteFaculty),
//                new MenuItem("Find faculty", Right.FIND, pagerBuilder::getFindFacultyPage),
//                new MenuItem("Show all faculties", Right.FIND, this::showAllFaculties)
//        );
//    }
    @Annotations(name = "Find faculty", right = Right.FIND, order = 4)
    private Page findFaculty() {
        return pagerBuilder.getFindFacultyPage(inputReader);
    }
    @Annotations(name = "Create faculty", right = Right.ADD, order = 1)
    private Page createFaculty() {
        String name = inputReader.readString("Enter faculty name: ");
        String shortName = inputReader.readStringWithMaxLengthProbablyBlank("Enter short name", Faculty.MAX_SHORT_NAME_LENGTH);
        Contact contact;
        while(true) {
            try {
                String email = uniqueEmail();
                String phone = uniquePhone();
                contact = new Contact(phone, email);
                break;
            }catch (IllegalArgumentException e){
                inputReader.println(e.getMessage());
            }
        }
        inputReader.println("Select dean (cansel if vacant)");
        Optional<Teacher> optionalTeacher = teacherFinder.findAndSelect();
        Teacher dean;
        dean = optionalTeacher.orElse(null);

        Optional<University> optionalUniversity = universityFinder.findAndSelect();
        if (optionalUniversity.isEmpty()){
            inputReader.println("Faculty not created!");
            return this;
        }
        University university = optionalUniversity.get();
        String uniqueCode = uniqueCode(university);
        try {
            Faculty faculty = new Faculty(uniqueCode, name, shortName, dean, contact, university);
            serviceFaculty.create(faculty);
        }catch (IllegalArgumentException e){
            inputReader.println(e.getMessage());
            inputReader.println("Faculty not created");
        }
        return this;
    }
    @Annotations(name = "Edit faculty", right = Right.EDIT, order = 2)
    private Page editFaculty() {
        Optional<Faculty> optionalFaculty = facultyFinder.findAndSelect();
        if (optionalFaculty.isEmpty()) {
            inputReader.println("Faculty not found");
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
    @Annotations(name = "Delete faculty", right = Right.DELETE, order = 3)
    private Page deleteFaculty() {
        Optional<Faculty> faculty = facultyFinder.findAndSelect();
        if (faculty.isPresent()) {
            try {
                Faculty facultyToDelete = faculty.get();
                serviceFaculty.delete(facultyToDelete.getId());
            }catch (IllegalArgumentException e){
                inputReader.println("Deleting error");
            }catch (EntityInUseException e) {
                inputReader.println(e.getMessage());
            }
        }
        return this;
    }
    @Annotations(name = "Show all faculties", right = Right.FIND, order = 5)
    private Page showAllFaculties() {
        List<Faculty> faculties = serviceFaculty.findAll();
        return new SortFacultyPage(inputReader, faculties);
    }

    private String uniqueCode(University university) {
        while (true) {
            String uniqueCode = inputReader.readString("Enter unique code: ");
            if (!serviceFaculty.existsByUniqueCode(uniqueCode, university))
                return uniqueCode;
            inputReader.println("Faculty with " + uniqueCode + " already exists");
        }
    }

    private String uniqueEmail() {
        while (true) {
            String email = inputReader.readString("Enter email: ");
            if (!serviceFaculty.existsByEmail(email))
                return email;
            inputReader.println("Email " + email + " already exists");
        }
    }

    private String uniquePhone() {
        while (true) {
            String phone = inputReader.readString("Enter phone: ");
            if (!serviceFaculty.existsByPhone(phone))
                return phone;
            inputReader.println("Phone " + phone + " already exists");
        }
    }
}
