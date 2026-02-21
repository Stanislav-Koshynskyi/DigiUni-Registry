import repository.*;
import service.*;
import ui.ConsoleMenu;
import ui.Page;
import ui.PageDisplay;
import ui.pages.MainPage;
import util.PagerBuilder;

import java.io.Console;

public class Main {
    public static void main(String[] args) {
        DepartmentRepository departmentRepository = new InMemoryDepartmentRepository();
        FacultyRepository  facultyRepository = new InMemoryFacultyRepository();
        StudentRepository studentRepository = new InMemoryStudentRepository();
        StudentGroupRepository studentGroupRepository = new InMemoryStudentGroupRepository();
        TeacherRepository teacherRepository = new InMemoryTeacherRepository();
        UniversityRepository universityRepository = new InMemoryUniversityRepository();

        ServiceDepartmentInterface serviceDepartmentInterface = new ServiceDepartment(departmentRepository);
        ServiceFacultyInterface serviceFacultyInterface = new ServiceFaculty(facultyRepository);
        ServiceStudentInterface serviceStudentInterface = new ServiceStudent(studentRepository);
        ServiceTeacherInterface serviceTeacherInterface = new ServiceTeacher(teacherRepository);
        ServiceUniversityInterface serviceUniversityInterface = new ServiceUniversity(universityRepository);
        ServiceStudentGroupInterface serviceStudentGroupInterface = new ServiceStudentGroup(studentGroupRepository);

        PagerBuilder pagerBuilder = new PagerBuilder(serviceTeacherInterface, serviceStudentInterface, serviceStudentGroupInterface, serviceDepartmentInterface,
                serviceFacultyInterface, serviceUniversityInterface);
        Console console = System.console();
        Page mainPage = new MainPage(console, pagerBuilder);
        PageDisplay pageDisplay = new PageDisplay(console);
        pageDisplay.start(mainPage);

    }
}
