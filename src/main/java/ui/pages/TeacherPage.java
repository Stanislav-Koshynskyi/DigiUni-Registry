package ui.pages;

import entity.*;
import service.ServiceTeacherInterface;
import ui.*;
import ui.finders.TeacherFinderInterface;
import util.Annotations;
import util.PagerBuilder;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TeacherPage extends BasePage {
    private final ServiceTeacherInterface serviceTeacher;
    private final PagerBuilder pagerBuilder;
    private final TeacherFinderInterface teacherFinder;
    public TeacherPage( ServiceTeacherInterface serviceTeacher, InputReader inputReader,
                        TeacherFinderInterface teacherFinder,PagerBuilder pagerBuilder) {
        super(inputReader);
        this.serviceTeacher = serviceTeacher;
        this.teacherFinder = teacherFinder;
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "Teacher";
    }

//    @Override
//    public List<MenuItem> getMenuItems() {
//        return List.of(
//            new MenuItem("Add teacher", Right.ADD, this::createTeacher),
//            new MenuItem("Edit teacher", Right.EDIT, this::editTeacher),
//            new MenuItem("Delete teacher", Right.DELETE, this::deleteTeacher),
//            new MenuItem("Show all teacher", Right.FIND, this:: showAllTeachers),
//            new MenuItem("Find teachers", Right.FIND, pagerBuilder::getTeacherFindPage)
//        );
//    }
    @Annotations(name = "Find teachers", right = Right.FIND, order = 5)
    private Page findTeacher() {
        return pagerBuilder.getTeacherFindPage();
    }
    @Annotations(name = "Add teacher", right = Right.ADD, order = 1)
    private Page createTeacher() {
        FullName fullName = inputReader.readfullName();
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
        String uniqueCode = uniqueCode();
        AcademicDegree degree = inputReader.readAcademicDegree();
        AcademicRank rank = inputReader.readAcademicRank();
        LocalDate dateOfEmployment = inputReader.readEmploymentDate();
        BigDecimal salary = inputReader.readBigDecimal("Enter salary: ");
        try {
            Teacher teacher = new Teacher(uniqueCode, fullName, birthDate, contact, degree, rank, dateOfEmployment, salary);
            serviceTeacher.create(teacher);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.out.println("Student not created");
        }
        return this;
    }
    @Annotations(name = "Edit teacher", right = Right.EDIT, order = 2)
    private Page editTeacher() {
        Optional<Teacher> optionalTeacher = teacherFinder.findAndSelect();
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
    @Annotations(name = "Delete teacher", right = Right.DELETE, order = 3)
    private Page deleteTeacher() {
        Optional<Teacher> teacherOptional = teacherFinder.findAndSelect();
        if (teacherOptional.isPresent()){
            serviceTeacher.delete(teacherOptional.get().getId());
        }

        return this;
    }
    @Annotations(name = "Show all teacher", right = Right.FIND, order = 4)
    private Page showAllTeachers() {
        List<Teacher> teachers = serviceTeacher.findAll();
        return new SortTeacherPage(inputReader, teachers);
    }

    private String uniqueEmail() {
        while (true) {
            String email = inputReader.readString("Enter email: ");
            if (!serviceTeacher.existsByEmail(email))
                return email;
            System.out.println("Email " + email + " already exists");
        }
    }

    private String uniquePhone() {
        while (true) {
            String phone = inputReader.readString("Enter phone: ");
            if (!serviceTeacher.existsByPhone(phone))
                return phone;
            System.out.println("Phone " + phone + " already exists");
        }
    }

    private String uniqueCode() {
        while (true) {
            String uniqueCode = inputReader.readString("Enter unique code: ");
            if (!serviceTeacher.existsByUniqueCode(uniqueCode))
                return uniqueCode;
            System.out.println("Teacher " + uniqueCode + " already exists");
        }
    }
}
