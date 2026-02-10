package ui;

import entity.*;
import service.ServiceStudentInterface;
import service.ServiceStudentGroupInterface;
import java.io.Console;
import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

public class StudentMenu {
    private final ServiceStudentInterface serviceStudent;
    private final ServiceStudentGroupInterface serviceStudentGroup;
    private final FindByStudent findByStudent;

    public StudentMenu(ServiceStudentInterface serviceStudent, ServiceStudentGroupInterface serviceStudentGroup) {
        this.serviceStudent = serviceStudent;
        this.serviceStudentGroup = serviceStudentGroup;
        this.findByStudent = new FindByStudent(serviceStudent, serviceStudentGroup);
    }
    public void main(Console console) {
        while (true) {
            System.out.println("Student \n 1 - Add student \n 2 - Edit student\n 3 - Delete student \n 4 - Show all students\n 5 - Find students\n 0 - Back");

            int userSelect = readInt(console);

            switch (userSelect) {
                case 1:
                    createStudent(console);
                    break;
                case 2:
                    editStudent(console);
                    break;
                case 3:
                    deleteStudent(console);
                    break;
                case 4:
                    showAllStudents();
                    break;
                case 5:
                    findByStudent.main(console);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input!");
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

    private void createStudent(Console console) {
        StudentGroup group;
        while (true) {
            Long groupId = ConsoleMenu.readRequiredLong(console, "Enter group id(-1 to exit): ");
            if (groupId.equals(Long.valueOf(-1))) return;
            Optional<StudentGroup> optionalGroup = serviceStudentGroup.findById(groupId);
            if  (optionalGroup.isPresent()) {
                group = optionalGroup.get();
                break;
            }
            else {
                System.out.println("Group not found!!!");

            }
        }


        FullName fullName = PersonMenu.fullName(console);
        LocalDate birthDate = PersonMenu.birthDate(console);
        Contact contact = PersonMenu.contact(console);

        String uniqueCode = ConsoleMenu.readRequiredString(console, "Enter unique code: ");
        String recordBook = ConsoleMenu.readRequiredString(console, "Enter record book number: ");
        int course;
        while (true) {
            try {
            course = Math.toIntExact(ConsoleMenu.readRequiredLong(console, "Enter course (1-6): "));
            if (course >=1  && course <= 6) break;
            else System.out.println("course must be between 1 and 6");

            }catch (NumberFormatException e){
                System.out.println("Invalid input, use numbers only!");
            }
        }
        Year yearOfAdmission;
        while (true) {
            try {
                int year = Math.toIntExact(ConsoleMenu.readRequiredLong(console, "Enter year of admission: "));
                yearOfAdmission = Year.of(year);
                if (!yearOfAdmission.isAfter(Year.now()) && !yearOfAdmission.isBefore(Year.of(Student.MINIUM_YEAR_OF_ADMISSION))){
                    break;
                }
                else{
                    System.out.println("Invalid year of admission, cannot be in future and cannot be too past");
                }
            }catch (NumberFormatException e){
                System.out.println("Use only numbers only!");
            }
        }
        FormOfEducation form = null;
        while (form == null) {
        System.out.println("Form of education: 1 - Budget, 2 - Contract");
        int userChoiceFormOfEducation = readInt(console);

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

        StudentStatus status = StudentStatus.STUDIES;



        Student student = new Student(uniqueCode, recordBook, fullName, birthDate, contact, form, status, yearOfAdmission, course, group);
        serviceStudent.create(student);
    }

    private void editStudent(Console console) {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter student id to edit: ");
        Optional<Student> optionalStudent = serviceStudent.findById(id);
        if (optionalStudent.isEmpty()) {
            System.out.println("Student not found!!!");
            return;
        }
        Student student = optionalStudent.get();
        String recordBook = console.readLine("Enter record book number: ");
        Integer course = null;
        while (true) {
            String stringCourse = console.readLine("Enter course (1-6): ");
            if (stringCourse.isBlank()) break;
            else{
                try {
                    course = Integer.valueOf(stringCourse);
                    if (course >=1  && course <= 6) break;
                    else{
                        System.out.println("Invalid course must be between 1 and 6");
                    }
                }catch (NumberFormatException e){
                    System.out.println("Invalid input, use numbers only!");
                }
            }
        }
        if (!recordBook.isBlank())
            student.setRecordBookNumber(recordBook);
        if (course != null)
            student.setCourse(course);

        System.out.println("Student status: 1 - Studies, 2 - Academic leave, 3 - Expelled");
        int statusChoice = readInt(console);

        switch (statusChoice) {
            case 1:
                student.setStudentStatus(StudentStatus.STUDIES);
                break;
            case 2:
                student.setStudentStatus(StudentStatus.ACADEMIC_LEAVE);
                break;
            case 3:
                student.setStudentStatus(StudentStatus.EXPELLED);
                break;
            default:
                System.out.println("Status not changed!");
        }

        serviceStudent.update(student);
    }

    private void deleteStudent(Console console) {
        Long id = ConsoleMenu.readRequiredLong(console, "Enter student id to delete: ");
        Optional<Student> optionalStudent = serviceStudent.findById(id);
        if (optionalStudent.isEmpty()) {
            System.out.println("Student not found!!!");
            return;
        }

        Student student = optionalStudent.get();
        student.getGroup().removeStudent(student);
        serviceStudent.delete(id);
    }

    private void showAllStudents() {
        for (Student student : serviceStudent.findAll())
            System.out.println("id - "+ student.getId() + ", " + student);
    }
}
