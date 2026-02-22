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
                new MenuItem("Student", Right.ANY, pagerBuilder::getStudentPage),
                new MenuItem("Teacher", Right.ANY, pagerBuilder::getTeacherPage),
                new MenuItem("University", Right.ANY, pagerBuilder::getUniversityPage),
                new MenuItem("Student group", Right.ANY, pagerBuilder::getStudentGroupPage),
                new MenuItem("Faculty", Right.ANY, pagerBuilder::getFacultyPage),
                new MenuItem("Department", Right.ANY, pagerBuilder::getDepartmentPage),
                new MenuItem("User", Right.ANY, pagerBuilder::getUserPage)
        );
    }
}
