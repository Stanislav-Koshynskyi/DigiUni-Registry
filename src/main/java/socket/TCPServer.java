package socket;

import security.AuthService;
import security.LocalAuthService;
import security.LocalSessionInfo;
import security.PasswordCoder;
import service.*;
import ui.InputReader;
import ui.finders.*;
import util.PagerBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private final int port;
    private final PasswordCoder coder;
    private final ServiceUserInterface serviceUser;
    private final ServiceTeacherInterface serviceTeacher;
    private final ServiceStudentInterface serviceStudent;
    private final ServiceStudentGroupInterface serviceStudentGroup;
    private final ServiceDepartmentInterface serviceDepartment;
    private final ServiceFacultyInterface serviceFaculty;
    private final ServiceUniversityInterface serviceUniversity;

    public TCPServer(int port, PasswordCoder coder,
                     ServiceUserInterface serviceUser,
                     ServiceTeacherInterface serviceTeacher,
                     ServiceStudentInterface serviceStudent,
                     ServiceStudentGroupInterface serviceStudentGroup,
                     ServiceDepartmentInterface serviceDepartment,
                     ServiceFacultyInterface serviceFaculty,
                     ServiceUniversityInterface serviceUniversity) {
        this.port = port;
        this.coder = coder;
        this.serviceUser = serviceUser;
        this.serviceTeacher = serviceTeacher;
        this.serviceStudent = serviceStudent;
        this.serviceStudentGroup = serviceStudentGroup;
        this.serviceDepartment = serviceDepartment;
        this.serviceFaculty = serviceFaculty;
        this.serviceUniversity = serviceUniversity;
    }

    public void start() throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("TCP Server start on port " + port);
            while (true) {
                Socket client = server.accept();
                System.out.println("Client connect " + client.getInetAddress());
                try {
                    AuthService clientAuth = new LocalAuthService(coder, serviceUser, new LocalSessionInfo());
                    InputReader reader = new SocketConsoleReader(client);
                    UniversityFinderInterface universityFinder = new UniversityFinder(serviceUniversity, reader);
                    FacultyFinderInterface facultyFinder = new FacultyFinder(serviceFaculty, reader, universityFinder);
                    DepartmentFinderInterface departmentFinder = new DepartmentFinder(reader, universityFinder, facultyFinder, serviceDepartment);
                    StudentGroupFinderInterface studentGroupFinder = new StudentGroupFinder(reader, universityFinder, facultyFinder, departmentFinder, serviceStudentGroup);
                    StudentFinderInterface studentFinder = new StudentFinder(reader, universityFinder, facultyFinder, departmentFinder, studentGroupFinder, serviceStudent);
                    TeacherFinderInterface teacherFinder = new TeacherFinder(serviceTeacher, reader);
                    PagerBuilder clientPager = new PagerBuilder(
                            serviceTeacher, serviceStudent, serviceStudentGroup,
                            serviceDepartment, serviceFaculty, serviceUniversity,
                            clientAuth, serviceUser, reader,
                            universityFinder, facultyFinder, departmentFinder, studentGroupFinder, studentFinder, teacherFinder
                    );
                    new Thread(new SocketClient(client, clientPager, reader)).start();
                } catch (IOException e) {
                    System.out.println("Failed to init client " + e.getMessage());
                    client.close();
                }
            }
        }
    }
}