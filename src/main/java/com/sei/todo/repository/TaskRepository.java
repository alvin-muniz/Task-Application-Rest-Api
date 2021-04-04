package com.sei.todo.repository;

import com.sei.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    //this is spring boot magic, essentially you can query directly with the properties!
    //saves so much time for writing code
    Optional<Task> findByTitle(String taskName);
}
