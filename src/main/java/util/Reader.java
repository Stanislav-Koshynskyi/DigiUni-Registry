package util;

import entity.Contact;
import entity.Person;
import entity.Student;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class Reader {
     public static String readString(Console console, String prompt) {
        while (true) {
            String input = console.readLine(prompt);
            if (!input.isBlank()) return input;
            System.out.println("Input cannot be blank!");
        }
    }

    public static String readStringWithMaxLength(Console console, String prompt, int maxLength) {
        while (true) {
            String input = readString(console, prompt);
            if (input.length() <= maxLength) return input;
            System.out.println("Input must be shorter than or equal to " + maxLength + " characters.");
        }
    }

    public static int readInt(Console console, String prompt) {
        while (true) {
            try {
                return Integer.parseInt(console.readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer number.");
            }
        }
    }

    public static Long readLong(Console console, String prompt) {
        while (true) {
            try {
                return Long.parseLong(console.readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    public static BigDecimal readBigDecimal(Console console, String prompt) {
        while (true) {
            try {
                return new BigDecimal(console.readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary! Use numbers and a dot only.");
            }
        }
    }
    public static Contact readContact(Console console) {
        while (true) {
            try {
                String phone = console.readLine("Enter phone (10-13 numbers with optional +): ");
                String email = console.readLine("Enter email (format text@text.text): ");
                return new Contact(phone, email);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid email or phone format!");
            }
        }
    }

    public static LocalDate readBirthDate(Console console) {
        while (true) {
            try {
                LocalDate birthDate = LocalDate.parse(console.readLine("Enter birth date (yyyy-mm-dd): "));
                if (birthDate.getYear() >= Person.MINIUM_AGE) {
                    return birthDate;
                }
                System.out.println("Invalid birth date, person must be " + Person.MINIUM_AGE + "+ years old.");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, use yyyy-mm-dd.");
            }
        }
    }

    public static LocalDate readEmploymentDate(Console console) {
        while (true) {
            try {
                LocalDate date = LocalDate.parse(console.readLine("Enter date of employment (yyyy-mm-dd): "));
                if (!date.isAfter(LocalDate.now())) return date;
                System.out.println("Date of employment cannot be in the future.");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, use yyyy-mm-dd.");
            }
        }
    }

    public static Year readYearOfAdmission(Console console) {
        while (true) {
            try {
                int yearValue = readInt(console, "Enter year of admission: ");
                Year year = Year.of(yearValue);
                if (!year.isAfter(Year.now()) && !year.isBefore(Year.of(Student.MINIUM_YEAR_OF_ADMISSION))) {
                    return year;
                }
                System.out.println("Invalid year: cannot be in the future or too far in the past.");
            } catch (Exception e) {
                System.out.println("Use numbers only!");
            }
        }
    }

    public static int readCourse(Console console) {
        while (true) {
            int course = readInt(console, "Enter course (1-6): ");
            if (course >= 1 && course <= 6) return course;
            System.out.println("Course must be between 1 and 6.");
        }
    }
}