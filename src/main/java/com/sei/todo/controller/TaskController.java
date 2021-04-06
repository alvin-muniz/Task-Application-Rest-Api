package com.sei.todo.controller;

import com.sei.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class TaskController {

    private TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping(path="/tasks")
    public String getTasks(){
        return "getTasks() ===> Called";
    }

    @GetMapping(path="/tasks/{taskId}")
    public String getTask(){
        return "get this task ";
    }

}
