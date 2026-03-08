package entity.find_criteria;

public enum ContactFind {
    PHONE("By phone"),
    EMAIL("By email"),
    CANCEL("Cancel");
    private final String name;
    private ContactFind(String name){
        this.name = name;
    }
}
