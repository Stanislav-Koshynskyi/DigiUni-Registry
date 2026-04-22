package entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
@Setter
public class StudentGroup implements Entity{
    private Long id;
    private String name;
    @JsonIgnore
    private Teacher headOfGroup;
    @Getter
    private Long headOfGroupId;
    @JsonIgnore
    private Student groupLeader;
    @Getter
    private Long groupLeaderId;
    @JsonIgnore
    private HashSet<Student> students;
    @JsonIgnore
    private Department department;
    private Long departmentId;
    public  StudentGroup() {
        this.students = new HashSet<>();
    }
    public StudentGroup(Long id, String name,Department department, Teacher headOfGroup, Student groupLeader, HashSet<Student> students) {
        this.students = students != null ? new HashSet<>(students) : new HashSet<>();
        setId(id);
        setName(name);
        setHeadOfGroup(headOfGroup);
        setGroupLeader(groupLeader);
        setDepartment(department);


    }

    public StudentGroup(String name, Department department) {
        setName(name);
        setDepartment(department);
        students = new HashSet<>();
    }
    public StudentGroup(Long id,String name, Department department) {
        setId(id);
        setName(name);
        setDepartment(department);
        students = new HashSet<>();
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        if (department == null) throw new IllegalArgumentException("Department cannot be null");
        this.department = department;
        setDepartmentId(department.getId());
    }

    public Long getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Long departmentId) {
        if  (departmentId == null) throw new IllegalArgumentException("Department cannot be null");
        this.departmentId = departmentId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name cannot be empty");
        this.name = name;
    }

    public Optional<Teacher> getHeadOfGroup() {
        return Optional.ofNullable(headOfGroup);
    }

    public void setHeadOfGroup(Teacher headOfGroup) {
        if (headOfGroup != null) {
            this.headOfGroup = headOfGroup;
            this.headOfGroupId = headOfGroup.getId();
        }
        else{
            this.headOfGroup = null;
            this.headOfGroupId = null;
        }
    }

    public Optional<Student> getGroupLeader() {
        return Optional.ofNullable(groupLeader);
    }

    public void setGroupLeader(Student groupLeader) {
        if (groupLeader != null) {
            if (!students.contains(groupLeader)) {
                addStudent(groupLeader);
            }

            this.groupLeader = groupLeader;
            this.groupLeaderId = groupLeader.getId();
        }
        else{
            this.groupLeader = null;
            this.groupLeaderId = null;
        }
    }
    public void addStudent(Student student) {
        if (student ==  null) throw new IllegalArgumentException("student cannot be null");
        students.add(student);
    }
    public void removeStudent(Student student) {
        if (student ==  null) throw new IllegalArgumentException("student cannot be null");
        if (students.contains(student)) {
            students.remove(student);
            if (student.equals(groupLeader))
                setGroupLeader(null);

        }
        else{
            throw new IllegalArgumentException("student not in group");
        }
    }
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGroup that = (StudentGroup) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        List<Student> studentsList = getStudents();
        List <FullName> studentsName = studentsList.stream()
                .map(Person::getFullName)
                .toList();
        String  headOfGroupInfo = headOfGroup == null ? "vacant" : headOfGroup.getFullName().toString();
        String departmentInfo = department == null ? "lost, id = "+ departmentId : department.getName();
        String groupLeaderInfo = groupLeader == null ? "vacant" : groupLeader.getFullName().toString();
        return "Group[name - " +name + ", head - " +  headOfGroupInfo +
                ",department - "+ departmentInfo + ", group leader - " + groupLeaderInfo +
                ", students - " + studentsName +"]";
    }

}