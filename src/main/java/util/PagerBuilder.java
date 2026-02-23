package util;

import security.AuthService;
import service.*;
import ui.InputReader;
import ui.Page;
import ui.finders.FacultyFinderInterface;
import ui.finders.UniversityFinderInterface;
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
    private final UniversityFinderInterface universityFinder;
    private final FacultyFinderInterface facultyFinder;
    public PagerBuilder(ServiceTeacherInterface serviceTeacher, ServiceStudentInterface serviceStudent, ServiceStudentGroupInterface serviceStudentGroup,
                        ServiceDepartmentInterface serviceDepartment, ServiceFacultyInterface serviceFaculty,
                        ServiceUniversityInterface serviceUniversity, AuthService authService,
                        ServiceUserInterface serviceUser, InputReader inputReader,
                        UniversityFinderInterface universityFinder, FacultyFinderInterface facultyFinder) {
        this.serviceTeacher = serviceTeacher;
        this.serviceStudent = serviceStudent;
        this.serviceStudentGroup = serviceStudentGroup;
        this.serviceDepartment = serviceDepartment;
        this.serviceFaculty = serviceFaculty;
        this.serviceUniversity = serviceUniversity;
        this.authService = authService;
        this.serviceUser = serviceUser;
        this.inputReader = inputReader;
        this.universityFinder = universityFinder;
        this.facultyFinder = facultyFinder;
    }

    public Page getDepartmentPage(){
        return new DepartmentPage(serviceDepartment, serviceFaculty,
                serviceTeacher, inputReader, facultyFinder);
    }
    public Page getFacultyPage(){
        return new FacultyPage(serviceFaculty, serviceTeacher, inputReader,
                universityFinder, this);
    }
    public Page getUniversityPage(){
        return new UniversityPage(serviceUniversity, inputReader, this);
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
    public Page getFindUniversityPage(){
        return new FindUniversityPage(inputReader, universityFinder);
    }
    public  Page getFindFacultyPage(){
        return new FindFacultyPage(inputReader, facultyFinder);
    }





}
