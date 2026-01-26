package entity;

public enum AcademicRank {
    ASSOCIATE_PROFESSOR("Доцент"),
    SENIOR_RESEARCHER("Старший дослідник"),
    PROFESSOR("Професор"),
    NONE("Відсутнє");
    private final String nameUa;
    AcademicRank(final String nameUa) {
        this.nameUa = nameUa;
    }
    public String getNameUa() {
        return nameUa;
    }
    @Override
    public String toString() {
        return this.nameUa;
    }
}
