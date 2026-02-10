package ui;

import entity.Contact;
import entity.FullName;
import entity.Person;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PersonMenu {
    public static FullName fullName(Console console) {
        String name = console.readLine("Enter name: ");
        while (name.isBlank()) {
            System.out.println("name cannot be blank");
            name = console.readLine("Enter name: ");
        }
        String surname = console.readLine("Enter surname: ");
        while (surname.isBlank()) {
            System.out.println("surname cannot be blank");
            surname = console.readLine("Enter surname: ");
        }
        String patronymic = console.readLine("Enter patronymic: ");
        return new FullName(name, surname, patronymic);
    }

    public static LocalDate birthDate(Console console) {
        while (true) {
            try {

                String birth = console.readLine("Enter birth date(yyyy-mm-dd): ");
                LocalDate birthDate = LocalDate.parse(birth);
                if (birthDate.getYear() >= Person.MINIUM_AGE)
                    return birthDate;
                else{
                    System.out.println("Invalid birth date, person must be "+Person.MINIUM_AGE+"+ years old");
                }
            }catch ( DateTimeParseException e) {
                System.out.println("Invalid date format, use  yyyy-mm-dd");
            }
        }
    }

    public static Contact contact(Console console) {
        while (true) {
            try {
                String phone = console.readLine("Enter phone(10-13 numbers with optional +): ");
                String email = console.readLine("Enter email(in format text.text@text): ");
                return new Contact(phone, email);
            }catch ( IllegalArgumentException e) {
                System.out.println("Invalid email or phone format, use text.text@text for email and 10-13 numbers for phone");
            }
        }
    }
}
