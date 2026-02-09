package ui;

import service.ServiceDepartmentInterface;
import java.io.Console;
import entity.Department;

public class DepartmentMenu {

    private final ServiceDepartmentInterface serviceDepartment;
    public DepartmentMenu(ServiceDepartmentInterface serviceDepartment) {
        this.serviceDepartment = serviceDepartment;
    }

    public void main(Console console) {
        while (true) {
            System.out.println(
                    "Departments \n 1 - Create Department\n 2 - Edit Department\n 3 - Delete Department\n 4 - Show All Departments\n 0 - Back"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createDepartment(console);
                    break;
                case 2:
                    editDepartment(console);
                    break;
                case 3:
                    deleteDepartment(console);
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

    private void createDepartment(Console console) {
        String name = console.readLine("Enter department name: ");
        String shortName = console.readLine("Enter short name: ");
        String cabinet = console.readLine("Enter cabinet/location: ");

        Department department = new Department(null, name, shortName, null, null, cabinet);
        serviceDepartment.create(department);
    }

    private void editDepartment(Console console) {
        Long id = Long.parseLong(console.readLine("Enter department id to edit: "));
        String name = console.readLine("Enter department name: ");
        String shortName = console.readLine("Enter short name: ");
        String cabinet = console.readLine("Enter cabinet/location: ");
    }

    private void deleteDepartment(Console console) {
        Long id = Long.parseLong(console.readLine("Enter department id to edit: "));
        serviceDepartment.delete(id);
    }

    private void showAllDepartments() {
        for (Department department : serviceDepartment.findAll())
            System.out.println(department);
    }
}
