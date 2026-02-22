package ui;

import entity.*;

import java.io.Console;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ConsoleReader implements InputReader {
    private final Console console;
    public ConsoleReader(Console console){
        this.console = console;
    }
     public String readString(String prompt) {
        while (true) {
            String input = console.readLine(prompt);
            if (input != null && !input.isBlank()) return input;
            System.out.println("Input cannot be blank!");
        }
    }
    public String readPassword( String prompt) {
         while (true) {
             char[] password = console.readPassword(prompt);
             String input =  new String(password);
             if (input != null && !input.isBlank()) {
                 return input;
             }
             else  {
                 System.out.println("Password cannot be blank!");
             }
         }
    }

    @Override
    public String readHide(String prompt) {
        return console.readPassword(prompt).toString();
    }

    public String readStringWithMaxLengthProbablyBlank(String prompt, int maxLength) {
        while (true) {
            String input = readString(prompt);
            if (input.length() <= maxLength) return input;
            System.out.println("Input must be shorter than or equal to " + maxLength + " characters.");
        }
    }
    public String readStringWithMaxLength(String prompt, int maxLength) {
        while (true) {
            String input = readString(prompt);
            if (input.isBlank()) System.out.println("Input cannot be blank!");
            else if (input.length() >  maxLength) System.out.println("Input must be less than or equal to " + maxLength + " characters.");
            else return input;
        }
    }


    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(console.readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer number.");
            }
        }
    }
    public int readIntInRange(String prompt, int min, int max) {
        int input;
         while (true) {
            try {
                input = Integer.parseInt(console.readLine(prompt));
                if (input >= min && input <=max)return input;
                else System.out.println("Invalid input! Input must be between " + min + " and " + max);
            }catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer number.");
            }

        }
    }
    public Integer readIntInRangeProbablyNull(String prompt,Integer min, Integer max) {
        String string;
        while (true) {
            try {
                string = console.readLine(prompt);
                if (string.isBlank()) return null;
                int number =  Integer.parseInt(string);
                if (number >= min && number <= max) return number;
                else System.out.println("Number must be between "+min+" and "+max);
            }catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer number.");
            }
        }
    }

    public Long readLong(String prompt) {
        while (true) {
            try {
                return Long.parseLong(console.readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                return new BigDecimal(console.readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary! Use numbers and a dot only.");
            }
        }
    }
    public Contact readContact() {
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
    public Contact readContactProbablyNull() {
        while (true) {
            try {
                String phone = console.readLine("Enter phone (10-13 numbers with optional +): ");
                String email = console.readLine("Enter email (format text@text.text): ");
                if (phone.isBlank() && email.isBlank()) return null;
                return new Contact(phone, email);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid email or phone format!");
            }
        }
    }

    public LocalDate readBirthDate() {
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

    public LocalDate readEmploymentDate() {
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

    public Year readYearOfAdmission() {
        while (true) {
            try {
                int yearValue = readInt( "Enter year of admission: ");
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

    public int readCourse() {
        while (true) {
            int course = readInt("Enter course (1-6): ");
            if (course >= 1 && course <= 6) return course;
            System.out.println("Course must be between 1 and 6.");
        }
    }
    public FormOfEducation readFormOfEducation() {

         FormOfEducation form = null;
         while (form == null) {
            System.out.println("Form of education: 1 - Budget, 2 - Contract");
            int userChoiceFormOfEducation = readInt("Choose a form of education: ");

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
    public FullName readfullName() {
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
    public AcademicDegree readAcademicDegree() {
        AcademicDegree degree = null;
        while (degree == null) {

            System.out.println("Academic degree: 1 - Candidate of sciences, 2 - Doctor of philosophy, 3 - Doctor of sciences, 4 - None");
            int degreeChoice = readInt("Choose academic degree: ");

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
    public AcademicRank readAcademicRank() {
        AcademicRank rank = null;
        while (rank == null) {
            System.out.println("Academic rank: 1 - Associate professor, 2 - Senior researcher, 3 - Professor, 4 - None");
            int rankChoice = readInt("Choose academic rank: ");

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
    public <T> T readChoose(List<T> list, String promt){
         if (list == null || list.isEmpty()) return null;
         int i = 1;
         for (T t : list) {
             System.out.println(i++ + " - " + t);
         }
         int choose = readIntInRange(promt, 1, list.size());
         return list.get(choose - 1);
    }
    public <T> T readChooseProbablyNull(List<T> list, String promt, String promptForZero) {
        if (list == null || list.isEmpty()) return null;
         int i = 1;
         System.out.println("0" + promptForZero);
         for (T t : list) {
             System.out.println(i++ + " - " + t);
         }
         int choose = readIntInRange(promt, 0, list.size());
         if (choose == 0) return null;
         return list.get(choose);
    }
    @Override
    public String readProbablyBlank(String prompt) {
        return console.readLine(prompt);
    }
}