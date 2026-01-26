package entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Teacher extends Person{
    private AcademicRank academicRank;
    private AcademicDegree academicDegree;
    LocalDate dateOfEmployment;
    BigDecimal salary;

    public Teacher(){}
    public Teacher(String uniqueCode,FullName fullName, LocalDate birthDate, Contact contact,
                   AcademicDegree academicDegree, AcademicRank academicRank,LocalDate dateOfEmployment,
                   BigDecimal salary){
        super(uniqueCode, fullName, birthDate, contact);
        setAcademicDegree(academicDegree);
        setAcademicRank(academicRank);
        setDateOfEmployment(dateOfEmployment);
        setSalary(salary);
    }
    public Teacher(Long id, String uniqueCode, FullName fullName, LocalDate birthDate, Contact contact,
                   AcademicDegree academicDegree, AcademicRank academicRank,LocalDate dateOfEmployment,
                   BigDecimal salary){
        this(uniqueCode, fullName, birthDate, contact, academicDegree, academicRank,dateOfEmployment,salary);
        setId(id);
    }

    public AcademicRank getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(AcademicRank academicRank) {
        if (academicRank == null) {
            throw new IllegalArgumentException("academicRank can't be null");
        }
        this.academicRank = academicRank;
    }

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(AcademicDegree academicDegree) {
        if (academicDegree == null) {
            throw new IllegalArgumentException("academicDegree can't be null");
        }
        this.academicDegree = academicDegree;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        if (dateOfEmployment == null) {
            throw new IllegalArgumentException("dateOfEmployment can't be null");
        }
        if (dateOfEmployment.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("dateOfEmployment can not be in future");
        }
        this.dateOfEmployment = dateOfEmployment;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        if  (salary == null) {
            throw new IllegalArgumentException("salary can't be null");
        }
        if (salary.signum() < 0) {
            throw new IllegalArgumentException("salary can't be negative");
        }
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Teacher[unique code - " + getUniqueCode()+", name - " + getFullName() +", salary - " + salary
                + ", academic rank - " + academicRank + ", academic degree - " + academicDegree + ", contact - " + getContact()
                + ", date of employment - " + dateOfEmployment +  ", birthday - "+getBirthDate() +"]";
    }
}
