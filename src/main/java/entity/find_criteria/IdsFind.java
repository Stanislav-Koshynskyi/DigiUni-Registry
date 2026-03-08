package entity.find_criteria;

public enum IdsFind {
    RECORD_BOOK("By record book"),
    UNIQUE_CODE("By unique code"),
    CANCEL("Cancel");
    private String name;
    private IdsFind(String name) {
        this.name = name;
    }
}
