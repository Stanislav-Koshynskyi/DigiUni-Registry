package util;

import security.AuthService;
import service.*;
import ui.InputReader;
import ui.Page;
import ui.finders.*;
import ui.pages.*;

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
    private final DepartmentFinderInterface departmentFinder;
    private final StudentGroupFinderInterface studentGroupFinder;
    private final StudentFinderInterface studentFinder;
    private final TeacherFinderInterface teacherFinder;
    public PagerBuilder(ServiceTeacherInterface serviceTeacher, ServiceStudentInterface serviceStudent, ServiceStudentGroupInterface serviceStudentGroup,
                        ServiceDepartmentInterface serviceDepartment, ServiceFacultyInterface serviceFaculty,
                        ServiceUniversityInterface serviceUniversity, AuthService authService,
                        ServiceUserInterface serviceUser, InputReader inputReader,
                        UniversityFinderInterface universityFinder, FacultyFinderInterface facultyFinder,
                        DepartmentFinderInterface departmentFinder, StudentGroupFinderInterface studentGroupFinder, StudentFinderInterface studentFinder,
                        TeacherFinderInterface teacherFinder) {
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
        this.departmentFinder = departmentFinder;
        this.studentGroupFinder = studentGroupFinder;
        this.studentFinder = studentFinder;
        this.teacherFinder = teacherFinder;
    }

    public Page getDepartmentPage(){
        return new DepartmentPage(serviceDepartment,  teacherFinder, inputReader, facultyFinder, departmentFinder, this);
    }
    public Page getFacultyPage(){
        return new FacultyPage(serviceFaculty, teacherFinder, inputReader,
                universityFinder, facultyFinder, this);
    }
    public Page getUniversityPage(){
        return new UniversityPage(serviceUniversity, inputReader, universityFinder, this);
    }
    public Page getTeacherPage(){
        return new TeacherPage(serviceTeacher, inputReader, teacherFinder, this);
    }
    public  Page getStudentPage(){
        return new StudentPage(studentGroupFinder,
                serviceStudent, inputReader, studentFinder,this);
    }
    public Page getStudentGroupPage(){
        return new StudentGroupPage( serviceStudentGroup, inputReader, departmentFinder, studentGroupFinder, this);
    }
    public Page getUserPage(){
        return new UserPage(authService,
                serviceUser, inputReader, this);
    }
    public Page getFindUniversityPage(){
        return new FindUniversityPage(inputReader, universityFinder);
    }
    public  Page getFindFacultyPage(){
        return new FindFacultyPage(inputReader, facultyFinder);
    }
    public Page getDepartmentFindPage() {
        return new FindDepartmentPage(inputReader, departmentFinder);
    }
    public Page getStudentGroupFindPage() {
        return new FindStudentGroupPage(inputReader, studentGroupFinder);
    }
    public Page getStudentFindPage() {
        return new FindStudentPage(inputReader, studentFinder);
    }
    public Page getTeacherFindPage() {
        return new FindTeacherPage(inputReader, teacherFinder);
    }
    public Page getAdminPage() {
        return new AdminPage(inputReader, serviceUser);
    }
}
