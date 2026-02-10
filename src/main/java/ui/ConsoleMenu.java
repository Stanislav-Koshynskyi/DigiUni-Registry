package ui;
import java.io.Console;

import repository.*;
import service.*;

public class ConsoleMenu {
    private final UniversityRepository universityRepository = new InMemoryUniversityRepository();
    private final ServiceUniversityInterface serviceUniversity = new ServiceUniversity(universityRepository);
    private final UniversityMenu universityMenu = new UniversityMenu(serviceUniversity);

    TeacherRepository teacherRepository = new InMemoryTeacherRepository();
    ServiceTeacherInterface serviceTeacher = new ServiceTeacher(teacherRepository);
    private final TeacherMenu teacherMenu = new TeacherMenu(serviceTeacher);

    private final FacultyRepository facultyRepository = new InMemoryFacultyRepository();
    private final ServiceFacultyInterface serviceFaculty = new ServiceFaculty(facultyRepository);
    private final FacultyMenu facultyMenu = new FacultyMenu(serviceFaculty, serviceUniversity, serviceTeacher);

    private final DepartmentRepository departmentRepository = new InMemoryDepartmentRepository();
    private final ServiceDepartmentInterface serviceDepartment = new ServiceDepartment(departmentRepository);
    private final DepartmentMenu departmentMenu = new DepartmentMenu(serviceDepartment, serviceFaculty, serviceTeacher);

    StudentRepository studentRepository = new InMemoryStudentRepository();
    StudentGroupRepository studentGroupRepository = new InMemoryStudentGroupRepository();
    ServiceStudentInterface serviceStudent = new ServiceStudent(studentRepository);
    ServiceStudentGroupInterface serviceStudentGroup = new ServiceStudentGroup(studentGroupRepository);
    private final StudentMenu studentMenu = new StudentMenu(serviceStudent, serviceStudentGroup);

    public void main() {
        var console = System.console();

        while (true) {
            System.out.println(
                    "MainMenu\n 1 - University\n 2 - Faculty\n 3 - Department\n 4 - Student\n 5 - Teacher\n 0 - Exit"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    universityMenu.main(console);
                    break;
                case 2:
                    facultyMenu.main(console);
                    break;
                case 3:
                    departmentMenu.main(console);
                    break;
                case 4:
                    studentMenu.main(console);
                    break;
                case 5:
                    teacherMenu.main(console);
                    break;
                case 0:
                    System.out.println("Exit");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    private int readInt(Console console) {
        try {
            return Integer.parseInt(console.readLine("Your choice: "));
        } catch (NumberFormatException e){
            return -1;
        }
    }
}
