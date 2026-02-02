package ui;

import java.io.Console;

public class DepartmentMenu {
    public void main(Console console) {
        while (true) {
            System.out.println(
                    " Departments \n" +
                            "1 - Create Department\n" +
                            "2 - Edit Department\n" +
                            "3 - Delete Department\n" +
                            "4 - Show All Departments\n" +
                            "0 - Back"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createDepartment();
                    break;
                case 2:
                    editDepartment();
                    break;
                case 3:
                    deleteDepartment();
                    break;
                case 4:
                    showAllDepartments();
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

    private void createDepartment() {

    }

    private void editDepartment() {

    }

    private void deleteDepartment() {

    }

    private void showAllDepartments() {

    }
}
