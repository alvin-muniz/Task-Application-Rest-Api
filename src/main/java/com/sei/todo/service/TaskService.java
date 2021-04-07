package com.sei.todo.service;

import com.sei.todo.exceptions.InformationExistsException;
import com.sei.todo.exceptions.InformationNotFoundException;
import com.sei.todo.model.Task;
import com.sei.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks(){
        System.out.println("service call getTasks!");
        return taskRepository.findAll();
    }

    public Task getTask(Long taskId){
        System.out.println(("Service calling getTask!"));
        Optional<Task> foundTask = this.taskRepository.findById(taskId);
        if(foundTask.isPresent())
        {
            return foundTask.get();
        }else
            throw new InformationNotFoundException("This task does not exists");
    }

    public Task createTask(Task taskObject)
    { System.out.println("Service creating a new category");
        Optional<Task> task = this.taskRepository.findByTitle(taskObject.getTitle());
        if(!(task.isPresent()))
            return this.taskRepository.save(taskObject);
        else
            throw new InformationExistsException("There is already a note wtih that title!!");
    }

    public Task updateTask(Long taskId,Task taskObject){
        System.out.println("Service call this task was updated ID: " + taskId +
                "Update info: " + taskObject.toString());

        Optional<Task> foundTask = this.taskRepository.findById(taskId);
        if(foundTask.isPresent())
        {
            System.out.println(taskObject.toString());
            foundTask.toString();
            boolean sameObject = foundTask.equals(taskObject);
            foundTask.toString();
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

    public Task deleteTask(Long taskId){

        Task foundTask = this.taskRepository.findById(taskId).get();
        if(foundTask != null) {
            this.taskRepository.delete(foundTask);
            return foundTask;
        }
        else
            throw new InformationNotFoundException("This is not found to be deleted");
    }
}


