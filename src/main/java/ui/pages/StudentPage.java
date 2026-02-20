package ui.pages;

import entity.*;
import service.ServiceStudentGroupInterface;
import service.ServiceStudentInterface;
import ui.*;

import java.io.Console;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public class StudentPage extends BasePage {
    private final ServiceStudentGroupInterface serviceStudentGroup;
    private final ServiceStudentInterface serviceStudent;
    public StudentPage(Console console,  ServiceStudentInterface serviceStudent, ServiceStudentGroupInterface serviceStudentGroup) {
        super(console);
        this.serviceStudent = serviceStudent;
        this.serviceStudentGroup = serviceStudentGroup;
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
                new MenuItem("Show all student", Right.FIND, this::showAllStudents)
        );
    }

    private int readInt() {
        try {
            return Integer.parseInt(console.readLine("Your choice: "));
        } catch (NumberFormatException e){
            return -1;
        }
    }

    private Page createStudent() {
        StudentGroup group;
        while (true) {
            Long groupId = ConsoleMenu.readRequiredLong(console, "Enter group id(-1 to exit): ");
            if (groupId.equals(Long.valueOf(-1))) return this;
            Optional<StudentGroup> optionalGroup = serviceStudentGroup.findById(groupId);
            if  (optionalGroup.isPresent()) {
                group = optionalGroup.get();
                break;
            }
            else {
                System.out.println("Group not found!!!");

            }
        }


        FullName fullName = PersonMenu.fullName(console);
        LocalDate birthDate = PersonMenu.birthDate(console);
        Contact contact = PersonMenu.contact(console);

        String uniqueCode = ConsoleMenu.readRequiredString(console, "Enter unique code: ");
        String recordBook = ConsoleMenu.readRequiredString(console, "Enter record book number: ");
        int course;
        while (true) {
            try {
                course = Math.toIntExact(ConsoleMenu.readRequiredLong(console, "Enter course (1-6): "));
                if (course >=1  && course <= 6) break;
                else System.out.println("course must be between 1 and 6");

            }catch (NumberFormatException e){
                System.out.println("Invalid input, use numbers only!");
            }
        }
        Year yearOfAdmission;
        while (true) {
            try {
                int year = Math.toIntExact(ConsoleMenu.readRequiredLong(console, "Enter year of admission: "));
                yearOfAdmission = Year.of(year);
                if (!yearOfAdmission.isAfter(Year.now()) && !yearOfAdmission.isBefore(Year.of(Student.MINIUM_YEAR_OF_ADMISSION))){
                    break;
                }
                else{
                    System.out.println("Invalid year of admission, cannot be in future and cannot be too past");
                }
            }catch (NumberFormatException e){
                System.out.println("Use only numbers only!");
            }
        }
        FormOfEducation form = null;
        while (form == null) {
            System.out.println("Form of education: 1 - Budget, 2 - Contract");
            int userChoiceFormOfEducation = readInt();

            switch (userChoiceFormOfEducation) {
                case 1:
                    form = FormOfEducation.BUDGET;
                    break;
                case 2:
                    form = FormOfEducation.CONTRACT;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }

        StudentStatus status = StudentStatus.STUDIES;



        Student student = new Student(uniqueCode, recordBook, fullName, birthDate, contact, form, status, yearOfAdmission, course, group);
        group.addStudent(student);
        serviceStudent.create(student);
        return this;
    }

    private Page editStudent() {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter student id to edit: ");
        Optional<Student> optionalStudent = serviceStudent.findById(id);
        if (optionalStudent.isEmpty()) {
            System.out.println("Student not found!!!");
            return this;
        }
        Student student = optionalStudent.get();
        String recordBook = console.readLine("Enter record book number: ");
        Integer course = null;
        while (true) {
            String stringCourse = console.readLine("Enter course (1-6): ");
            if (stringCourse.isBlank()) break;
            else{
                try {
                    course = Integer.valueOf(stringCourse);
                    if (course >=1  && course <= 6) break;
                    else{
                        System.out.println("Invalid course must be between 1 and 6");
                    }
                }catch (NumberFormatException e){
                    System.out.println("Invalid input, use numbers only!");
                }
            }
        }
        if (!recordBook.isBlank())
            student.setRecordBookNumber(recordBook);
        if (course != null)
            student.setCourse(course);

        System.out.println("Student status: 1 - Studies, 2 - Academic leave, 3 - Expelled");
        int statusChoice = readInt();

        switch (statusChoice) {
            case 1:
                student.setStudentStatus(StudentStatus.STUDIES);
                break;
            case 2:
                student.setStudentStatus(StudentStatus.ACADEMIC_LEAVE);
                break;
            case 3:
                student.setStudentStatus(StudentStatus.EXPELLED);
                break;
            default:
                System.out.println("Status not changed!");
        }

        serviceStudent.update(student);
        return this;
    }

    private Page deleteStudent() {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter student id to delete: ");
        Optional<Student> optionalStudent = serviceStudent.findById(id);
        if (optionalStudent.isEmpty()) {
            System.out.println("Student not found!!!");
            return this;
        }

        Student student = optionalStudent.get();
        student.getGroup().removeStudent(student);
        serviceStudent.delete(id);
        return this;
    }

    private Page showAllStudents() {
        for (Student student : serviceStudent.findAll())
            System.out.println("id - "+ student.getId() + ", " + student);
        return this;
    }
}
