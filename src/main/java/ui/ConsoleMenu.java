package ui;
import java.io.Console;

import repository.StudentRepository;
import repository.InMemoryStudentRepository;
import service.ServiceStudentInterface;
import service.ServiceStudent;

import repository.DepartmentRepository;
import repository.InMemoryDepartmentRepository;
import service.ServiceDepartmentInterface;
import service.ServiceDepartment;

import repository.FacultyRepository;
import repository.InMemoryFacultyRepository;
import service.ServiceFacultyInterface;
import service.ServiceFaculty;

import repository.TeacherRepository;
import repository.InMemoryTeacherRepository;
import service.ServiceTeacherInterface;
import service.ServiceTeacher;

public class ConsoleMenu {

    private final FacultyRepository facultyRepository = new InMemoryFacultyRepository();
    private final ServiceFacultyInterface serviceFaculty = new ServiceFaculty(facultyRepository);
    private final FacultyMenu facultyMenu = new FacultyMenu(serviceFaculty);

    private final DepartmentRepository departmentRepository = new InMemoryDepartmentRepository();
    private final ServiceDepartmentInterface serviceDepartment = new ServiceDepartment(departmentRepository);
    private final DepartmentMenu departmentMenu = new DepartmentMenu(serviceDepartment);

    StudentRepository studentRepository = new InMemoryStudentRepository();
    ServiceStudentInterface serviceStudent = new ServiceStudent(studentRepository);
    private final StudentMenu studentMenu = new StudentMenu(serviceStudent);

    TeacherRepository teacherRepository = new InMemoryTeacherRepository();
    ServiceTeacherInterface serviceTeacher = new ServiceTeacher(teacherRepository);
    private final TeacherMenu teacherMenu = new TeacherMenu(serviceTeacher);

    public void main() {
        var console = System.console();

        while (true) {
            System.out.println(
                    "MainMenu\n 1 - Faculty\n 2 - Department\n 3 - Student\n 4 - Teacher\n 0 - Exit"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    facultyMenu.main(console);
                    break;
                case 2:
                    departmentMenu.main(console);
                    break;
                case 3:
                    studentMenu.main(console);
                    break;
                case 4:
                    teacherMenu.main(console);
                    break;
                case 0:
                    System.out.println("Exit");
                    return;
                default:
                    System.out.println("Invalid input");
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
