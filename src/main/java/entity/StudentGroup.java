package entity;
import java.util.*;

public class StudentGroup {
    private Long id;
    private String name;
    private Teacher headOfGroup;
    private Student groupLeader;
    private final HashSet<Student> students;

    public StudentGroup(Long id, String name, Teacher headOfGroup, Student groupLeader, HashSet<Student> students) {
        this.students = students != null ? new HashSet<>(students) : new HashSet<>();
        setId(id);
        setName(name);
        setHeadOfGroup(headOfGroup);
        setGroupLeader(groupLeader);


    }


    public StudentGroup(String name) {
        setName(name);
        students = new HashSet<>();
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
        this.headOfGroup = headOfGroup;
    }

    public Optional<Student> getGroupLeader() {
        return Optional.ofNullable(groupLeader);
    }

    public void setGroupLeader(Student groupLeader) {
        if (groupLeader!=null && !students.contains(groupLeader)) {
            addStudent(groupLeader);
        }

        this.groupLeader = groupLeader;
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
        String groupLeaderInfo = groupLeader == null ? "vacant" : groupLeader.getFullName().toString();
        return "Group[name - " +name + ", head - " +  headOfGroupInfo + ", group leader - " + groupLeaderInfo +
                ", students - " + studentsName +"]";
    }
}