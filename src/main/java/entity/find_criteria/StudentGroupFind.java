package entity.find_criteria;

public enum StudentGroupFind {
    NAME("By name"),
    UNIVERSITY("By university"),
    DEPARTMENT("By department"),
    FACULTY("By faculty"),
    SHOW_ALL("Show all"),
    CANCEL("Cancel");

    private String name;
    private StudentGroupFind(String name) {
        this.name = name;
    }
}