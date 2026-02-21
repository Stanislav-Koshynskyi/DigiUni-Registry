package entity;

public enum Right {
    NONE(0),
    FIND(1),
    ADD (2),
    EDIT (4),
    DELETE (8),
    LOGGED_IN(32+15),
    GUEST_ONLY(16),
    ADMIN_ONLY(32),
    ANY(63);
    private final int value;
    Right(int value) {
        this.value = value;
    }
    public int getNeededRight() {
        return value;
    }
}
