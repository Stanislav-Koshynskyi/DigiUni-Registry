package util;

import security.AuthService;
import service.*;
import ui.InputReader;
import ui.Page;
import ui.pages.*;

import java.io.Console;

public class PagerBuilder {
    private final ServiceTeacherInterface serviceTeacher;
    private final ServiceStudentInterface serviceStudent;
    private final ServiceStudentGroupInterface serviceStudentGroup;
    private final ServiceDepartmentInterface serviceDepartment;
    private final ServiceFacultyInterface serviceFaculty;
    private final ServiceUniversityInterface serviceUniversity;
    private final AuthService authService;
    private final ServiceUserInterface serviceUser;
    private final InputReader inputReader;
    public PagerBuilder(ServiceTeacherInterface serviceTeacher, ServiceStudentInterface serviceStudent, ServiceStudentGroupInterface serviceStudentGroup,
                        ServiceDepartmentInterface serviceDepartment, ServiceFacultyInterface serviceFaculty,
                        ServiceUniversityInterface serviceUniversity, AuthService authService,
                        ServiceUserInterface serviceUser, InputReader inputReader) {
        this.serviceTeacher = serviceTeacher;
        this.serviceStudent = serviceStudent;
        this.serviceStudentGroup = serviceStudentGroup;
        this.serviceDepartment = serviceDepartment;
        this.serviceFaculty = serviceFaculty;
        this.serviceUniversity = serviceUniversity;
        this.authService = authService;
        this.serviceUser = serviceUser;
        this.inputReader = inputReader;
    }

    public Page getDepartmentPage(Console console){
        return new DepartmentPage(console, serviceDepartment, serviceFaculty,
                serviceTeacher, inputReader);
    }
    public Page getFacultyPage(Console console){
        return new FacultyPage(console, serviceFaculty, serviceUniversity, serviceTeacher,
                inputReader);
    }
    public Page getUniversityPage(Console console){
        return new UniversityPage(console,
                serviceUniversity, inputReader);
    }
    public Page getTeacherPage(Console console){
        return new TeacherPage(console,
                serviceTeacher, inputReader);
    }
    public  Page getStudentPage(Console console){
        return new StudentPage(console, serviceStudentGroup,
                serviceStudent, inputReader);
    }
    public Page getStudentGroupPage(Console console){
        return new StudentGroupPage(console, serviceDepartment,
                serviceStudentGroup, inputReader);
    }
    public Page getUserPage(Console console){
        return new UserPage(console, authService,
                serviceUser, inputReader);
    }





}
