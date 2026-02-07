package ui;

import java.io.Console;
import entity.Student;
import service.ServiceStudentInterface;

public class StudentMenu {
    private final ServiceStudentInterface serviceStudent;

    public StudentMenu(ServiceStudentInterface serviceStudent) {
        this.serviceStudent = serviceStudent;
    }
    public void main(Console console) {
        while (true) {
            System.out.println(" Student \n 1 - Add student \n 2 - Edit student\n 3 - Delete student \n 4 - Show all students\n 5 - Transfer student\n 0 - Back");

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    showAllStudents();
                    break;
                case 5:
                    transferStudent();
                    break;
                case 0:
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

    private void createStudent() {

    }

    private void editStudent() {

    }

    private void deleteStudent() {

    }

    private void showAllStudents() {
        for (Student student : serviceStudent.findAll())
            System.out.println(student);
    }

    private void transferStudent() {

    }
}
