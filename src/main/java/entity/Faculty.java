package entity;

import java.util.Objects;
import java.util.Optional;

public class Faculty {
    private Long id;
    private Long uniqueCode;
    private String name;
    private String shortName;
    private Long deanId;
    private Teacher dean;
    private Contact contact;

    public Faculty() {
    }

    public Faculty(Long uniqueCode, String name, String shortName, Teacher dean, Contact contact) {
        setUniqueCode(uniqueCode);
        setName(name);
        setShortName(shortName);
        setDean(dean);
        setContact(contact);
    }
    public Faculty(Long id,Long uniqueCode, String name, String shortName, Teacher dean, Contact contact) {
        this(uniqueCode, name, shortName, dean, contact);
        setId(id);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(Long uniqueCode) {
        if (uniqueCode == null ||  uniqueCode <= 0) {
            throw new IllegalArgumentException("Invalid unique code");
        }
        this.uniqueCode = uniqueCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        if (shortName == null || shortName.isBlank()) {
            throw new IllegalArgumentException("Invalid short name");
        }
        if (shortName.length() > 15){
            throw new IllegalArgumentException("Short name must be short");
        }
        this.shortName = shortName;
    }

    public Long getDeanId() {
        return deanId;
    }

    public void setDeanId(Long deanId) {
        this.deanId = deanId;
    }

    public Optional<Teacher> getDean() {
        return Optional.ofNullable(dean);
    }

    public void setDean(Teacher dean) {

        this.dean = dean;
        if (dean != null) {
            setDeanId(dean.getId());
        }
        else{
            setDeanId(null);
        }
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    public int hashCode() {
        return Objects.hash(id);
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Faculty other = (Faculty) obj;
        return Objects.equals(id, other.id);
    }

    public String toString() {
        String deanInfo = dean !=null ? dean.toString() : "vacant";
        return "Faculty[ unique code - " + getUniqueCode() + ", name - " + getName() +", contact - "+ getContact()+ ", dean -" + deanInfo +  "]";
    }
}
