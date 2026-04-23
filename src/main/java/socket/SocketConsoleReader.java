package socket;

import entity.*;
import ui.InputReader;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SocketConsoleReader implements InputReader {
    private final BufferedReader in;
    private final PrintWriter out;
    public SocketConsoleReader(Socket socket)throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    public void println(String message) {out.println(message);}
    public String readString(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (input != null && !input.isBlank()) return input;
            out.println("Input cannot be blank!");
        }
    }
    private String readLine(String prompt) {
        try {
            out.println(prompt);
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readPassword( String prompt) {
//        while (true) {
//            char[] password = readPassword(prompt);
//            String input =  new String(password);
//            if (input != null && !input.isBlank()) {
//                return input;
//            }
//            else  {
//                out.println("Password cannot be blank!");
//            }
//        }
        return readLine(prompt);
    }

    @Override
    public String readHide(String prompt) {
        readLine(prompt);
        return "";
    }
    public String readStringWithMaxLengthProbablyBlank(String prompt, int maxLength) {
        while (true) {
            String input = readString(prompt);
            if (input.length() <= maxLength) return input;
            out.println("Input must be shorter than or equal to " + maxLength + " characters.");
        }
    }
    public String readStringWithMaxLength(String prompt, int maxLength) {
        while (true) {
            String input = readString(prompt);
            if (input.isBlank()) out.println("Input cannot be blank!");
            else if (input.length() >  maxLength) out.println("Input must be less than or equal to " + maxLength + " characters.");
            else return input;
        }
    }


    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                out.println("Invalid input! Please enter a valid integer number.");
            }
        }
    }
    public int readIntInRange(String prompt, int min, int max) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(readLine(prompt));
                if (input >= min && input <=max)return input;
                else out.println("Invalid input! Input must be between " + min + " and " + max);
            }catch (NumberFormatException e) {
                out.println("Invalid input! Please enter a valid integer number.");
            }

        }
    }
    public Integer readIntInRangeProbablyNull(String prompt,Integer min, Integer max) {
        String string;
        while (true) {
            try {
                string = readLine(prompt);
                if (string.isBlank()) return null;
                int number =  Integer.parseInt(string);
                if (number >= min && number <= max) return number;
                else out.println("Number must be between "+min+" and "+max);
            }catch (NumberFormatException e) {
                out.println("Invalid input! Please enter a valid integer number.");
            }
        }
    }

    public Long readLong(String prompt) {
        while (true) {
            try {
                return Long.parseLong(readLine(prompt));
            } catch (NumberFormatException e) {
                out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                return new BigDecimal(readLine(prompt));
            } catch (NumberFormatException e) {
                out.println("Invalid salary! Use numbers and a dot only.");
            }
        }
    }
    public Contact readContact() {
        while (true) {
            try {
                String phone = readLine("Enter phone (10-13 numbers with optional +): ");
                String email = readLine("Enter email (format text@text.text): ");
                return new Contact(phone, email);
            } catch (IllegalArgumentException e) {
                out.println("Invalid email or phone format!");
            }
        }

    }
    public Contact readContactProbablyNull() {
        while (true) {
            try {
                String phone = readLine("Enter phone (10-13 numbers with optional +): ");
                String email = readLine("Enter email (format text@text.text): ");
                if (phone.isBlank() && email.isBlank()) return null;
                return new Contact(phone, email);
            } catch (IllegalArgumentException e) {
                out.println("Invalid email or phone format!");
            }
        }
    }

    public LocalDate readBirthDate() {
        while (true) {
            try {
                LocalDate birthDate = LocalDate.parse(readLine("Enter birth date (yyyy-mm-dd): "));
                if (birthDate.getYear() >= Person.MINIUM_AGE) {
                    return birthDate;
                }
                out.println("Invalid birth date, person must be " + Person.MINIUM_AGE + "+ years old.");
            } catch (DateTimeParseException e) {
                out.println("Invalid date format, use yyyy-mm-dd.");
            }
        }
    }

    public LocalDate readEmploymentDate() {
        while (true) {
            try {
                LocalDate date = LocalDate.parse(readLine("Enter date of employment (yyyy-mm-dd): "));
                if (!date.isAfter(LocalDate.now())) return date;
                out.println("Date of employment cannot be in the future.");
            } catch (DateTimeParseException e) {
                out.println("Invalid date format, use yyyy-mm-dd.");
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
                out.println("Invalid year: cannot be in the future or too far in the past.");
            } catch (Exception e) {
                out.println("Use numbers only!");
            }
        }
    }

    public int readCourse() {
        while (true) {
            int course = readInt("Enter course (1-6): ");
            if (course >= 1 && course <= 6) return course;
            out.println("Course must be between 1 and 6.");
        }
    }
    public FormOfEducation readFormOfEducation() {
        FormOfEducation form = null;
        while (form == null) {
            out.println("Form of education: 1 - Budget, 2 - Contract");
            int userChoiceFormOfEducation = readInt("Choose a form of education: ");

            switch (userChoiceFormOfEducation) {
                case 1:
                    form = FormOfEducation.BUDGET;
                    break;
                case 2:
                    form = FormOfEducation.CONTRACT;
                    break;
                default:
                    out.println("Invalid input!");
            }
        }
        return form;
    }
    public FullName readfullName() {
        String name = readLine("Enter name: ");
        while (name.isBlank()) {
            out.println("name cannot be blank");
            name = readLine("Enter name: ");
        }
        String surname = readLine("Enter surname: ");
        while (surname.isBlank()) {
            out.println("surname cannot be blank");
            surname = readLine("Enter surname: ");
        }
        String patronymic = readLine("Enter patronymic: ");
        return new FullName(name, surname, patronymic);
    }
    public AcademicDegree readAcademicDegree() {
        AcademicDegree degree = null;
        while (degree == null) {

            out.println("Academic degree: 1 - Candidate of sciences, 2 - Doctor of philosophy, 3 - Doctor of sciences, 4 - None");
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
                    out.println("Invalid academic degree!");
            }
        }
        return degree;
    }
    public AcademicRank readAcademicRank() {
        AcademicRank rank = null;
        while (rank == null) {
            out.println("Academic rank: 1 - Associate professor, 2 - Senior researcher, 3 - Professor, 4 - None");
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
                    out.println("Invalid academic rank!");
            }
        }
        return rank;
    }
    public <T> T readChoose(List<T> list, String promt){
        if (list == null || list.isEmpty()) return null;
        int i = 1;
        for (T t : list) {
            out.println(i++ + " - " + t);
        }
        int choose = readIntInRange(promt, 1, list.size());
        return list.get(choose - 1);
    }
    public <T> T readChooseProbablyNull(List<T> list, String promt, String promptForZero) {
        if (list == null || list.isEmpty()) return null;
        int i = 1;
        out.println("0" + promptForZero);
        for (T t : list) {
            out.println(i++ + " - " + t);
        }
        int choose = readIntInRange(promt, 0, list.size());
        if (choose == 0) return null;
        return list.get(choose-1);
    }
    @Override
    public String readProbablyBlank(String prompt) {
        return readLine(prompt);
    }
}