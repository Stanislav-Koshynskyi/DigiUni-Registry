package entity.find_criteria;

import entity.University;

public enum UniversityFind {
    NAME ("Find by name"),
    SHORT_NAME("Find bu short name"),
    CITY("Find by city"),
    ALL("Show all"),
    CANCEL("Cancel");

    private final String name;
    private UniversityFind(String name) {
        this.name = name;
    }
}
