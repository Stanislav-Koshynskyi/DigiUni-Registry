import exception.LinkedException;
import exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import repository.*;
import security.*;
import service.*;
import ui.*;
import ui.finders.*;
import ui.pages.MainPage;
import util.PagerBuilder;
import util.RepositoryLinker;

import java.io.Console;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Slf4j
public class Main {
    public static void main(String[] args) {

        InMemoryDepartmentRepository departmentRepository = null;
        InMemoryFacultyRepository  facultyRepository = null;
        InMemoryStudentRepository studentRepository = null;
        InMemoryStudentGroupRepository studentGroupRepository = null;
        InMemoryTeacherRepository teacherRepository  = null;
        InMemoryUniversityRepository universityRepository =  null;
        InMemoryUserRepository userRepository = null;
        try {
            departmentRepository = new InMemoryDepartmentRepository(Path.of("db", "departments.json"));
            facultyRepository = new InMemoryFacultyRepository(Path.of("db", "faculties.json"));
            studentRepository = new InMemoryStudentRepository(Path.of("db", "students.json"));
            studentGroupRepository = new InMemoryStudentGroupRepository(Path.of("db", "student_groups.json"));
            teacherRepository = new InMemoryTeacherRepository(Path.of("db", "teachers.json"));
            universityRepository = new InMemoryUniversityRepository(Path.of("db", "universities.json"));
            userRepository = new InMemoryUserRepository(Path.of("db", "users.json"));
            RepositoryLinker.linkRepository(universityRepository, facultyRepository
                    , departmentRepository, studentGroupRepository, studentRepository);
        }catch (StorageException e){
            System.out.println("Disk error, we can`t read/writo into file");
            System.exit(1);
        }catch (LinkedException e){
            System.out.println("File read successfully but data connection broken");
            System.exit(1);
        }catch (Exception e){
            System.out.println("Unexpected error");
            System.exit(1);
        }




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


        ServiceDepartmentInterface serviceDepartmentInterface = new ServiceDepartment(departmentRepository, studentGroupRepository);
        ServiceFacultyInterface serviceFacultyInterface = new ServiceFaculty(facultyRepository, departmentRepository);
        ServiceStudentInterface serviceStudentInterface = new ServiceStudent(studentRepository);
        ServiceTeacherInterface serviceTeacherInterface = new ServiceTeacher(teacherRepository);
        ServiceUniversityInterface serviceUniversityInterface = new ServiceUniversity(universityRepository, facultyRepository);
        ServiceStudentGroupInterface serviceStudentGroupInterface = new ServiceStudentGroup(studentGroupRepository);

        PasswordCoder coder = new BCryptPasswordEncoder();

        ServiceUserInterface serviceUserInterface = new ServiceUser(userRepository, coder);
        /*// dont need now?
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
        log.info("Starting program");
        pageDisplay.start(mainPage);
        log.info("Ending program");
        System.exit(0);
    }
}
