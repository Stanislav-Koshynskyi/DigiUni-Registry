package util;

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

    public PagerBuilder(ServiceTeacherInterface serviceTeacherInterface,
        ServiceStudentInterface serviceStudentInterface, ServiceStudentGroupInterface serviceStudentGroupInterface,
        ServiceDepartmentInterface serviceDepartmentInterface, ServiceFacultyInterface serviceFacultyInterface,
        ServiceUniversityInterface serviceUniversityInterface) {
        this.serviceTeacher = serviceTeacherInterface;
        this.serviceStudent = serviceStudentInterface;
        this.serviceStudentGroup = serviceStudentGroupInterface;
        this.serviceDepartment = serviceDepartmentInterface;
        this.serviceFaculty = serviceFacultyInterface;
        this.serviceUniversity = serviceUniversityInterface;
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





}
