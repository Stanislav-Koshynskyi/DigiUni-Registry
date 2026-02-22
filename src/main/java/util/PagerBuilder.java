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

    public Page getDepartmentPage(){
        return new DepartmentPage(serviceDepartment, serviceFaculty,
                serviceTeacher, inputReader);
    }
    public Page getFacultyPage(){
        return new FacultyPage(serviceFaculty, serviceUniversity, serviceTeacher,
                inputReader);
    }
    public Page getUniversityPage(){
        return new UniversityPage(serviceUniversity, inputReader);
    }
    public Page getTeacherPage(){
        return new TeacherPage(serviceTeacher, inputReader);
    }
    public  Page getStudentPage(){
        return new StudentPage(serviceStudentGroup,
                serviceStudent, inputReader);
    }
    public Page getStudentGroupPage(){
        return new StudentGroupPage(serviceDepartment,
                serviceStudentGroup, inputReader);
    }
    public Page getUserPage(){
        return new UserPage(authService,
                serviceUser, inputReader);
    }





}
