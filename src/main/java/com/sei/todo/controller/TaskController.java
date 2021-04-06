package com.sei.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class TaskController {

    public TaskController(){

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
