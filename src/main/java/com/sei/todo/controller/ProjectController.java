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

    @GetMapping(path = "/projects/{projectId}/tasks")
    public List<Task> getProjectTasks(@PathVariable Long projectId){
        return projectService.getProjectTasks(projectId);
    }

    @GetMapping(path = "/projects/{projectId}/tasks/{taskId}")
    public Task getProjectTask(@PathVariable Long projectId,
                               @PathVariable Long taskId){
        return projectService.getProjectTask(projectId,taskId);
    }

    @PostMapping(path = "/projects/{projectId}/tasks")
    public Task createProjectTask(@PathVariable Long projectId,
                                  @RequestBody Task taskObject){
            return projectService.createProjectTask(projectId, taskObject);
    }

    @PutMapping(path = "/projects/{projectId}/tasks/{taskId}")
    public Task updateProjectTask(@PathVariable Long projectId,
                                  @PathVariable Long taskId,
                                  @RequestBody Task taskObject){

        return projectService.updateProjectTask(projectId, taskId,taskObject);

    }

    @DeleteMapping(path = "/projects/{projectId}/tasks/{taskId}")
    public void deleteProjectTask(@PathVariable Long projectId,
                                  @PathVariable Long taskId)
    {
                projectService.deleteProjectTask(projectId,taskId);
    }
}
