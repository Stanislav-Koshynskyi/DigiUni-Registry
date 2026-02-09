package ui;

import entity.Contact;
import entity.FullName;
import java.io.Console;
import java.time.LocalDate;

public class PersonMenu {
    public static FullName fullName(Console console) {
        String name = console.readLine("Enter name: ");
        String surname = console.readLine("Enter surname: ");
        String patronymic = console.readLine("Enter patronymic: ");
        return new FullName(name, surname, patronymic);
    }

    public static LocalDate birthDate(Console console) {
        String birth = console.readLine("Enter birth date: ");
        return LocalDate.parse(birth);
    }

    public static Contact contact(Console console) {
        String phone = console.readLine("Enter phone: ");
        String email = console.readLine("Enter email: ");
        return new Contact(phone, email);
    }
}
