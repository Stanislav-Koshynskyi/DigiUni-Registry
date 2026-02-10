package ui;

import java.io.Console;
import java.time.LocalDate;
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
        } catch (NumberFormatException e){
            return -1;
        }
    }

    private void createTeacher(Console console){
        FullName fullName = PersonMenu.fullName(console);
        LocalDate birthDate = PersonMenu.birthDate(console);
        Contact contact = PersonMenu.contact(console);

        String uniqueCode = console.readLine("Enter unique code: ");
        System.out.println("Academic degree: 1 - Candidate of sciences, 2 - Doctor of philosophy, 3 - Doctor of sciences, 4 - None");
        int degreeChoice = readInt(console);
        AcademicDegree degree;
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
                return;
        }

        System.out.println("Academic rank: 1 - Associate professor, 2 - Senior researcher, 3 - Professor, 4 - None");
        int rankChoice = readInt(console);
        AcademicRank rank;
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
                return;
        }
        LocalDate dateOfEmployment = LocalDate.parse(console.readLine("Enter date of employment: "));
        BigDecimal salary = new BigDecimal(console.readLine("Enter salary: "));

        Teacher teacher = new Teacher(uniqueCode, fullName, birthDate, contact, degree, rank, dateOfEmployment, salary);
        serviceTeacher.create(teacher);
    }

    private void editTeacher(Console console) {
        Long id = Long.parseLong(console.readLine("Enter teacher id to edit: "));
        Optional<Teacher> optionalTeacher = serviceTeacher.findById(id);
        if (optionalTeacher.isEmpty()) {
            System.out.println("Teacher not found!!!");
            return;
        }

        Teacher teacher = optionalTeacher.get();
        System.out.println("Academic degree: 1 - Candidate of sciences, 2 - Doctor of philosophy, 3 - Doctor of sciences, 4 - None");
        int degreeChoice = readInt(console);
        switch (degreeChoice) {
            case 1:
                teacher.setAcademicDegree(AcademicDegree.CANDIDATE_OF_SCIENCES);
                break;
            case 2:
                teacher.setAcademicDegree(AcademicDegree.DOCTOR_OF_PHILOSOPHY);
                break;
            case 3:
                teacher.setAcademicDegree(AcademicDegree.DOCTOR_OF_SCIENCES);
                break;
            case 4:
                teacher.setAcademicDegree(AcademicDegree.NONE);
                break;
            default:
                System.out.println("Invalid academic degree!");
                return;
        }

        System.out.println("Academic rank: 1 - Associate professor, 2 - Senior researcher, 3 - Professor, 4 - None");
        int rankChoice = readInt(console);
        switch (rankChoice) {
            case 1:
                teacher.setAcademicRank(AcademicRank.ASSOCIATE_PROFESSOR);
                break;
            case 2:
                teacher.setAcademicRank(AcademicRank.SENIOR_RESEARCHER);
                break;
            case 3:
                teacher.setAcademicRank(AcademicRank.PROFESSOR);
                break;
            case 4:
                teacher.setAcademicRank(AcademicRank.NONE);
                break;
            default:
                System.out.println("Invalid academic rank!");
                return;
        }

        String salaryStr = console.readLine("Enter salary: ");
        if (!salaryStr.isBlank())
            teacher.setSalary(new BigDecimal(salaryStr));

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
