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

    public StudentPage(StudentGroupFinderInterface studentGroupFinder,
                       ServiceStudentInterface serviceStudent, InputReader inputReader, PagerBuilder pagerBuilder) {
        super(inputReader);
        this.studentGroupFinder = studentGroupFinder;
        this.serviceStudent = serviceStudent;
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
                new MenuItem("Find student", Right.FIND, pagerBuilder::getStudentFindPage),

                new MenuItem("Students sorted by course", Right.FIND, this::studentsSortedByCourse),
                new MenuItem("Students of faculty sorted by surname", Right.FIND, this::studentsFacultySortedSurname),
                new MenuItem("Students of department sorted by course", Right.FIND, this::studentsDepartmentSortedCourse),
                new MenuItem("Students of department sorted by surname", Right.FIND, this::studentsDepartmentSortedSurname)
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


        FullName fullName = inputReader.readfullName();
        FormOfEducation form = inputReader.readFormOfEducation();
        LocalDate birthDate = inputReader.readBirthDate();
        Contact contact = inputReader.readContact();

        String uniqueCode, recordBook;
        while (true) {
            uniqueCode = inputReader.readString("Enter unique code: ");
            if (!serviceStudent.existsByUniqueCode(uniqueCode))
                break;
            System.out.println("Student with this unique code already exists!");
        }
        // тут перевірка на унікальність
        while (true) {
            recordBook = inputReader.readString( "Enter record book number: ");
            if (!serviceStudent.existsByRecordBookNumber(recordBook))
                break;
            System.out.println("Student with this record book number already exists!");
        }

        int course = inputReader.readIntInRange("Enter course(1-6)", 1, 6);
        Year yearOfAdmission = inputReader.readYearOfAdmission();
        StudentStatus status = StudentStatus.STUDIES;



        Student student = new Student(uniqueCode, recordBook, fullName, birthDate, contact, form, status, yearOfAdmission, course, studentGroup);
        serviceStudent.create(student);
        return this;
    }

    private Page editStudent() {
        Long id = inputReader.readLong("Enter student id to edit: ");
        Optional<Student> optionalStudent = serviceStudent.findById(id);
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
        Long id = inputReader.readLong("Enter student id to delete: ");
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

    private Page studentsSortedByCourse() {
        List<Student> students = serviceStudent.findAllSortedByCourse();

        for (Student student : students) {
            System.out.println("id - " + student.getId() + ", " + student);
        }
        return this;
    }

    private Page studentsFacultySortedSurname() {
        Long facultyId = inputReader.readLong("Enter faculty id: ");
        List<Student> students = serviceStudent.StudentsByFacultySortedSurname(facultyId);

        for (Student student : students) {
            System.out.println("id - " + student.getId() + ", " + student);
        }
        return this;
    }

    private Page studentsDepartmentSortedCourse() {
        Long departmentId = inputReader.readLong("Enter department id: ");
        List<Student> students = serviceStudent.StudentsByDepartmentSortedByCourse(departmentId);

        for (Student student : students) {
            System.out.println("id - " + student.getId() + ", " + student);
        }
        return this;
    }
    private Page studentsDepartmentSortedSurname() {
        Long departmentId = inputReader.readLong("Enter department id: ");
        List<Student> students = serviceStudent.StudentsByDepartmentSortedBySurname(departmentId);

        for (Student student : students) {
            System.out.println("id - " + student.getId() + ", " + student);
        }
        return this;
    }
}
