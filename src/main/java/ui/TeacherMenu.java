package ui;

import java.io.Console;

public class TeacherMenu {
    public void main(Console console) {
        while (true) {
            System.out.println(
                    " Teachers \n" +
                            "1 - Add teacher\n" +
                            "2 - Edit teacher\n" +
                            "3 - Delete teacher\n" +
                            "4 - Show all teachers\n" +
                            "0 - Back"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createTeacher();
                    break;
                case 2:
                    editTeacher();
                    break;
                case 3:
                    deleteTeacher();
                    break;
                case 4:
                    showAllTeachers();
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

    private void createTeacher(){
    }

    private void editTeacher() {
    }

    private void deleteTeacher() {
    }

    private void showAllTeachers() {
    }
}
