package ui;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.math.BigDecimal;

import entity.*;
import service.ServiceTeacherInterface;

public class TeacherMenu {
    private final ServiceTeacherInterface serviceTeacher;

    public TeacherMenu(ServiceTeacherInterface serviceTeacher) {
        this.serviceTeacher = serviceTeacher;
    }

    public void main(Console console) {
        while (true) {
            System.out.println(
                    "Teachers \n 1 - Add teacher\n 2 - Edit teacher\n 3 - Delete teacher\n 4 - Show all teachers\n 0 - Back"
            );

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createTeacher(console);
                    break;
                case 2:
                    editTeacher(console);
                    break;
                case 3:
                    deleteTeacher(console);
                    break;
                case 4:
                    showAllTeachers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private int readInt(Console console) {
        try {
            return Integer.parseInt(console.readLine("Your choice: "));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void createTeacher(Console console) {
        FullName fullName = PersonMenu.fullName(console);
        LocalDate birthDate = PersonMenu.birthDate(console);
        Contact contact = PersonMenu.contact(console);

        String uniqueCode = ConsoleMenu.readRequiredString(console, "Enter unique code: ");
        AcademicDegree degree = null;
        while (degree == null) {

            System.out.println("Academic degree: 1 - Candidate of sciences, 2 - Doctor of philosophy, 3 - Doctor of sciences, 4 - None");
            int degreeChoice = readInt(console);

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
        AcademicRank rank = null;
        while (rank == null) {
            System.out.println("Academic rank: 1 - Associate professor, 2 - Senior researcher, 3 - Professor, 4 - None");
            int rankChoice = readInt(console);

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
        LocalDate dateOfEmployment;
        while (true) {
            try {

                dateOfEmployment = LocalDate.parse(console.readLine("Enter date of employment: "));
                if (dateOfEmployment.isBefore(LocalDate.now())) {
                    break;
                } else {
                    System.out.println("date of employment cannot be in future");
                }
            }catch (DateTimeParseException e){
                System.out.println("Use format yyyy-mm-dd");
            }
        }
        BigDecimal salary = null;
        while (salary == null) {
            try {
                salary = new BigDecimal(console.readLine("Enter salary: "));
            }catch (NumberFormatException e) {
                System.out.println("Invalid salary! Use number and dot only");
            }
        }

        Teacher teacher = new Teacher(uniqueCode, fullName, birthDate, contact, degree, rank, dateOfEmployment, salary);
        serviceTeacher.create(teacher);
    }

    private void editTeacher(Console console) {
        Long id = null;
        while (id == null) {
            try {

                id = Long.parseLong(console.readLine("Enter teacher id to edit: "));
            }catch (NumberFormatException e) {
                System.out.println("Invalid teacher id! Use number only");
            }
        }
        Optional<Teacher> optionalTeacher = serviceTeacher.findById(id);
        if (optionalTeacher.isEmpty()) {
            System.out.println("Teacher not found!!!");
            return;
        }

        Teacher teacher = optionalTeacher.get();
        AcademicDegree degree = null;
        while (degree == null) {

            System.out.println("Academic degree: 1 - Candidate of sciences, 2 - Doctor of philosophy, 3 - Doctor of sciences, 4 - None");
            int degreeChoice = readInt(console);

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
        teacher.setAcademicDegree(degree);
        AcademicRank rank = null;
        while (rank == null) {
            System.out.println("Academic rank: 1 - Associate professor, 2 - Senior researcher, 3 - Professor, 4 - None");
            int rankChoice = readInt(console);

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
        teacher.setAcademicRank(rank);
        BigDecimal salary = null;
        while (salary == null) {
            try {
                salary = new BigDecimal(console.readLine("Enter salary: "));
            }catch (NumberFormatException e) {
                System.out.println("Invalid salary! Use number and dot only");
            }
        }
        teacher.setSalary(salary);
        serviceTeacher.update(teacher);
    }

    private void deleteTeacher(Console console) {
        Long id = Long.parseLong(console.readLine("Enter teacher id to edit: "));
        Optional<Teacher> optionalTeacher = serviceTeacher.findById(id);
        if (optionalTeacher.isEmpty()) {
            System.out.println("Teacher not found!!!");
            return;
        }

        serviceTeacher.delete(id);
    }

    private void showAllTeachers() {
        for (Teacher teacher : serviceTeacher.findAll()) {
            System.out.println("id -" + teacher.getId() + ", " + teacher);
        }
    }
}
