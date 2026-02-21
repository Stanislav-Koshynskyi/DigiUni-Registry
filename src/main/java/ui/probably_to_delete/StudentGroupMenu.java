package ui.probably_to_delete;

import entity.Department;
import entity.StudentGroup;
import service.ServiceDepartmentInterface;
import service.ServiceStudentGroupInterface;
import java.util.Optional;
import java.io.Console;

public class StudentGroupMenu {
    private final ServiceDepartmentInterface serviceDepartment;
    private final ServiceStudentGroupInterface serviceStudentGroup;

    public StudentGroupMenu(ServiceStudentGroupInterface serviceStudentGroup, ServiceDepartmentInterface serviceDepartment){
        this.serviceStudentGroup = serviceStudentGroup;
        this.serviceDepartment = serviceDepartment;
    }

    public void main(Console console) {
        while (true) {
            System.out.println(
                    "Student Group \n 1 - Create Student Group\n 2 - Edit Student Group\n 3 - Delete Student Group\n 4 - Show All Student Group\n 0 - Back"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createStudentGroup(console);
                    break;
                case 2:
                    editStudentGroup(console);
                    break;
                case 3:
                    deleteStudentGroup(console);
                    break;
                case 4:
                    showStudentGroup();
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
    private void createStudentGroup(Console console) {
        String name = ConsoleMenu.readRequiredString(console, "Enter group name: ");

        Department department;
        while (true) {
            Long id = ConsoleMenu.readRequiredLong(console, "Enter department id(-1 to exit): ");
            if (id.equals(-1L)) return;
            Optional<Department> optionalDepartment = serviceDepartment.findById(id);
            if (optionalDepartment.isPresent()) {
                department = optionalDepartment.get();
                break;
            }
            else{
                System.out.println("Department not found");
            }
        }


        StudentGroup group = new StudentGroup(name, department);
        serviceStudentGroup.create(group);
    }

    private void editStudentGroup(Console console) {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter group id to edit: ");
        Optional<StudentGroup> optionalGroup = serviceStudentGroup.findById(id);

        if (optionalGroup.isEmpty()) {
            System.out.println("Group not found!");
            return;
        }
        StudentGroup group = optionalGroup.get();

        String name = console.readLine("Enter group name: ");
        if (!name.isBlank()) {
            group.setName(name);
        }

        serviceStudentGroup.update(group);
    }

    private void deleteStudentGroup(Console console) {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter group id to delete: ");
        serviceStudentGroup.delete(id);
    }

    private void showStudentGroup() {
        for (StudentGroup group : serviceStudentGroup.findAll()) {
            System.out.println("ID: " + group.getId() + group);
        }
    }
}
