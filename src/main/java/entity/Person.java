package entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

public abstract class Person {
    private Long id;
    private String uniqueCode;
    private FullName fullName;
    private LocalDate birthDate;
    private Contact contact;
    public Person() {}
    public Person(String uniqueCode,FullName fullName, LocalDate birthDate, Contact contact) {
        setUniqueCode(uniqueCode);
        setFullName(fullName);
        setBirthDate(birthDate);
        setContact(contact);
    }
    public Person(Long id, String uniqueCode, FullName fullName, LocalDate birthDate, Contact contact) {
        this (uniqueCode, fullName, birthDate, contact);
        setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        if (uniqueCode == null || uniqueCode.isBlank()) throw new IllegalArgumentException("uniqueCode cannot be empty");
        this.uniqueCode = uniqueCode;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }



    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if  (birthDate == null) throw new IllegalArgumentException("birthDate cannot be empty");
        LocalDate now = LocalDate.now();
        if (birthDate.isAfter(now)) throw new IllegalArgumentException("birthDate cannot be in the future");
        if (Period.between( birthDate, now).getYears() < 16 ) {
            throw new IllegalArgumentException("Person must be 16+ years old");
        }
        this.birthDate = birthDate;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return Objects.equals(id, other.id);
    }
}
