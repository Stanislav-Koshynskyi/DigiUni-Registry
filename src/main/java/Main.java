import repository.*;
import security.*;
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
        UserRepository userRepository = new InMemoryUserRepository();

        ServiceDepartmentInterface serviceDepartmentInterface = new ServiceDepartment(departmentRepository);
        ServiceFacultyInterface serviceFacultyInterface = new ServiceFaculty(facultyRepository);
        ServiceStudentInterface serviceStudentInterface = new ServiceStudent(studentRepository);
        ServiceTeacherInterface serviceTeacherInterface = new ServiceTeacher(teacherRepository);
        ServiceUniversityInterface serviceUniversityInterface = new ServiceUniversity(universityRepository);
        ServiceStudentGroupInterface serviceStudentGroupInterface = new ServiceStudentGroup(studentGroupRepository);
        ServiceUserInterface serviceUserInterface = new ServiceUser(userRepository);

        SessionInfo sessionInfo = new LocalSessionInfo();

        PasswordCoder coder = new BCryptPasswordEncoder();
        AuthService authService = new LocalAuthService(coder, serviceUserInterface, sessionInfo);

        PagerBuilder pagerBuilder = new PagerBuilder(serviceTeacherInterface, serviceStudentInterface, serviceStudentGroupInterface, serviceDepartmentInterface,
                serviceFacultyInterface, serviceUniversityInterface,
                authService, serviceUserInterface);
        Console console = System.console();
        Page mainPage = new MainPage(console, pagerBuilder);
        PageDisplay pageDisplay = new PageDisplay(console, authService);
        pageDisplay.start(mainPage);

    }
}
