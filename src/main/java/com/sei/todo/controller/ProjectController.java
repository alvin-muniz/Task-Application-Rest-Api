package com.sei.todo.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
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

    @PutMapping(path = "/projects/{projectId}")
    public Project updateProject(@PathVariable Long projectId, @RequestBody Project projectObject){
        System.out.println("Updating a Project with ID " + projectId);
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isPresent()){
            if(project.get().getName().equals(projectObject.getName())){
                throw new InformationExistsException("No updates here");
            }else{
                  return projectRepository.save(projectObject);
            }
        }else{
            throw new InformationNotFoundException("This project does not exists");
        }
    }

    @DeleteMapping(path="/projects/{projectId}")
    public void deleteProject(@PathVariable Long projectId){
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isPresent())
        {
            System.out.println("project deleted " + projectId);
            projectRepository.deleteById(projectId);
        }
        else
        {
            throw new InformationNotFoundException("Cannot delete a category that does not exists");
        }
    }


}
