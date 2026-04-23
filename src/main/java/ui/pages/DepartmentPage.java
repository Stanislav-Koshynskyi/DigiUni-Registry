package ui.pages;

import entity.*;
import exception.EntityInUseException;
import service.ServiceDepartmentInterface;
import ui.*;
import ui.finders.DepartmentFinderInterface;
import ui.finders.FacultyFinderInterface;
import ui.finders.TeacherFinderInterface;
import util.Annotations;
import util.PagerBuilder;

import java.util.List;
import java.util.Optional;

public class DepartmentPage extends BasePage {
    private final ServiceDepartmentInterface serviceDepartment;
    private final TeacherFinderInterface teacherFinder;
    private final FacultyFinderInterface facultyFinder;
    private final DepartmentFinderInterface departmentFinder;
    private final PagerBuilder pagerBuilder;
    public DepartmentPage(ServiceDepartmentInterface serviceDepartment
            ,TeacherFinderInterface teacherFinder,
                          InputReader inputReader, FacultyFinderInterface facultyFinder,
                          DepartmentFinderInterface departmentFinder,PagerBuilder pagerBuilder) {
        super(inputReader);
        this.serviceDepartment = serviceDepartment;
        this.teacherFinder = teacherFinder;
        this.facultyFinder = facultyFinder;
        this.departmentFinder = departmentFinder;
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "Department";
    }

//    @Override
//    public List<MenuItem> getMenuItems() {
//        return List.of(
//                new MenuItem("Create department", Right.ADD, this::createDepartment),
//                new MenuItem("Edit department", Right.EDIT, this::editDepartment),
//                new MenuItem("Delete Department", Right.DELETE, this::deleteDepartment),
//                new MenuItem("Show all departments", Right.FIND, this::showAllDepartments),
//                new MenuItem("Find departments", Right.FIND, pagerBuilder::getDepartmentFindPage)
//        );
//    }

    @Annotations(name = "Find departments", right = Right.FIND, order = 5)
    private Page findDepartment() {
        return pagerBuilder.getDepartmentFindPage(inputReader);
    }

    @Annotations(name = "Create department", right = Right.ADD, order = 1)
    private Page createDepartment() {
        String name = inputReader.readString("Enter department name: ");
        String shortName = inputReader.readStringWithMaxLengthProbablyBlank("Enter short name",
                Department.MAX_SHORT_NAME_LENGTH);
        String cabinet = inputReader.readString("Enter cabinet/location: ");
        System.out.println("Select head of department (cancel if vacant)");
        Optional<Teacher> head = teacherFinder.findAndSelect();
        Teacher headOfDepartment = head.orElse(null);
        Optional<Faculty> optFaculty = facultyFinder.findAndSelect();
        if (optFaculty.isEmpty()) {
            System.out.println("Faculty not found");
            System.out.println("Department not created");
            return this;
        }
        Faculty faculty = optFaculty.get();
        University university = faculty.getUniversity();
        String uniqueCode = uniqueCode(university);
        try {
            Department department = new Department(uniqueCode, name, shortName, faculty, headOfDepartment, cabinet);
            serviceDepartment.create(department);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.out.println("Department not created");
        }
        return this;
    }
    @Annotations(name = "Edit department", right = Right.EDIT, order = 2)
    private Page editDepartment() {
        Optional<Department> optionalDepartment = departmentFinder.findAndSelect();
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
    @Annotations(name = "Delete department", right = Right.DELETE, order = 3)
    private Page deleteDepartment() {
        Optional<Department> optionalDepartment = departmentFinder.findAndSelect();
        if (optionalDepartment.isPresent()){
            try{
                Department department = optionalDepartment.get();
                serviceDepartment.delete(department.getId());
            }catch (IllegalArgumentException e){
                System.out.println("Deleting error");
            }catch (EntityInUseException e){
                System.out.println(e.getMessage());
            }
        }
        return this;
    }
    @Annotations(name = "Show all departments", right = Right.FIND, order = 4)
    private Page showAllDepartments() {
        List<Department> departments = serviceDepartment.findAll();
        return new SortDepartmentPage(inputReader, departments);
    }

    private String uniqueCode(University university) {
        while (true) {
            String uniqueCode = inputReader.readString("Enter unique code: ");
            if (!serviceDepartment.existsByUniqueCode(uniqueCode, university))
                return uniqueCode;
            System.out.println("Department with " + uniqueCode + " already exists");
        }
    }
}

