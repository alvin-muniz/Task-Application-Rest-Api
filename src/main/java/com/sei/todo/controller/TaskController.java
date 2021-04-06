package com.sei.todo.controller;

import com.sei.todo.exceptions.InformationExistsException;
import com.sei.todo.exceptions.InformationNotFoundException;
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
        Optional foundTask = taskRepository.findById(taskId);
        if(foundTask.isPresent())
        {
            return taskRepository.findById(taskId).get();
        }
        else
            {
                throw new InformationNotFoundException("THis id does not exist");
            }

    }

    @PostMapping(path="/tasks")
    public Task createTask(@RequestBody Task taskObject){

        Optional<Task> foundTask = Optional.ofNullable(this.taskRepository.findByName(taskObject.getName()));
        if(foundTask.isPresent())
            throw new InformationExistsException("This task already exists!");
        else
            return this.taskRepository.save(taskObject);
    }
    @PutMapping(path="/tasks/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task taskObject)
    {
        System.out.println("Update Tasks Called");

        Optional foundTask = this.taskRepository.findById(taskId);
        if(foundTask.isPresent()){
            Task task = (Task)foundTask.get();
            System.out.println("This task is present");
            if(task.equals(taskObject)){
                throw new InformationExistsException("These are the same objects");
            }else{
                boolean completedSame = task.isCompleted()==taskObject.isCompleted() ? true:false;
                if(!completedSame) task.setCompleted(taskObject.isCompleted());
                boolean sameName = task.getName().equals(taskObject.getName())?true : false;
                if(!sameName) task.setName(taskObject.getName());
                boolean sameDescription = task.getDescription().equals(taskObject.getDescription())? true:false;
                if(!sameDescription) task.setDescription(taskObject.getDescription());
                return this.taskRepository.save(task);
            }
        }else{
            throw new InformationNotFoundException("Task doesn't exist to update");
        }

    }

    @DeleteMapping(path="/tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        System.out.println("delete this task " + taskId);
        Optional foundTask = taskRepository.findById(taskId);
        if(foundTask.isPresent())
        {
            taskRepository.deleteById(taskId);
            System.out.println("THis task is deleted " + taskId);
        }
        else
        {
            throw new InformationNotFoundException("THis id does not exist");
        }

    }

}
