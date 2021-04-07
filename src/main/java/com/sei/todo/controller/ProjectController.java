package com.sei.todo.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.sei.todo.exceptions.InformationExistsException;
import com.sei.todo.exceptions.InformationNotFoundException;
import com.sei.todo.model.Project;
import com.sei.todo.model.Task;
import com.sei.todo.repository.ProjectRepository;
import com.sei.todo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path = "/projects")
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping(path = "/projects/{projectId}")
    public Project getProject(@PathVariable Long projectId) {
        return projectService.getProject(projectId);
    }

    @PostMapping(path = "/projects")
    public Project createProject(@RequestBody Project projectObject) {
        return projectService.createProject(projectObject);
    }

    @PutMapping(path = "/projects/{projectId}")
    public Project updateProject(@PathVariable Long projectId,
                                 @RequestBody Project projectObject) {
        return projectService.updateProject(projectId, projectObject);
    }

    @DeleteMapping(path = "/projects/{projectId}")
    public void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }

    @PostMapping(path = "/projects/{projectId}/tasks")
    public List<Task> getProjectTasks(@PathVariable Long projectId){
        return new ArrayList<>();
    }

    @PostMapping(path = "/projects/{projectId}/tasks/{taskId}")
    public Task getProjectTask(@PathVariable Long projectId,
                               @PathVariable Long taskId){
        return new Task();
    }

    @PostMapping(path = "/projects/{projectId}/tasks")
    public void createProjectTask(@PathVariable Long projectId){

    }

    @PutMapping(path = "/projects/{projectId}/tasks/{taskId}")
    public void updateProjectTask(@PathVariable Long projectId,
                                  @PathVariable Long taskId,
                                  @RequestBody Task taskObject){

    }

    @DeleteMapping(path = "/projects/{projectId}/tasks/{taskId}")
    public void deleteProjectTask(@PathVariable Long projectId,
                                  @PathVariable Long taskId)
    {

    }
}
