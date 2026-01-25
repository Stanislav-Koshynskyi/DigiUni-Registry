package entity;

public record FullName(String name, String surname, String patronymic) {
    public FullName {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name cannot be empty");
        if (surname == null || surname.isBlank()) throw new IllegalArgumentException("surname cannot be empty");
        if (patronymic == null || patronymic.isBlank()) {
            patronymic = "";
        }
    }
}
