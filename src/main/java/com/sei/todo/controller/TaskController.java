package com.sei.todo.controller;

import com.sei.todo.model.Task;
import com.sei.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class TaskController {

    private TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping(path="/tasks")
    public List<Task> getTasks(){
        System.out.println("getTasks() ===> Called");
        return taskRepository.findAll();
    }

    @GetMapping(path="/tasks/{taskId}")
    public Task getTask(@PathVariable Long taskId){
        System.out.println("get this task " + taskId);
        return taskRepository.findById(taskId).get();
    }

}
