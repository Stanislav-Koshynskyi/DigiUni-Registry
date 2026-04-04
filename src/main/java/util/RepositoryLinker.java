package util;

import entity.*;
import exception.LinkedException;
import repository.*;

import java.util.List;
import java.util.NoSuchElementException;

public class RepositoryLinker {
    public static void linkRepository(
            UniversityRepository universityRepository,
            FacultyRepository  facultyRepository,
            DepartmentRepository  departmentRepository,
            StudentGroupRepository  studentGroupRepository,
            StudentRepository studentRepository
    ){
        linkFacultyRepository(facultyRepository, universityRepository);
        linkDepartmentRepository(departmentRepository, facultyRepository);
        linkStudentGroupRepository(studentGroupRepository, departmentRepository);
        linkStudentRepository(studentRepository, studentGroupRepository);

    }
    public static void linkFacultyRepository (
            FacultyRepository facultyRepository,
            UniversityRepository universityRepository
    ){
        List<Faculty> faculties = facultyRepository.findAll();
        for (Faculty faculty : faculties){
            try {
                faculty.setUniversity(universityRepository.findById(faculty.getUniversityId()).get());
            }catch (NoSuchElementException e){
                throw new LinkedException("error of linking faculty with id " + faculty.getUniversityId(), e);
            }
        }
    }
    public static void linkDepartmentRepository (
            DepartmentRepository departmentRepository,
            FacultyRepository facultyRepository
    ){
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments){
            try{
                department.setFaculty(facultyRepository.findById(department.getFacultyId()).get());
            }catch (NoSuchElementException e) {
                throw new LinkedException("error of linking departments with id " + department.getFacultyId(), e);
            }
        }
    }
    public static void linkStudentGroupRepository (
            StudentGroupRepository studentGroupRepository,
            DepartmentRepository departmentRepository
    ){
        List<StudentGroup> studentGroups = studentGroupRepository.findAll();
        for (StudentGroup studentGroup : studentGroups){
            try{
                studentGroup.setDepartment(departmentRepository.findById(studentGroup.getDepartmentId()).get());
            }catch (NoSuchElementException e){
                throw new LinkedException("error of linking studentGroups with id " + studentGroup.getDepartmentId(), e);
            }
        }
    }
    public static void linkStudentRepository (
            StudentRepository studentRepository,
            StudentGroupRepository studentGroupRepository
    ){
        List<Student> students = studentRepository.findAll();
        for ( Student student: students){
            try {
                student.setGroup(studentGroupRepository.findById(student.getGroupId()).get());
                student.getGroup().addStudent(student);
            }catch (NoSuchElementException e){
                throw new LinkedException("error of linking students with id " + student.getGroupId(), e);
            }
        }
    }
}
