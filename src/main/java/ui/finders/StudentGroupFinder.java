package ui.finders;

import entity.Department;
import entity.Faculty;
import entity.StudentGroup;
import entity.University;
import entity.find_criteria.StudentGroupFind;
import service.ServiceStudentGroupInterface;
import ui.InputReader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentGroupFinder implements  StudentGroupFinderInterface {
    private final InputReader inputReader;
    private final UniversityFinderInterface universityFinder;
    private final FacultyFinderInterface facultyFinder;
    private final DepartmentFinderInterface departmentFinder;
    private final ServiceStudentGroupInterface serviceStudentGroup;
    public  StudentGroupFinder(InputReader inputReader, UniversityFinderInterface universityFinder, FacultyFinderInterface facultyFinder,
                               DepartmentFinderInterface departmentFinder, ServiceStudentGroupInterface serviceStudentGroup) {
        this.inputReader = inputReader;
        this.universityFinder = universityFinder;
        this.facultyFinder = facultyFinder;
        this.departmentFinder = departmentFinder;
        this.serviceStudentGroup = serviceStudentGroup;
    }
    public Optional<StudentGroup> chooseStudentGroup(List<StudentGroup> studentGroups) {
        if (studentGroups == null || studentGroups.isEmpty()) return Optional.empty();
        else{
            return Optional.ofNullable(inputReader.readChoose(studentGroups, "Choose student group"));
        }
    }
    public List<StudentGroup> findByName(){
        String name = inputReader.readString("Enter name");
        return serviceStudentGroup.findByName(name);
    }
    public List<StudentGroup> findByUniversity(){
        Optional<University> optionalUniversity = universityFinder.findAndSelect();
        if (optionalUniversity.isEmpty()){
            return Collections.emptyList();
        }
        else{
            return serviceStudentGroup.findByUniversity(optionalUniversity.get());
        }
    }
    public List<StudentGroup> findByFaculty(){
        Optional<Faculty> optionalFaculty = facultyFinder.findAndSelect();
        if (optionalFaculty.isEmpty()){
            return Collections.emptyList();
        }
        else{
            return serviceStudentGroup.findByFaculty(optionalFaculty.get());
        }
    }
    public List<StudentGroup> findByDepartment(){
        Optional<Department> optionalDepartment = departmentFinder.findAndSelect();
        if (optionalDepartment.isEmpty()){
            return Collections.emptyList();
        }
        else{
            return serviceStudentGroup.findByDepartment(optionalDepartment.get());
        }
    }
    public Optional<StudentGroup> findAndSelect(){
        System.out.println("Select student group");
        Optional<StudentGroup> optionalStudentGroup = Optional.empty();
        while (true) {
            StudentGroupFind find = inputReader.readChoose(
                    Arrays.stream(StudentGroupFind.values()).toList(), "Choose criteria"
            );
            switch (find) {
                case UNIVERSITY -> {
                    optionalStudentGroup = chooseStudentGroup(findByUniversity());
                }
                case SHOW_ALL ->  {
                    optionalStudentGroup = chooseStudentGroup(serviceStudentGroup.findAll());
                }
                case NAME ->  {
                    optionalStudentGroup = chooseStudentGroup(findByName());
                }
                case FACULTY ->  {
                    optionalStudentGroup = chooseStudentGroup(findByFaculty());
                }
                case DEPARTMENT ->  {
                    optionalStudentGroup = chooseStudentGroup(findByDepartment());
                }
                case CANCEL -> {
                    return Optional.empty();
                }
                default -> {
                    optionalStudentGroup = Optional.empty();
                }
            }
            if (optionalStudentGroup.isPresent()) return optionalStudentGroup;
            else{
                System.out.println("No student group found, try again");
            }
        }
    }
}
