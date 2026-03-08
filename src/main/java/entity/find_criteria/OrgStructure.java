package entity.find_criteria;

public enum OrgStructure {
    UNIVERSITY("By university"),
    FACULTY("By faculty"),
    DEPARTMENT("By department"),
    STUDENT_GROUP("By student group"),
    CANCEL("Cancel");
    private final String name;
    private OrgStructure(String name) {
        this.name = name;
    }
}
