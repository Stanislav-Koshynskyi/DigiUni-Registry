package ui;

import service.ServiceDepartmentInterface;
import service.ServiceFacultyInterface;
import java.io.Console;
import entity.Department;
import entity.Faculty;
import entity.Teacher;
import service.ServiceTeacherInterface;
import java.util.Optional;

public class DepartmentMenu {

    private final ServiceDepartmentInterface serviceDepartment;
    private final ServiceFacultyInterface serviceFaculty;
    private final ServiceTeacherInterface serviceTeacher;

    public DepartmentMenu(ServiceDepartmentInterface serviceDepartment, ServiceFacultyInterface serviceFaculty, ServiceTeacherInterface serviceTeacher) {
        this.serviceDepartment = serviceDepartment;
        this.serviceFaculty = serviceFaculty;
        this.serviceTeacher = serviceTeacher;
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
        String uniqueCode = console.readLine("Enter unique code: ");
        String name = console.readLine("Enter department name: ");
        String shortName = console.readLine("Enter short name: ");
        String cabinet = console.readLine("Enter cabinet/location: ");
        Teacher headOfDepartment = null;
        Long teacherId = Long.parseLong(console.readLine("Enter teacher id: "));
        Optional<Teacher> optionalTeacher = serviceTeacher.findById(teacherId);
        if (optionalTeacher.isEmpty()) {
            System.out.println("Teacher not found!!!");
            return;
        }
        headOfDepartment = optionalTeacher.get();
        Long facultyId = Long.parseLong(console.readLine("Enter faculty id: "));
        Optional<Faculty> optionalFaculty = serviceFaculty.findById(facultyId);
        if (optionalFaculty.isEmpty()) {
            System.out.println("Faculty not found!!!");
            return;
        }
        Faculty faculty = optionalFaculty.get();

        Department department = new Department(uniqueCode, name, shortName, faculty, headOfDepartment, cabinet);
        serviceDepartment.create(department);
    }

    private void editDepartment(Console console) {
        Long id = Long.parseLong(console.readLine("Enter department id to edit: "));
        Optional<Department> optionalDepartment = serviceDepartment.findById(id);
        if (optionalDepartment.isEmpty()) {
            System.out.println("Department not found!!!");
            return;
        }

        Department department = optionalDepartment.get();
        String name = console.readLine("Enter department name: ");
        String shortName = console.readLine("Enter short name: ");
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
        Long id = Long.parseLong(console.readLine("Enter department id to edit: "));
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
