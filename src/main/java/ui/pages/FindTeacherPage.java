package ui.pages;

import entity.Right;
import entity.Teacher;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;
import ui.finders.TeacherFinderInterface;

import java.util.List;
import java.util.Optional;

public class FindTeacherPage extends BasePage {
    private final TeacherFinderInterface teacherFinder;
    public FindTeacherPage(InputReader inputReader, TeacherFinderInterface teacherFinder) {
        super(inputReader);
        this.teacherFinder = teacherFinder;
    }

    @Override
    public String getTitle() {
        return "Find teacher";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("By name", Right.FIND, this::findByName),
                new MenuItem("By surname", Right.FIND, this::findBySurname),
                new MenuItem("By patronymic", Right.FIND, this::findByPatronymic),
                new MenuItem("By contact", Right.FIND, this::findByContact),
                new MenuItem("By academic rank", Right.FIND, this::findByAcademicRank),
                new MenuItem("By academic degree", Right.FIND, this::findByAcademicDegree),
                new MenuItem("By unique code", Right.FIND, this::findByUniqueCode),
                new MenuItem("By salary", Right.FIND, this::findBySalary),
                new MenuItem("Advanced search", Right.FIND, this::advancedSearch)
        );
    }
    private void printTeachers(List<Teacher> teachers) {
        if (teachers.isEmpty()) {
            inputReader.println("No teachers found");
        }
        else{
            for (Teacher teacher : teachers) {
                inputReader.println(teacher);
            }
        }
    }
    private Page findByName(){
        printTeachers(teacherFinder.findByName());
        return this;
    }
    private Page findBySurname(){
        printTeachers(teacherFinder.findBySurname());
        return this;
    }
    private Page findByPatronymic(){
        printTeachers(teacherFinder.findByPatronymic());
        return this;
    }
    private Page findByContact(){
        Optional<Teacher>  optionalTeacher = teacherFinder.findByContact();
        if(optionalTeacher.isPresent()){
            printTeachers(List.of(optionalTeacher.get()));
        }
        else{
            printTeachers(List.of());
        }
        return this;
    }
    private Page findByAcademicRank(){
        printTeachers(teacherFinder.findByAcademicRank());
        return this;
    }
    private Page findByAcademicDegree(){
        printTeachers(teacherFinder.findByAcademicDegree());
        return this;
    }
    private Page findByUniqueCode(){
        Optional<Teacher>  optionalTeacher = teacherFinder.findByUniqueCode();
        if (optionalTeacher.isPresent()){
            printTeachers(List.of(optionalTeacher.get()));
        }
        else{
            printTeachers(List.of());
        }
        return this;
    }
    private Page findBySalary(){
        printTeachers(teacherFinder.findBySalary());
        return this;
    }
    private Page advancedSearch(){
        printTeachers(teacherFinder.advancedSearch());
        return this;
    }
}
/*
NAME("By name"),
    SURNAME("By surname"),
    Patronymic("By patronymic"),
    CONTACT("By contact"),
    ACADEMIC_RANK("By academic rank"),
    ACADEMIC_DEGREE("By academic degree"),
    UNIQUE_CODE("By unique code"),
    SALARY("By salary"),
 */