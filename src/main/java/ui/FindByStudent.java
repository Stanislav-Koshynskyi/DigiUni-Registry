package ui;

import entity.Student;
import entity.StudentGroup;
import entity.FullName;
import service.ServiceStudentInterface;
import service.ServiceStudentGroupInterface;
import java.io.Console;

public class FindByStudent {
    private final ServiceStudentInterface serviceStudent;
    private final ServiceStudentGroupInterface serviceStudentGroup;

    public FindByStudent(ServiceStudentInterface serviceStudent, ServiceStudentGroupInterface serviceStudentGroup) {
        this.serviceStudent = serviceStudent;
        this.serviceStudentGroup = serviceStudentGroup;
    }

    public void main(Console console) {
        while (true) {
            System.out.println("Find students by \n 1 - Full Name\n 2 - Course\n 3 - Group\n 0 - Back");
            int choice = readInt(console);

            switch (choice) {
                case 1:
                    findByFullName(console);
                    break;
                case 2:
                    findByCourse(console);
                    break;
                case 3:
                    findByGroup(console);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input!"); }
        }
    }

    private  void findByFullName(Console console){
        FullName fullName = PersonMenu.fullName(console);
        boolean found = false;
        for (Student student : serviceStudent.findAll()) {
            if (student.getFullName().equals(fullName)) {
                System.out.println(student);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Student with this name not found!");
        }
    }

    private void findByCourse(Console console){
        System.out.print("Enter course: ");
        int course = readInt(console);

        boolean found = false;
        for (Student student : serviceStudent.findAll()) {
            if (student.getCourse() == course) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found)
            System.out.println("Students not found!");
    }

    private void findByGroup(Console console){
        String groupName = console.readLine("Enter group name: ");
        var groups = serviceStudentGroup.findByName(groupName);

        if (groups.isEmpty()) {
            System.out.println("Group not found!");
            return;
        }

        for (StudentGroup group : groups) {
            System.out.println("Group: " + group.getName());

            var students = group.getStudents();
            if (students.isEmpty()) {
                System.out.println("No students in this group!");
                continue;
            }
            for (Student student : students)
                System.out.println(student);
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
