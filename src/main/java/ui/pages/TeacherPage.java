package ui.pages;

import entity.*;
import service.ServiceTeacherInterface;
import ui.*;
import util.Reader;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class TeacherPage extends BasePage {
    private final ServiceTeacherInterface serviceTeacher;
    public TeacherPage(Console console, ServiceTeacherInterface serviceTeacher) {
        super(console);
        this.serviceTeacher = serviceTeacher;
    }

    @Override
    public String getTitle() {
        return "Teacher";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
            new MenuItem("Add teacher", Right.ADD, this::createTeacher),
            new MenuItem("Edit teacher", Right.EDIT, this::editTeacher),
            new MenuItem("Delete teacher", Right.DELETE, this::deleteTeacher),
            new MenuItem("Show all teacher", Right.FIND, this:: showAllTeachers)
        );
    }
    private Page createTeacher() {
        FullName fullName = Reader.fullName(console);
        LocalDate birthDate = Reader.readBirthDate(console);
        Contact contact = Reader.readContact(console);

        String uniqueCode = Reader.readString(console, "Enter unique code: ");
        AcademicDegree degree = Reader.readAcademicDegree(console);
        AcademicRank rank = Reader.readAcademicRank(console);
        LocalDate dateOfEmployment = Reader.readEmploymentDate(console);
        BigDecimal salary = Reader.readBigDecimal(console, "Enter salary: ");
        Teacher teacher = new Teacher(uniqueCode, fullName, birthDate, contact, degree, rank, dateOfEmployment, salary);
        serviceTeacher.create(teacher);
        return this;
    }

    private Page editTeacher() {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter teacher id: ");
        Optional<Teacher> optionalTeacher = serviceTeacher.findById(id);
        if (optionalTeacher.isEmpty()) {
            System.out.println("Teacher not found!!!");
            return this;
        }

        Teacher teacher = optionalTeacher.get();
        AcademicDegree degree = Reader.readAcademicDegree(console);
        teacher.setAcademicDegree(degree);
        AcademicRank rank = Reader.readAcademicRank(console);
        teacher.setAcademicRank(rank);
        BigDecimal salary = Reader.readBigDecimal(console, "Enter salary: ");
        teacher.setSalary(salary);
        serviceTeacher.update(teacher);
        return this;
    }

    private Page deleteTeacher() {
        Long id = Reader.readLong(console, "Enter teacher id: ");
        Optional<Teacher> optionalTeacher = serviceTeacher.findById(id);
        if (optionalTeacher.isEmpty()) {
            System.out.println("Teacher not found!!!");
            return this;
        }

        serviceTeacher.delete(id);
        return this;
    }

    private Page showAllTeachers() {
        for (Teacher teacher : serviceTeacher.findAll()) {
            System.out.println("id -" + teacher.getId() + ", " + teacher);
        }
        return this;
    }
}
