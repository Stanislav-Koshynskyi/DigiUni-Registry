package ui.pages;

import entity.*;
import service.ServiceStudentGroupInterface;
import service.ServiceStudentInterface;
import ui.*;
import ui.finders.StudentFinderInterface;
import ui.finders.StudentGroupFinderInterface;
import util.PagerBuilder;

import java.io.Console;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentPage extends BasePage {
    private final StudentGroupFinderInterface studentGroupFinder;
    private final ServiceStudentInterface serviceStudent;
    private final PagerBuilder pagerBuilder;
    private final StudentFinderInterface studentFinder;

    public StudentPage(StudentGroupFinderInterface studentGroupFinder,
                       ServiceStudentInterface serviceStudent, InputReader inputReader,StudentFinderInterface studentFinder, PagerBuilder pagerBuilder) {
        super(inputReader);
        this.studentGroupFinder = studentGroupFinder;
        this.serviceStudent = serviceStudent;
        this.studentFinder = studentFinder;
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "Student";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Add student", Right.ADD, this::createStudent),
                new MenuItem("Edit student", Right.EDIT, this::editStudent),
                new MenuItem("Delete student", Right.DELETE, this::deleteStudent),
                new MenuItem("Show all student", Right.FIND, this::showAllStudents),
                new MenuItem("Find student", Right.FIND, pagerBuilder::getStudentFindPage)
        );
    }


    private Page createStudent() {
        Optional<StudentGroup> optionalStudentGroup = studentGroupFinder.findAndSelect();
        if (optionalStudentGroup.isEmpty()){
            System.out.println("No student group selected");
            System.out.println("Student not created");
            return this;
        }
        StudentGroup studentGroup = optionalStudentGroup.get();
        University university = studentGroup.getDepartment().getFaculty().getUniversity();

        FullName fullName = inputReader.readfullName();
        FormOfEducation form = inputReader.readFormOfEducation();
        LocalDate birthDate = inputReader.readBirthDate();
        Contact contact;
        while (true) {
            try {
                String email = uniqueEmail();
                String phone = uniquePhone();
                contact = new Contact(phone, email);
                break;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        String uniqueCode = uniqueCode(university);
        String recordBook = uniqueRecordBookNumber(university);
        int course = inputReader.readIntInRange("Enter course(1-6)", 1, 6);
        Year yearOfAdmission = inputReader.readYearOfAdmission();
        StudentStatus status = StudentStatus.STUDIES;


        try {
            Student student = new Student(uniqueCode, recordBook, fullName, birthDate, contact, form, status, yearOfAdmission, course, studentGroup);
            serviceStudent.create(student);
        }catch (IllegalArgumentException e){
            System.out.println("Illegal Argument");
            System.out.println(e.getMessage());
            System.out.println("Student not created");
        }
        return this;
    }

    private String uniqueCode(University university) {
        while (true) {
            String uniqueCode = inputReader.readString("Enter unique code: ");
            if (!serviceStudent.existsByUniqueCode(uniqueCode, university))
                return uniqueCode;
            System.out.println("Student " + uniqueCode + " already exists");
        }
    }

    private String uniqueRecordBookNumber(University university) {
        while (true) {
            String recordBookNumber = inputReader.readString("Enter record book number: ");
            if (!serviceStudent.existsByRecordBookNumber(recordBookNumber, university))
                return recordBookNumber;
            System.out.println("Student with record book number " + recordBookNumber + " already exists");
        }
    }

    private String uniquePhone() {
        while (true) {
            String phone = inputReader.readString("Enter phone: ");
            if (!serviceStudent.existsByPhone(phone))
                return phone;
            System.out.println("Phone " + phone + " already exists");
        }
    }

    private String uniqueEmail() {
        while (true) {
            String email = inputReader.readString("Enter email: ");
            if (!serviceStudent.existsByEmail(email))
                return email;
            System.out.println("Email " + email + " already exists");
        }
    }

    private Page editStudent() {
        Optional<Student> optionalStudent = studentFinder.findAndSelect();
        if (optionalStudent.isEmpty()) {
            System.out.println("Student not found!!!");
            return this;
        }
        Student student = optionalStudent.get();
        String recordBook = inputReader.readProbablyBlank("Enter record book number: ");
        Integer course = inputReader.readIntInRangeProbablyNull( "Enter course number (1-6)", 1, 6);
        if (!recordBook.isBlank())
            student.setRecordBookNumber(recordBook);
        if (course != null)
            student.setCourse(course);

        StudentStatus status = inputReader.readChooseProbablyNull(List.of(StudentStatus.values()),
                "Choose new student status", "not change");
        if (status != null) {
            student.setStudentStatus(status);
        }
        serviceStudent.update(student);
        return this;
    }

    private Page deleteStudent() {
        Optional<Student> studentOptional = studentFinder.findAndSelect();
        if(studentOptional.isPresent()){
            try{
                Student student = studentOptional.get();
                serviceStudent.delete(student.getId());
            }catch (IllegalArgumentException e){
                System.out.println("Deleting error");
            }
        }
        return this;
    }

    private Page showAllStudents() {
        List<Student> student = serviceStudent.findAll();
        return new SortStudentPage(inputReader, student);
    }
}
