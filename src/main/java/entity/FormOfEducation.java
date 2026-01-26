package entity;

public enum FormOfEducation {
    BUDGET("бюджет"),
    CONTRACT("контракт");

    private final String nameUa;

    FormOfEducation(String nameUa) {
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
