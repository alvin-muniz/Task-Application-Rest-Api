package com.sei.todo.controller;

import com.sei.todo.exceptions.InformationExistsException;
import com.sei.todo.model.Task;
import com.sei.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path="/tasks")
    public Task createTask(@RequestBody Task taskObject){

        Optional<Task> foundTask = Optional.ofNullable(this.taskRepository.findByName(taskObject.getName()));
        if(foundTask.isPresent())
            throw new InformationExistsException("This task already exists!");
        else
            return this.taskRepository.save(taskObject);

    }

}
