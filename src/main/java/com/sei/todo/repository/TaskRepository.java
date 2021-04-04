package com.sei.todo.repository;

import com.sei.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByName(String taskName);
}
