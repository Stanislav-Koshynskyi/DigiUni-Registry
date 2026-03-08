package ui.pages;

import entity.Department;
import entity.Faculty;
import entity.Right;
import entity.Teacher;
import service.ServiceDepartmentInterface;
import service.ServiceTeacherInterface;
import ui.*;
import ui.finders.FacultyFinderInterface;
import util.PagerBuilder;

import java.util.List;
import java.util.Optional;

public class DepartmentPage extends BasePage {
    private final ServiceDepartmentInterface serviceDepartment;
    private final ServiceTeacherInterface serviceTeacher;
    private final FacultyFinderInterface facultyFinder;
    private final PagerBuilder pagerBuilder;
    public DepartmentPage(ServiceDepartmentInterface serviceDepartment
            , ServiceTeacherInterface serviceTeacher,
                          InputReader inputReader, FacultyFinderInterface facultyFinder,
                          PagerBuilder pagerBuilder) {
        super(inputReader);
        this.serviceDepartment = serviceDepartment;
        this.serviceTeacher = serviceTeacher;
        this.facultyFinder = facultyFinder;
        this.pagerBuilder = pagerBuilder;
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
                new MenuItem("Show all departments", Right.FIND, this::showAllDepartments),
                new MenuItem("Find departments", Right.FIND, pagerBuilder::getDepartmentFindmentPage)
        );
    }

    private Page createDepartment() {

        String uniqueCode = inputReader.readString("Enter unique code: ");
        String name = inputReader.readString("Enter department name: ");
        String shortName = inputReader.readStringWithMaxLengthProbablyBlank("Enter short name",
                Department.MAX_SHORT_NAME_LENGTH);
        String cabinet = inputReader.readString("Enter cabinet/location: ");
        Teacher headOfDepartment = null;
        Long teacherId;
        while (true) {
            teacherId = inputReader.readLong("Enter teacher id(if vacant write -1): ");
            if (teacherId.equals(Long.valueOf(-1))) break;
            Optional<Teacher> optionalTeacher = serviceTeacher.findById(teacherId);
            if (optionalTeacher.isPresent()) {
                headOfDepartment = optionalTeacher.get();
                break;
            } else {
                System.out.println("Teacher not found!!!");
            }

        }
        Optional<Faculty> optFaculty = facultyFinder.findAndSelect();
        if (optFaculty.isEmpty()) {
            System.out.println("Faculty not found");
            System.out.println("Department not created");
            return this;
        }
        Faculty faculty = optFaculty.get();

        Department department = new Department(uniqueCode, name, shortName, faculty, headOfDepartment, cabinet);
        serviceDepartment.create(department);
        return this;
    }

    private Page editDepartment() {
        Long id = inputReader.readLong("Enter department id to edit: ");
        Optional<Department> optionalDepartment = serviceDepartment.findById(id);
        if (optionalDepartment.isEmpty()) {
            System.out.println("Department not found!!!");
            return this;
        }

        Department department = optionalDepartment.get();
        String name = inputReader.readProbablyBlank("Enter department name: ");
        String shortName = inputReader.readStringWithMaxLengthProbablyBlank(
                "Enter new short name", Department.MAX_SHORT_NAME_LENGTH);

        String cabinet = inputReader.readProbablyBlank("Enter cabinet/location: ");

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
        Long id = inputReader.readLong("Enter department id to delete: ");
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

