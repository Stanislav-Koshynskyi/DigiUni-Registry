package ui.pages;

import entity.*;
import service.ServiceTeacherInterface;
import ui.*;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TeacherPage extends BasePage {
    private final ServiceTeacherInterface serviceTeacher;
    public TeacherPage( ServiceTeacherInterface serviceTeacher, InputReader inputReader) {
        super(inputReader);
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
        FullName fullName = inputReader.readfullName();
        LocalDate birthDate = inputReader.readBirthDate();
        Contact contact = inputReader.readContact();

        String uniqueCode = inputReader.readString("Enter unique code: ");
        AcademicDegree degree = inputReader.readAcademicDegree();
        AcademicRank rank = inputReader.readAcademicRank();
        LocalDate dateOfEmployment = inputReader.readEmploymentDate();
        BigDecimal salary = inputReader.readBigDecimal("Enter salary: ");
        Teacher teacher = new Teacher(uniqueCode, fullName, birthDate, contact, degree, rank, dateOfEmployment, salary);
        serviceTeacher.create(teacher);
        return this;
    }

    private Page editTeacher() {
        Long id = inputReader.readLong("Enter teacher id: ");
        Optional<Teacher> optionalTeacher = serviceTeacher.findById(id);
        if (optionalTeacher.isEmpty()) {
            System.out.println("Teacher not found!!!");
            return this;
        }

        Teacher teacher = optionalTeacher.get();
        AcademicDegree degree = inputReader.readAcademicDegree();
        teacher.setAcademicDegree(degree);
        AcademicRank rank = inputReader.readAcademicRank();
        teacher.setAcademicRank(rank);
        BigDecimal salary = inputReader.readBigDecimal("Enter salary: ");
        teacher.setSalary(salary);
        serviceTeacher.update(teacher);
        return this;
    }

    private Page deleteTeacher() {
        Long id = inputReader.readLong("Enter teacher id: ");
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
