package com.sei.todo.controller;


import com.sei.todo.exceptions.InformationExistsException;
import com.sei.todo.exceptions.InformationNotFoundException;
import com.sei.todo.model.Task;
import com.sei.todo.repository.TaskRepository;
import com.sei.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//localhost/api set up for back end
@RestController
@RequestMapping(path="/api")
public class TaskController  {
    //injecting this for use
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping(path="/tasks")
    public List<Task> getTasks(){
        System.out.println("Calling get Tasks");
        return this.taskService.getTasks();
    }

    @GetMapping(path = "/tasks/{taskId}")
    public Task getTask(@PathVariable Long taskId)  {
        return this.taskService.getTask(taskId);
    }

    @PostMapping("/tasks/")
    public Task createTask(@RequestBody Task taskObject)
    {
        return this.taskService.createTask(taskObject);
    }

    @PutMapping("/tasks/{taskId}")
    public Task updateTask(@PathVariable(value = "taskId")Long taskId, @RequestBody Task taskObject){
              return this.taskService.updateTask(taskId, taskObject);
    }

    @DeleteMapping("/tasks/{taskId}")
    public Task deleteTask(@PathVariable(value = "taskId") Long taskId){
                return this.taskService.deleteTask(taskId);
    }
}
