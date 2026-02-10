package entity;

import java.time.LocalDate;
import java.time.Year;
import java.util.Objects;

public class Student extends Person{
    private FormOfEducation formOfEducation;
    private String recordBookNumber;
    private StudentStatus studentStatus;
    private Year yearOfAdmission;
    private int course;
    private StudentGroup group;
    private Long groupId;

    public Student() {}
    public Student(String uniqueCode,String recordBookNumber, FullName fullName, LocalDate birthDate, Contact contact, FormOfEducation formOfEducation, StudentStatus studentStatus, Year yearOfAdmission, int course, StudentGroup group) {
        super(uniqueCode, fullName, birthDate, contact);
        setFormOfEducation(formOfEducation);
        setStudentStatus(studentStatus);
        setYearOfAdmission(yearOfAdmission);
        setCourse(course);
        setGroup(group);
        setRecordBookNumber(recordBookNumber);
    }
    public Student(Long id, String recordBookNumber, String uniqueCode, FullName fullName, LocalDate birthDate, Contact contact, FormOfEducation formOfEducation, StudentStatus studentStatus, Year yearOfAdmission, int course, StudentGroup group) {
        super(id, uniqueCode, fullName, birthDate, contact);
        setFormOfEducation(formOfEducation);
        setStudentStatus(studentStatus);
        setYearOfAdmission(yearOfAdmission);
        setCourse(course);
        setGroup(group);
        setRecordBookNumber(recordBookNumber);
    }


    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        if (group == null) throw new IllegalArgumentException("group cannot be null");
        if (this.group != null && this.group.equals(group)) return;

        if (this.group != null) {
            this.group.removeStudent(this);
        }
        this.group = group;
        this.group.addStudent(this);
        this.groupId = group.getId();
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        if  (course < 1 || course > 6) throw new IllegalArgumentException("course cannot be less than 1 or bigger than 6");
        this.course = course;
    }

    public Year getYearOfAdmission() {
        return yearOfAdmission;
    }

    public void setYearOfAdmission(Year yearOfAdmission) {
        if (yearOfAdmission == null) throw new IllegalArgumentException("yearOfAdmission cannot be null");
        if (yearOfAdmission.isAfter(Year.now())) {
            throw new IllegalArgumentException("yearOfAdmission cannot be in the future");
        }
        if (yearOfAdmission.isBefore(Year.of(1898))) {
            throw new IllegalArgumentException("yearOfAdmission too old");
        }
        this.yearOfAdmission = yearOfAdmission;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(StudentStatus studentStatus) {
        if (studentStatus == null) throw new IllegalArgumentException("studentStatus cannot be null");
        this.studentStatus = studentStatus;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        if (formOfEducation == null) throw new IllegalArgumentException("formOfEducation cannot be null");
        this.formOfEducation = formOfEducation;
    }
    public String getRecordBookNumber() {
        return recordBookNumber;
    }
    public void setRecordBookNumber(String recordBookNumber) {
        if (recordBookNumber == null || recordBookNumber.isBlank()) throw new IllegalArgumentException("recordBookNumber can not be null or blank");
        this.recordBookNumber = recordBookNumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(this.getId() , student.getId());
    }
    @Override
    public int hashCode() {
        return  Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        String groupInfo = group == null ? "lost" : group.getName();
        return "Student[unique code - "+ getUniqueCode()+", record book number - " + recordBookNumber
                + ", name - " + getFullName() +", course - "+ course
                + ", form of education - " + formOfEducation + ", student status - " + studentStatus + ", contact - " + getContact()
                + ", group - " + groupInfo +", year of admission - " + yearOfAdmission + ", birthday - "+getBirthDate() +"]";
    }
}
