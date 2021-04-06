package com.sei.todo.controller;

import com.sei.todo.exceptions.InformationExistsException;
import com.sei.todo.exceptions.InformationNotFoundException;
import com.sei.todo.model.Project;
import com.sei.todo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class ProjectController {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @GetMapping(path="/projects")
    public List<Project> getProjects(){
        return this.projectRepository.findAll();
    }

    @GetMapping(path="/projects/{projectId}")
    public Project getProject(@PathVariable Long projectId){
        Optional<Project> foundProject = this.projectRepository.findById(projectId);
        if(foundProject.isPresent())
            return foundProject.get();
        else
            throw new InformationNotFoundException("This project does not exists with id " + projectId);
    }

    @PostMapping(path="/projects")
    public Project createProject(@RequestBody Project projectObject){
        System.out.println("Creating a new project");
        Optional<Project> project = this.projectRepository.findByName(projectObject.getName());
        if(project.isPresent())
            throw new InformationExistsException("No need to create another one");
        else
            return this.projectRepository.save(projectObject);
    }



}
