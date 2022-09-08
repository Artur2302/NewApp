package com.example.trainingSystem.service;

import com.example.trainingSystem.entity.Groups;
import com.example.trainingSystem.entity.Instructor;
import com.example.trainingSystem.entity.Student;
import com.example.trainingSystem.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GroupService {
    private final GroupRepository groupRepository;


    private final StudentService studentService;
    private final InstructorService instructorService;

    private final EmailSenderService emailSenderService;

    @Autowired
    public GroupService(GroupRepository groupRepository,
                        StudentService studentService,
                        InstructorService instructorService,
                        EmailSenderService emailSenderService) {
        this.groupRepository = groupRepository;
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.emailSenderService = emailSenderService;
    }


    public List<Groups> getGroups() {
        return groupRepository.findAll();
    }


    public Groups addGroup(Groups group) {
        if (groupRepository.findGroupsByName(group.getName()).isPresent()) {
            throw new IllegalStateException("Name is Taken");
        } else {
            return groupRepository.save(group);
        }
    }

    public Groups getOneGroup(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalStateException("Group with id " + groupId + " doesn't exist"));
    }

    public Groups enrollStudent(Long group_id, Long student_id) {
        Groups group = getOneGroup(group_id);
        Student student = studentService.getOneStudent(student_id);
        List<Student> enrolledStudents = group.getEnrolledStudents();
        if (enrolledStudents.size() >= 10) {
            throw new IllegalStateException
                    ("Number of enrolled students should not be more than 10 for each group");
        }
        for (Student s : enrolledStudents) {
            if (Objects.equals(s.getId(), student.getId())) {
                throw new IllegalStateException("Student already exists");
            }
        }

        int quantityOfGroupsPerStudent = student.getGroups().size();
        if (quantityOfGroupsPerStudent >= 5) {
            throw new IllegalStateException("One student should belong to max 5 groups");
        }
        enrolledStudents.add(student);
        emailSenderService.sendEmail("instructor@yopmail.com", "Student Enrolled",
                "This email is about that a Student " + student.getName() + " joined to the group " + group.getName());
        return groupRepository.save(group);

    }

    public void deleteGroup(Long group_id) {
        Groups group = getOneGroup(group_id);
        groupRepository.delete(group);

    }

    public Groups discardStudent(Long group_id, Long student_id) {
        Groups group = getOneGroup(group_id);
        Student student = studentService.getOneStudent(student_id);
        List<Student> enrolledStudents = group.getEnrolledStudents();
        enrolledStudents.remove(student);
        emailSenderService.sendEmail("instructor@yopmail.com", "Student discarded",
                "This email is about that a Student " + student.getName() + " discarded from the group " + group.getName());
        return groupRepository.save(group);
    }

    public Groups assignInstructor(Long group_id, Long instructor_id) {
        Groups group = getOneGroup(group_id);
        Instructor instructor = instructorService.getOneInstructor(instructor_id);
        group.setInstructor(instructor);
        return groupRepository.save(group);

    }


}
