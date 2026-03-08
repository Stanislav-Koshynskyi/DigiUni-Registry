package entity.find_criteria;

public enum TeacherFind {
    NAME("By name"),
    SURNAME("By surname"),
    Patronymic("By patronymic"),
    CONTACT("By contact"),
    ACADEMIC_RANK("By academic rank"),
    ACADEMIC_DEGREE("By academic degree"),
    UNIQUE_CODE("By unique code"),
    SALARY("By salary"),
    SHOW_ALL("Show all"),
    ADVANCED("Advanced search"),
    CANCEL("Cancel");

    private String name;
    private TeacherFind(String name)
    {
        this.name = name;
    }
}
