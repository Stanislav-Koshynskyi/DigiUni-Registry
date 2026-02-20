package entity;

public enum Right {
    FIND(1),
    ADD (2),
    EDIT (4),
    DELETE (8),
    ANY(15);
    private final int value;
    Right(int value) {
        this.value = value;
    }
    public int getNeededRight() {
        return value;
    }
}
