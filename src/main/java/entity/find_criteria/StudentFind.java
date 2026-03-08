package entity.find_criteria;

public enum StudentFind {
    ORG_STRUCTURE("By org structure(university/faculty/department/student group)"),
    IDS("By unique ids(record book/)"),
    STUDENT_STATUS("By student status"),
    FORM_OF_EDUCATION("By form of education"),
    COURSE("By course"),
    Contact("By contact(phone/email)"),
    NAME("By name"),
    SURNAME("By surname"),
    PATRONYMIC("By patronymic"),
    ADVANCED("Advanced search"),
    SHOW_ALL("Show all"),
    CANCEL("Cancel");



    private final String name;
    private StudentFind(String name){
        this.name = name;
    }

}
