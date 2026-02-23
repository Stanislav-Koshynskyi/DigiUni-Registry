package entity.find_criteria;

public enum DepartmentForAdvancedFind {
    UNIQUE_CODE("By unique code"),
    NAME("By name"),
    SHORT_NAME("By short name"),
    FACULTY("By faculty"),
    UNIVERSITY("By university");
    private final String name;
    private DepartmentForAdvancedFind(final String name) {
        this.name = name;
    }
}
