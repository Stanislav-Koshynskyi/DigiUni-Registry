import entity.Role;
import entity.User;
import repository.*;
import security.*;
import service.*;
import ui.ConsoleMenu;
import ui.Page;
import ui.PageDisplay;
import ui.pages.MainPage;
import util.PagerBuilder;
import util.Pretier;

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

        PasswordCoder coder = new BCryptPasswordEncoder();

        ServiceUserInterface serviceUserInterface = new ServiceUser(userRepository, coder);

        /*
        Initial user
         */
        serviceUserInterface.save(new User(Role.ADMIN,"admin","admin"));
        serviceUserInterface.save(new User(Role.MODERATOR,"moderator","moderator"));
        serviceUserInterface.save(new User(Role.USER,"user","user"));

        SessionInfo sessionInfo = new LocalSessionInfo();


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
