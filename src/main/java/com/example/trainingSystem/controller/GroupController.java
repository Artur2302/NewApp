package com.example.trainingSystem.controller;

import com.example.trainingSystem.entity.Groups;
import com.example.trainingSystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    public final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Groups> getGroups(){
        return groupService.getGroups();
    }


    @PostMapping
    public Groups addNewGroup(@RequestBody Groups group){
        return groupService.addGroup(group);
    }

    @DeleteMapping
    public void deleteGroup(@PathVariable Long group_id){
        groupService.deleteGroup(group_id);
    }


    @PutMapping("/{group_id}/students/{student_id}")
    public Groups enrollStudentToGroup(
            @PathVariable("group_id") Long group_id,
            @PathVariable Long student_id
    ){
        return  groupService.enrollStudent(group_id,student_id);

    }

    @PutMapping("/{group_id}/students/delete/{student_id}")
    public Groups deleteStudentFromGroup(
            @PathVariable Long group_id,
            @PathVariable Long student_id
    ){
        return groupService.discardStudent(group_id,student_id);

    }

    @PutMapping("/{group_id}/instructors/{instructor_id}")
    public Groups assignInstructorToGroup(
            @PathVariable Long group_id,
            @PathVariable Long instructor_id
    ){

        return groupService.assignInstructor(group_id,instructor_id);


    }

}
