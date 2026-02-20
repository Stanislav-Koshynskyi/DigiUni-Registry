package ui.pages;

import entity.Department;
import entity.Faculty;
import entity.Right;
import entity.Teacher;
import service.ServiceDepartmentInterface;
import service.ServiceFacultyInterface;
import service.ServiceTeacherInterface;
import ui.BasePage;
import ui.MenuItem;
import ui.Page;
import util.Reader;

import java.io.Console;
import java.util.List;
import java.util.Optional;

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
                new MenuItem("Edit department", Right.EDIT, this::editDepartment),
                new MenuItem("Delete Department", Right.DELETE, this::deleteDepartment),
                new MenuItem("Show all departments", Right.FIND, this::showAllDepartments)
        );
    }

    private Page createDepartment() {
        String uniqueCode = Reader.readString(console, "Enter unique code: ");
        String name = Reader.readString(console, "Enter department name: ");
        String shortName = Reader.readStringWithMaxLength(console, "Enter short name",
                Department.MAX_SHORT_NAME_LENGTH);
        String cabinet = Reader.readString(console, "Enter cabinet/location: ");
        Teacher headOfDepartment = null;
        Long teacherId;
        while (true) {
            teacherId = Reader.readLong(console, "Enter teacher id(if vacant write -1): ");
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
            Long facultyId = Reader.readLong(console, "Enter faculty id(-1 to exit): ");
            if (facultyId.equals(Long.valueOf(-1))) return this;
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
            return this;

    }

    private Page editDepartment() {
        Long id =Reader.readLong(console, "Enter department id to edit: ");
        Optional<Department> optionalDepartment = serviceDepartment.findById(id);
        if (optionalDepartment.isEmpty()) {
            System.out.println("Department not found!!!");
            return this;
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
        return this;
    }

    private Page deleteDepartment() {
        Long id = Reader.readLong(console, "Enter department id to delete: ");
        Optional<Department> optionalDepartment = serviceDepartment.findById(id);
        if (optionalDepartment.isEmpty()) {
            System.out.println("Department not found!!!");
            return this;
        }
        serviceDepartment.delete(id);
        return this;
    }

    private Page showAllDepartments() {
        for (Department department : serviceDepartment.findAll())
            System.out.println("id -" + department.getId() + ", " + department);
        return this;
    }
}

