package util;

import security.AuthService;
import service.*;
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

    public PagerBuilder(ServiceTeacherInterface serviceTeacher, ServiceStudentInterface serviceStudent, ServiceStudentGroupInterface serviceStudentGroup,
                        ServiceDepartmentInterface serviceDepartment, ServiceFacultyInterface serviceFaculty,
                        ServiceUniversityInterface serviceUniversity, AuthService authService,
                        ServiceUserInterface serviceUser) {
        this.serviceTeacher = serviceTeacher;
        this.serviceStudent = serviceStudent;
        this.serviceStudentGroup = serviceStudentGroup;
        this.serviceDepartment = serviceDepartment;
        this.serviceFaculty = serviceFaculty;
        this.serviceUniversity = serviceUniversity;
        this.authService = authService;
        this.serviceUser = serviceUser;
    }

    public Page getDepartmentPage(Console console){
        return new DepartmentPage(serviceDepartment, serviceFaculty,
                serviceTeacher, console);
    }
    public Page getFacultyPage(Console console){
        return new FacultyPage(console, serviceFaculty, serviceUniversity, serviceTeacher);
    }
    public Page getUniversityPage(Console console){
        return new UniversityPage(console, serviceUniversity);
    }
    public Page getTeacherPage(Console console){
        return new TeacherPage(console, serviceTeacher);
    }
    public  Page getStudentPage(Console console){
        return new StudentPage(console, serviceStudent, serviceStudentGroup);
    }
    public Page getStudentGroupPage(Console console){
        return new StudentGroupPage(console, serviceDepartment, serviceStudentGroup);
    }
    public Page getUserPage(Console console){
        return new UserPage(console, authService, serviceUser);
    }





}
