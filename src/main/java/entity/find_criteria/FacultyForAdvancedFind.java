package entity.find_criteria;

public enum FacultyForAdvancedFind {
    UNIQUE_CODE("By unique code"),
    NAME("By name"),
    SHORT_NAME("By short name"),
    UNIVERSITY("By university");
    private String name;
    private FacultyForAdvancedFind(String name) {
        this.name = name;
    }
}
