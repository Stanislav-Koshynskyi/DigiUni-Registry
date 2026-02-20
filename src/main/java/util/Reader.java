package util;

import entity.*;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class Reader {
     public static String readString(Console console, String prompt) {
        while (true) {
            String input = console.readLine(prompt);
            if (input != null && !input.isBlank()) return input;
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
    public static FormOfEducation readFormOfEducation(Console console) {

         FormOfEducation form = null;
         while (form == null) {
            System.out.println("Form of education: 1 - Budget, 2 - Contract");
            int userChoiceFormOfEducation = Reader.readInt(console, "Choose a form of education: ");

            switch (userChoiceFormOfEducation) {
                case 1:
                    form = FormOfEducation.BUDGET;
                    break;
                case 2:
                    form = FormOfEducation.CONTRACT;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
         return form;
    }
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
    public static AcademicDegree readAcademicDegree(Console console) {
        AcademicDegree degree = null;
        while (degree == null) {

            System.out.println("Academic degree: 1 - Candidate of sciences, 2 - Doctor of philosophy, 3 - Doctor of sciences, 4 - None");
            int degreeChoice = readInt(console,"Choose academic degree: ");

            switch (degreeChoice) {
                case 1:
                    degree = AcademicDegree.CANDIDATE_OF_SCIENCES;
                    break;
                case 2:
                    degree = AcademicDegree.DOCTOR_OF_PHILOSOPHY;
                    break;
                case 3:
                    degree = AcademicDegree.DOCTOR_OF_SCIENCES;
                    break;
                case 4:
                    degree = AcademicDegree.NONE;
                    break;
                default:
                    System.out.println("Invalid academic degree!");
            }
        }
        return degree;
    }
    public static AcademicRank readAcademicRank(Console console) {
        AcademicRank rank = null;
        while (rank == null) {
            System.out.println("Academic rank: 1 - Associate professor, 2 - Senior researcher, 3 - Professor, 4 - None");
            int rankChoice = readInt(console, "Choose academic rank: ");

            switch (rankChoice) {
                case 1:
                    rank = AcademicRank.ASSOCIATE_PROFESSOR;
                    break;
                case 2:
                    rank = AcademicRank.SENIOR_RESEARCHER;
                    break;
                case 3:
                    rank = AcademicRank.PROFESSOR;
                    break;
                case 4:
                    rank = AcademicRank.NONE;
                    break;
                default:
                    System.out.println("Invalid academic rank!");
            }
        }
        return rank;
    }
}