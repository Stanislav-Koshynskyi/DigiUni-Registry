package ui.pages;

import entity.Right;
import entity.Teacher;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import ui.Page;

import java.util.Comparator;
import java.util.List;

public class SortTeacherPage extends BasePage {
    private List<Teacher> teachers;
    public SortTeacherPage(InputReader inputReader, List<Teacher> teachers) {
        super(inputReader);
        this.teachers = teachers;
    }

    public String getTitle() {return "Sort Teachers";}
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Sort teachers by name", Right.FIND, this::teacherSortByName),
                new MenuItem("Sort teachers by surname", Right.FIND, this::teacherSortBySurname),
                new MenuItem("Sort teachers by salary", Right.FIND, this::teacherSortBySalary),
                new MenuItem("Sort teachers by employment date", Right.FIND, this::teacherSortByDate)
        );
    }

    private Page teacherSortByDate() {
        teachers = teachers.stream().sorted(Comparator.comparing(Teacher::getDateOfEmployment)).toList();
        return showTeachers();
    }

    private Page teacherSortBySalary() {
        teachers = teachers.stream().sorted(Comparator.comparing(Teacher::getSalary)).toList();
        return showTeachers();
    }

    private Page teacherSortBySurname() {
        teachers = teachers.stream().sorted(Comparator.comparing(t -> t.getFullName().surname())).toList();
        return showTeachers();
    }

    private Page teacherSortByName() {
        teachers = teachers.stream().sorted(Comparator.comparing(t -> t.getFullName().name())).toList();
        return showTeachers();
    }

    private Page showTeachers() {
        for (Teacher teacher : teachers)
            System.out.println("id -" + teacher.getId() + ", " + teacher);
        return this;
    }

}
