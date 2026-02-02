package ui;

import java.io.Console;

public class FacultyMenu {
    public void main(Console console) {
        while (true) {
            System.out.println(
                    " Faculties \n" +
                            "1 - Create Faculty\n" +
                            "2 - Edit Faculty\n" +
                            "3 - Delete Faculty\n" +
                            "4 - Show All Faculties\n" +
                            "0 - Back"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createFaculty();
                    break;
                case 2:
                    editFaculty();
                    break;
                case 3:
                    deleteFaculty();
                    break;
                case 4:
                    showAllFaculties();
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


    private void createFaculty() {

    }

    private void editFaculty() {

    }

    private void deleteFaculty() {

    }

    private void showAllFaculties() {

    }
}
