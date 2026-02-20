package ui.pages;

import entity.Right;
import service.ServiceDepartmentInterface;
import service.ServiceFacultyInterface;
import service.ServiceTeacherInterface;
import ui.BasePage;
import ui.MenuItem;

import java.io.Console;
import java.util.List;

public class DepartmentPage extends BasePage {
    private final ServiceDepartmentInterface serviceDepartment;
    private final ServiceFacultyInterface serviceFaculty;
    private final ServiceTeacherInterface serviceTeacher;
    public DepartmentPage(ServiceDepartmentInterface serviceDepartment, ServiceFacultyInterface serviceFaculty,ServiceTeacherInterface serviceTeacher, Console console){
        super(console);
        this.serviceDepartment = serviceDepartment;
        this.serviceFaculty = serviceFaculty;
        this.serviceTeacher = serviceTeacher;
    }


    @Override
    public String getTitle() {
        return "Department";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Create department", Right.ADD, this::createDepartment),
                new MenuItem("Edit department", Right.EDIT, this::)
        );
    }


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

    private int readInt() {
        try {
            return Integer.parseInt(console.readLine("Your choice: "));
        } catch (NumberFormatException e){
            return -1;
        }
    }

    private void createDepartment() {
        String uniqueCode = ConsoleMenu.readRequiredString(console, "Enter unique code: ");
        String name = ConsoleMenu.readRequiredString(console, "Enter department name: ");
        String shortName;
        while (true) {
            shortName = console.readLine("Enter short name: ");
            if (shortName.length() <= Department.MAX_SHORT_NAME_LENGTH) break;
            else {
                System.out.println("Short name must be shorter than " + Department.MAX_SHORT_NAME_LENGTH);
            }
        }
        String cabinet = ConsoleMenu.readRequiredString(console, "Enter cabinet/location: ");
        Teacher headOfDepartment = null;
        Long teacherId;
        while (true) {
            teacherId = ConsoleMenu.readRequiredLong(console, "Enter teacher id(if vacant write -1): ");
            if (teacherId.equals(Long.valueOf(-1))) break;
            Optional<Teacher> optionalTeacher = serviceTeacher.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                headOfDepartment = optionalTeacher.get();
                break;
            } else {
                System.out.println("Teacher not found!!!");
            }

        }
        Faculty faculty;
        while (true) {
            Long facultyId = ConsoleMenu.readRequiredLong(console, "Enter faculty id(-1 to exit): ");
            if (facultyId.equals(Long.valueOf(-1))) return;
            Optional<Faculty> optionalFaculty = serviceFaculty.findById(facultyId);
            if (optionalFaculty.isPresent()) {
                faculty = optionalFaculty.get();
                break;
            } else {
                System.out.println("Faculty not found!!!");
            }
        }

            Department department = new Department(uniqueCode, name, shortName, faculty, headOfDepartment, cabinet);
            serviceDepartment.create(department);

    }

    private void editDepartment(Console console) {
        Long id =ConsoleMenu.readRequiredLong(console, "Enter department id to edit: ");
        Optional<Department> optionalDepartment = serviceDepartment.findById(id);
        if (optionalDepartment.isEmpty()) {
            System.out.println("Department not found!!!");
            return;
        }

        Department department = optionalDepartment.get();
        String name = console.readLine("Enter department name: ");
        String shortName;
        while (true) {
            shortName = console.readLine("Enter short name: ");
            if (shortName.length() <= Department.MAX_SHORT_NAME_LENGTH) break;
            else{
                System.out.println("Short name must be shorter than " + Department.MAX_SHORT_NAME_LENGTH);
            }
        }
        String cabinet = console.readLine("Enter cabinet/location: ");

        if (!name.isBlank())
            department.setName(name);
        if (!shortName.isBlank())
            department.setShortName(shortName);
        if (!cabinet.isBlank())
            department.setCabinet(cabinet);

        serviceDepartment.update(department);
    }

    private void deleteDepartment(Console console) {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter department id to delete: ");
        Optional<Department> optionalDepartment = serviceDepartment.findById(id);
        if (optionalDepartment.isEmpty()) {
            System.out.println("Department not found!!!");
            return;
        }
        serviceDepartment.delete(id);
    }

    private void showAllDepartments() {
        for (Department department : serviceDepartment.findAll())
            System.out.println("id -" + department.getId() + ", " + department);
    }
}

