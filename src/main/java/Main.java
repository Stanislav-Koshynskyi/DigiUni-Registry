import entity.Role;
import entity.User;
import entity.find_criteria.UniversityFind;
import repository.*;
import security.*;
import service.*;
import ui.*;
import ui.finders.*;
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
        serviceUserInterface.save(new User(Role.ADMIN, "admin", "admin"));
        serviceUserInterface.save(new User(Role.USER, "user", "user"));
        serviceUserInterface.save(new User(Role.MODERATOR, "moderator", "moderator"));


        SessionInfo sessionInfo = new LocalSessionInfo();


        AuthService authService = new LocalAuthService(coder, serviceUserInterface, sessionInfo);

        Console console = System.console();
        InputReader inputReader = new ConsoleReader(console);

        UniversityFinderInterface universityFinderInterface = new UniversityFinder(serviceUniversityInterface, inputReader);
        FacultyFinderInterface facultyFinder = new FacultyFinder(serviceFacultyInterface, inputReader, universityFinderInterface);
        DepartmentFinderInterface departmentFinder = new DepartmentFinder(inputReader, universityFinderInterface,
                facultyFinder, serviceDepartmentInterface);
        StudentGroupFinderInterface studentGroupFinder = new StudentGroupFinder(inputReader,
                universityFinderInterface, facultyFinder, departmentFinder, serviceStudentGroupInterface);
        StudentFinderInterface studentFinder = new StudentFinder(inputReader, universityFinderInterface, facultyFinder,
                departmentFinder,studentGroupFinder, serviceStudentInterface);
        TeacherFinderInterface teacherFinder = new TeacherFinder(serviceTeacherInterface, inputReader);

        PagerBuilder pagerBuilder = new PagerBuilder(serviceTeacherInterface, serviceStudentInterface,
                serviceStudentGroupInterface, serviceDepartmentInterface,
                serviceFacultyInterface, serviceUniversityInterface,
                authService, serviceUserInterface, inputReader,
                universityFinderInterface,  facultyFinder, departmentFinder,
                studentGroupFinder, studentFinder, teacherFinder);

        Page mainPage = new MainPage(inputReader, pagerBuilder);
        PageDisplay pageDisplay = new PageDisplay(authService, inputReader);
        pageDisplay.start(mainPage);

    }
}
