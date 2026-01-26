package entity;

public enum StudentStatus {
    STUDIES("навчається"),
    EXPELLED("відрахований"),
    ACADEMIC_LEAVE("академічна відпустка");

    private final String nameUa;
    StudentStatus(String nameUa) {
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
