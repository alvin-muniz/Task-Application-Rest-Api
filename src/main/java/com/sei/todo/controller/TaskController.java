package com.sei.todo.controller;


import com.sei.todo.exceptions.InformationExistsException;
import com.sei.todo.exceptions.InformationNotFoundException;
import com.sei.todo.model.Task;
import com.sei.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//localhost/api set up for back end
@RestController
@RequestMapping(path="/api")
public class TaskController  {
    //injecting this for use
    private TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping(path="/tasks")
    public List<Task> getTasks(){
        System.out.println("Calling get Tasks");
        return this.taskRepository.findAll();
    }

    @GetMapping(path = "/tasks/{taskId}")
    public Task getTask(@PathVariable Long taskId)  {
        System.out.println("getting the category with id " + taskId);
        Optional<Task> foundTask = this.taskRepository.findById(taskId);
        if(foundTask.isPresent())
            return foundTask.get();
        else
            throw new InformationNotFoundException("Nope");
    }

    @PostMapping("/tasks/")
    public Task createTask(@RequestBody Task taskObject)
    { System.out.println("Creating a new category");
        Optional<Task> task = this.taskRepository.findByTitle(taskObject.getTitle());
        if(!(task.isPresent()))
            return this.taskRepository.save(taskObject);
        else
            throw new InformationExistsException("There is already a note wtih that title!!");
    }

    @PutMapping("/tasks/{taskId}")
    public Task updateTask(@PathVariable(value = "taskId")Long taskId, @RequestBody Task taskObject){
        System.out.println("this task was updated ID: " + taskId + "Update info: " + taskObject.toString());

        Optional<Task> foundTask = this.taskRepository.findById(taskId);
        if(foundTask.isPresent())
        {
           boolean sameObject = foundTask.equals(taskObject);
           if(sameObject)
           {
               throw new InformationExistsException("THis update changes nothing");
           }else
           {
               Task updateTask = this.taskRepository.findById(taskId).get();
               updateTask.setDescription(taskObject.getDescription());
               updateTask.setCompleted(taskObject.isCompleted());
               updateTask.setTitle(taskObject.getTitle());
               return taskRepository.save(updateTask);
           }
        }else
        {
            throw new InformationNotFoundException("This object does not exists");
        }

    }

    @DeleteMapping("/task/{taskId}")
    public Task deleteTask(@PathVariable(value = "taskId") Long taskId){

        Task foundTask = this.taskRepository.findById(taskId).get();
                if(foundTask != null) {
                    this.taskRepository.delete(foundTask);
                    return foundTask;
                }
                else
                    throw new InformationNotFoundException("This is not found to be delted");
    }
}
