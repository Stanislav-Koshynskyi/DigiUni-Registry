package entity.find_criteria;

public enum UserFind {
    ROLE("By role"),
    NAME("By name");
    private String name;
    UserFind(String name) {
        this.name = name;
    }
}
