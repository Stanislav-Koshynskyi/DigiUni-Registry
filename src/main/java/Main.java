import entity.Role;
import entity.User;
import repository.*;
import security.*;
import service.*;
import ui.*;
import ui.finders.*;
import ui.pages.MainPage;
import util.BaseForTest;
import util.PagerBuilder;
import util.RepositoryLinker;

import java.io.Console;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        InMemoryDepartmentRepository departmentRepository = new InMemoryDepartmentRepository(Path.of("db", "departments.json"));
        InMemoryFacultyRepository  facultyRepository = new InMemoryFacultyRepository(Path.of("db", "faculties.json"));
        InMemoryStudentRepository studentRepository = new InMemoryStudentRepository(Path.of("db", "students.json"));
        InMemoryStudentGroupRepository studentGroupRepository = new InMemoryStudentGroupRepository(Path.of("db", "student_groups.json"));
        InMemoryTeacherRepository teacherRepository = new InMemoryTeacherRepository(Path.of("db", "teachers.json"));
        InMemoryUniversityRepository universityRepository = new InMemoryUniversityRepository(Path.of("db", "universities.json"));
        InMemoryUserRepository userRepository = new InMemoryUserRepository(Path.of("db", "users.json"));
        RepositoryLinker.linkRepository(universityRepository, facultyRepository
            ,departmentRepository, studentGroupRepository, studentRepository);
        // dont need now?
        //BaseForTest.seed(universityRepository, facultyRepository, departmentRepository, teacherRepository, studentGroupRepository, studentRepository);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        List<Stored> stored = List.of(universityRepository, facultyRepository
        ,departmentRepository, studentGroupRepository, studentRepository, userRepository
        ,teacherRepository);
        scheduler.scheduleAtFixedRate(()->stored.forEach(Stored::saveToFile),
                5, 5, TimeUnit.MINUTES);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            stored.forEach(Stored::saveToFile);
        }));


        ServiceDepartmentInterface serviceDepartmentInterface = new ServiceDepartment(departmentRepository);
        ServiceFacultyInterface serviceFacultyInterface = new ServiceFaculty(facultyRepository);
        ServiceStudentInterface serviceStudentInterface = new ServiceStudent(studentRepository);
        ServiceTeacherInterface serviceTeacherInterface = new ServiceTeacher(teacherRepository);
        ServiceUniversityInterface serviceUniversityInterface = new ServiceUniversity(universityRepository);
        ServiceStudentGroupInterface serviceStudentGroupInterface = new ServiceStudentGroup(studentGroupRepository);

        PasswordCoder coder = new BCryptPasswordEncoder();

        ServiceUserInterface serviceUserInterface = new ServiceUser(userRepository, coder);
        /* dont need now?
        serviceUserInterface.save(new User(Role.ADMIN, "admin", "admin"));
        serviceUserInterface.save(new User(Role.USER, "user", "user"));
        serviceUserInterface.save(new User(Role.MODERATOR, "moderator", "moderator"));
         */

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
