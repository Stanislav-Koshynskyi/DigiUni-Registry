import repository.*;
import security.*;
import service.*;
import ui.*;
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

        PasswordCoder coder = new BCryptPasswordEncoder();

        ServiceUserInterface serviceUserInterface = new ServiceUser(userRepository, coder);

        SessionInfo sessionInfo = new LocalSessionInfo();


        AuthService authService = new LocalAuthService(coder, serviceUserInterface, sessionInfo);

        Console console = System.console();
        InputReader inputReader = new ConsoleReader(console);
        PagerBuilder pagerBuilder = new PagerBuilder(serviceTeacherInterface, serviceStudentInterface, serviceStudentGroupInterface, serviceDepartmentInterface,
                serviceFacultyInterface, serviceUniversityInterface,
                authService, serviceUserInterface, inputReader);

        Page mainPage = new MainPage(inputReader, pagerBuilder);
        PageDisplay pageDisplay = new PageDisplay(console, authService, inputReader);
        pageDisplay.start(mainPage);

    }
}
