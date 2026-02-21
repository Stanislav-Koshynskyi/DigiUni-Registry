package entity;

public enum Right {
    NONE(0),
    FIND(1),
    ADD (2),
    EDIT (4),
    DELETE (8),
    ANY(15),
    GUEST_ONLY(16);
    private final int value;
    Right(int value) {
        this.value = value;
    }
    public int getNeededRight() {
        return value;
    }
}
