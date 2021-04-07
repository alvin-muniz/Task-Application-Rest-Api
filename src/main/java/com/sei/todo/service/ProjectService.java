package com.sei.todo.service;

import com.sei.todo.exceptions.InformationExistsException;
import com.sei.todo.exceptions.InformationNotFoundException;
import com.sei.todo.model.Project;
import com.sei.todo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getProjects() {
        return this.projectRepository.findAll();
    }

    public Project getProject(Long projectId) {
        Optional<Project> foundProject = this.projectRepository.findById(projectId);
        if (foundProject.isPresent())
            return foundProject.get();
        else
            throw new InformationNotFoundException("This project does not exists with id " + projectId);
    }

    public Project createProject(Project projectObject) {
        System.out.println("Creating a new project");
        Optional<Project> project = this.projectRepository.findByName(projectObject.getName());
        if (project.isPresent())
            throw new InformationExistsException("No need to create another one");
        else
            return this.projectRepository.save(projectObject);
    }

    public Project updateProject(Long projectId, Project projectObject) {
        System.out.println("Updating a Project with ID " + projectId);

        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            if (project.get().getName().equals(projectObject.getName())) {
                throw new InformationExistsException("No updates here");
            } else {
                return projectRepository.save(projectObject);
            }
        } else {
            throw new InformationNotFoundException("This project does not exists");
        }
    }


    public void deleteProject(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            if (project.get().getTaskList() != null) {
                System.out.println("Cannot delete project that has tasks");
            } else {
                System.out.println("project deleted " + projectId);
                projectRepository.deleteById(projectId);
            }
        } else {
            throw new InformationNotFoundException("Cannot delete a category that does not exists");
        }
    }

}
