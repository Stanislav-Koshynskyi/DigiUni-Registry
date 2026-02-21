package ui.pages;

import entity.Right;
import ui.BasePage;
import ui.MenuItem;
import util.PagerBuilder;

import java.io.Console;
import java.util.List;

public class MainPage extends BasePage {
    private final PagerBuilder pagerBuilder;
    public MainPage(Console console, PagerBuilder pagerBuilder) {
        super(console);
        this.pagerBuilder = pagerBuilder;
    }

    @Override
    public String getTitle() {
        return "Work With";
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return List.of(
                new MenuItem("Student", Right.ANY, () ->{
                    return pagerBuilder.getStudentPage(console);
                }),
                new MenuItem("Teacher", Right.ANY, () ->{
                    return pagerBuilder.getTeacherPage(console);
                }),
                new MenuItem("University", Right.ANY, () ->{
                    return pagerBuilder.getUniversityPage(console);
                }),
                new MenuItem("Student group", Right.ANY, () ->{
                    return pagerBuilder.getStudentGroupPage(console);
                }),
                new MenuItem("Faculty", Right.ANY, () ->{
                    return pagerBuilder.getFacultyPage(console);
                }),
                new MenuItem("Department", Right.ANY, () -> {
                    return pagerBuilder.getDepartmentPage(console);
                }),
                new MenuItem("User", Right.ANY, () -> {
                    return pagerBuilder.getUserPage(console);
                })
        );
    }
}
