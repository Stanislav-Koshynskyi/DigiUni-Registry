package entity.find_criteria;

public enum StudentForAdvancedFind {
    ORG_STRUCTURE("By org structure(university/faculty/department/student group)"),
    IDS("By unique ids(record book/)"),
    STUDENT_STATUS("By student status"),
    FORM_OF_EDUCATION("By form of education"),
    COURSE("By course"),
    NAME("By name"),
    SURNAME("By surname"),
    PATRONYMIC("By patronymic");
    private final String name;
    private StudentForAdvancedFind(String name){
        this.name = name;
    }
}
