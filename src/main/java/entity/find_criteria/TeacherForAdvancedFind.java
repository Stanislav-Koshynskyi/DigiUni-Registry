package entity.find_criteria;

public enum TeacherForAdvancedFind {
    NAME("By name"),
    SURNAME("By surname"),
    Patronymic("By patronymic"),
    ACADEMIC_RANK("By academic rank"),
    ACADEMIC_DEGREE("By academic degree"),
    SALARY("By salary");
    private String name;
    private TeacherForAdvancedFind(String name)
    {
        this.name = name;
    }
}
