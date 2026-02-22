package ui.pages;

import entity.Right;
import ui.BasePage;
import ui.InputReader;
import ui.MenuItem;
import util.PagerBuilder;

import java.io.Console;
import java.util.List;

public class MainPage extends BasePage {
    private final PagerBuilder pagerBuilder;
    public MainPage(InputReader inputReader, PagerBuilder pagerBuilder) {
        super(inputReader);
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
                    return pagerBuilder.getStudentPage();
                }),
                new MenuItem("Teacher", Right.ANY, () ->{
                    return pagerBuilder.getTeacherPage();
                }),
                new MenuItem("University", Right.ANY, () ->{
                    return pagerBuilder.getUniversityPage();
                }),
                new MenuItem("Student group", Right.ANY, () ->{
                    return pagerBuilder.getStudentGroupPage();
                }),
                new MenuItem("Faculty", Right.ANY, () ->{
                    return pagerBuilder.getFacultyPage();
                }),
                new MenuItem("Department", Right.ANY, () -> {
                    return pagerBuilder.getDepartmentPage();
                }),
                new MenuItem("User", Right.ANY, () -> {
                    return pagerBuilder.getUserPage();
                })
        );
    }
}
