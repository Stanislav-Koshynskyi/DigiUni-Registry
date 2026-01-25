package entity.enums;

import entity.Faculty;

public record FullName(String name, String surname, String Patronymic) {
    public FullName{
        if (name == null || name.isBlank()) throw new NullPointerException("name cannot be empty");
        if (surname == null || surname.isBlank()) throw new NullPointerException("surname cannot be empty");
    }
}
