package entity.find_criteria;

public enum FacultyFind {
    UNIQUE_CODE("By unique code"),
    NAME("By name"),
    SHORT_NAME("By short name"),
    EMAIL("By email"),
    PHONE("By phone"),
    UNIVERSITY("By university"),
    ADVANCED("Advanced"),
    SHOW_ALL("Show all"),
    CANCEL("Cancel");
    private String name;
    private FacultyFind(String name) {
        this.name = name;
    }

}
