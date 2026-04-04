package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Optional;

public class Faculty implements Entity {
    private Long id;
    private String uniqueCode;
    private String name;
    private String shortName;
    private Long deanId;
    @JsonIgnore
    private Teacher dean;
    private Contact contact;
    @JsonIgnore
    private University university;
    private Long universityId ;
    public static final int MAX_SHORT_NAME_LENGTH = 15;

    public Faculty() {
    }

    public Faculty(String uniqueCode, String name, String shortName, Teacher dean, Contact contact, University university) {
        setUniqueCode(uniqueCode);
        setName(name);
        setShortName(shortName);
        setDean(dean);
        setContact(contact);
        setUniversity(university);
    }




    public Faculty(Long id,String uniqueCode, String name, String shortName, Teacher dean, Contact contact, University university) {
        this(uniqueCode, name, shortName, dean, contact,  university);
        setId(id);
    }


    public Long getId() {
        return id;
    }

    public void setUniversity(University university) {
        if (university == null)throw new NullPointerException("university can not be null");
        this.university = university;
        this.universityId = university.getId();
    }
    public University getUniversity() {
        return university;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        if (uniqueCode == null) {
            throw new IllegalArgumentException("uniqueCode cannot be null");
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
        String deanInfo = dean !=null ? dean.getFullName().toString() : "vacant";
        return "Faculty[ university - " + university.getFullName() + ", unique code - " + getUniqueCode() + ", name - " + getName() +", contact - "+
                getContact()+ ", dean -" + deanInfo +  "]";
    }
}
