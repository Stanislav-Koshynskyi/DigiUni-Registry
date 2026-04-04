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
                new MenuItem("Student", Right.LOGGED_IN, pagerBuilder::getStudentPage),
                new MenuItem("Teacher", Right.LOGGED_IN, pagerBuilder::getTeacherPage),
                new MenuItem("University", Right.LOGGED_IN, pagerBuilder::getUniversityPage),
                new MenuItem("Student group", Right.LOGGED_IN, pagerBuilder::getStudentGroupPage),
                new MenuItem("Faculty", Right.LOGGED_IN, pagerBuilder::getFacultyPage),
                new MenuItem("Department", Right.LOGGED_IN, pagerBuilder::getDepartmentPage),
                new MenuItem("Account", Right.ANY, pagerBuilder::getUserPage)
        );
    }
}
