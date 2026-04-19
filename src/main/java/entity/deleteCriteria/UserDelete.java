package entity.deleteCriteria;

public enum UserDelete {
    HARD("Hard delete"),
    SOFT("Soft delete");
    private String value;
    UserDelete(String value) {
        this.value = value;
    }
}
