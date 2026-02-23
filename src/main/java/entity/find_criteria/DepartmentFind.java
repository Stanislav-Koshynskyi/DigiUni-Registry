package entity.find_criteria;

import entity.Faculty;
import entity.Teacher;

import javax.swing.*;

public enum DepartmentFind {
    UNIQUE_CODE("By unique code"),
    NAME("By name"),
    SHORT_NAME("By short name"),
    FACULTY("By faculty"),
    UNIVERSITY("By university"),
    SHOW_ALL("Show all"),
    CANCEL("Cancel");


    private final String name;
    private DepartmentFind(String name) {
        this.name = name;
    }
}
/*
private Long id;
private String uniqueCode;
private String name;
private String shortName;
private Faculty faculty;
private Long facultyId;
private Teacher headOfDepartment;
private Long headOfDepartmentId;
private String cabinet;

 */