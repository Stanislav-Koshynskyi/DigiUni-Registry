package util;

import entity.*;
import exception.LinkedException;
import lombok.extern.slf4j.Slf4j;
import repository.*;

import java.util.List;
import java.util.NoSuchElementException;
@Slf4j
public class RepositoryLinker {
    public static void linkRepository(
            UniversityRepository universityRepository,
            FacultyRepository  facultyRepository,
            DepartmentRepository  departmentRepository,
            StudentGroupRepository  studentGroupRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository
    ){
        linkFacultyRepository(facultyRepository, universityRepository, teacherRepository);
        linkDepartmentRepository(departmentRepository, facultyRepository, teacherRepository);
        linkStudentGroupRepository(studentGroupRepository, departmentRepository, teacherRepository, studentRepository);
        linkStudentRepository(studentRepository, studentGroupRepository);

    }
    public static void linkFacultyRepository (
            FacultyRepository facultyRepository,
            UniversityRepository universityRepository,
            TeacherRepository teacherRepository
    ){
        List<Faculty> faculties = facultyRepository.findAll();
        for (Faculty faculty : faculties){
            try {
                faculty.setUniversity(universityRepository.findById(faculty.getUniversityId()).get());
                if (faculty.getDeanId() != null) {
                    faculty.setDean(teacherRepository.findById(faculty.getDeanId()).get());
                }
            }catch (NoSuchElementException e){
                log.error("error of linking faculty with id {}", faculty.getUniversityId(), e);
                throw new LinkedException("error of linking faculty with id " + faculty.getUniversityId(), e);
            }
        }
        log.info("linking faculty repository");
    }
    public static void linkDepartmentRepository (
            DepartmentRepository departmentRepository,
            FacultyRepository facultyRepository,
            TeacherRepository teacherRepository
    ){
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments){
            try{
                department.setFaculty(facultyRepository.findById(department.getFacultyId()).get());
                if (department.getHeadOfDepartmentId() != null) {
                    department.setHeadOfDepartment(teacherRepository.findById(department.getHeadOfDepartmentId()).get());
                }
            }catch (NoSuchElementException e) {
                log.error("error of linking department with id {}", department.getFacultyId(), e);
                throw new LinkedException("error of linking departments with id " + department.getFacultyId(), e);
            }
        }
        log.info("linking department repository");
    }
    public static void linkStudentGroupRepository (
            StudentGroupRepository studentGroupRepository,
            DepartmentRepository departmentRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository
    ){
        List<StudentGroup> studentGroups = studentGroupRepository.findAll();
        for (StudentGroup studentGroup : studentGroups){
            try{
                studentGroup.setDepartment(departmentRepository.findById(studentGroup.getDepartmentId()).get());
                if (studentGroup.getHeadOfGroupId() != null) {
                    studentGroup.setHeadOfGroup(teacherRepository.findById(studentGroup.getHeadOfGroupId()).get());
                }
                if (studentGroup.getGroupLeaderId() != null) {
                    studentGroup.setGroupLeader(studentRepository.findById(studentGroup.getGroupLeaderId()).get());
                }
            }catch (NoSuchElementException e){
                log.error("error of linking studentGroups with id {}", studentGroup.getDepartmentId(), e);
                throw new LinkedException("error of linking studentGroups with id " + studentGroup.getDepartmentId(), e);
            }
        }
        log.info("linking studentGroup repository");
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
                log.error("error of linking students with id {}", student.getGroupId(), e);
                throw new LinkedException("error of linking students with id" + student.getGroupId(), e);
            }
        }
        log.info("linking student repository");
    }
}
