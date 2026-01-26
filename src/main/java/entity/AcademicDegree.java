package entity;

public enum AcademicDegree {
    CANDIDATE_OF_SCIENCES("Кандидат наук"),
    DOCTOR_OF_PHILOSOPHY("Доктор філософії"),
    DOCTOR_OF_SCIENCES("Доктор наук"),
    NONE("Відсутнє");
    private final String nameUa;
    AcademicDegree(String nameUa) {
        this.nameUa = nameUa;
    }
    public String getNameUa() {
        return nameUa;
    }
    @Override
    public String toString() {
        return nameUa;
    }
}
