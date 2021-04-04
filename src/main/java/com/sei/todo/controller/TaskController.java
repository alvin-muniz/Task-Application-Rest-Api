package com.sei.todo.controller;


import org.springframework.web.bind.annotation.*;

//localhost/api set up for back end
@RestController
@RequestMapping(path="/api")
public class TaskController {

    @GetMapping(path="/tasks")
    public String getTasks(){
        return "get all tasks";
    }

    @GetMapping(path = "/tasks/{taskId}")
    public String getTask(@PathVariable Long taskId){
        return "getting the category with id " + taskId;
    }

    @PostMapping("/tasks/")
    public String createTask(@RequestBody String body){
        return "creating a task " + body;
    }

    @PutMapping("/tasks/{taskId}")
    public String updateTask(@PathVariable(value = "taskId")Long taskId, @RequestBody String body){
        return "this task was updated ID: " + taskId + "Update info: " + body;
    }

    @DeleteMapping("/task/{taskId}")
    public String deleteTask(@PathVariable(value = "taskId") Long taskId){
        return "this wask was delete " + taskId;
    }
}
