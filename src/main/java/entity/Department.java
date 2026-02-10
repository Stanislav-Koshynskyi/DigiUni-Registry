package entity;

import java.util.Objects;
import java.util.Optional;

public class Department implements Entity {
    private Long id;
    private String uniqueCode;
    private String name;
    private String shortName;
    private Faculty faculty;
    private Long facultyId;
    private Teacher headOfDepartment;
    private Long headOfDepartmentId;
    private String cabinet;
    public static final int MAX_SHORT_NAME_LENGTH = 15;


    public Department(){}
    public Department(String uniqueCode, String name, String shortName, Faculty faculty, Teacher headOfDepartment, String cabinet) {
        setUniqueCode(uniqueCode);
        setName(name);
        setShortName(shortName);
        setFaculty(faculty);
        setHeadOfDepartment(headOfDepartment);
        setCabinet(cabinet);
    }
    public Department (Long id, String uniqueCode, String name, String shortName, Faculty faculty, Teacher headOfDepartment, String cabinet) {
        this(uniqueCode, name, shortName, faculty, headOfDepartment, cabinet);
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
        if (uniqueCode == null || uniqueCode.isBlank()) {
            throw new IllegalArgumentException("uniqueCode can not be blank");
        }
        this.uniqueCode = uniqueCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name can not be blank");
        }
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        if (shortName == null || shortName.isBlank()) {
            throw new IllegalArgumentException("shortName can not be blank");
        }
        if (shortName.length() > 15) {
            throw new IllegalArgumentException("shortName must be short");
        }
        this.shortName = shortName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("faculty can not be null");
        }
        this.faculty = faculty;
        setFacultyId(faculty.getId());

    }

    public Long getFacultyId() {
        return facultyId;
    }
    public void setFacultyId(Long facultyId) {
        if (facultyId == null) {
            throw new IllegalArgumentException("facultyId can not be null");
        }
        this.facultyId = facultyId;
    }

    public Optional<Teacher> getHeadOfDepartment() {
        return Optional.ofNullable(headOfDepartment);
    }

    public void setHeadOfDepartment(Teacher headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
        if  (headOfDepartment == null) {
            setHeadOfDepartmentId(null);
        }
        else {
            setHeadOfDepartmentId(headOfDepartment.getId());
        }
    }

    public Long getHeadOfDepartmentId() {

        return headOfDepartmentId;
    }

    public void setHeadOfDepartmentId(Long headOfDepartmentId) {
        this.headOfDepartmentId = headOfDepartmentId;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        if (cabinet == null || cabinet.isBlank()) {
            throw new IllegalArgumentException("cabinet can not be blank");
        }
        this.cabinet = cabinet;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Department that = (Department) obj;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        String facultyInfo = faculty == null ? "lost, id =" + facultyId  : faculty.getShortName();
        String headOfDepartmentInfo = headOfDepartment == null ? "vacant" : headOfDepartment.getFullName().toString();
        return "Department [uniqueCode=" + uniqueCode + ", name=" + name + ", shortName=" + shortName+
                ", faculty=" + facultyInfo + ", head of department=" + headOfDepartmentInfo +
                ", cabinet=" + cabinet + "]";
    }
}
