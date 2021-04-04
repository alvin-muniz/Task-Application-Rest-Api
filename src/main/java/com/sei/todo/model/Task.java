package com.sei.todo.model;

public class Task {

    private Long id;
    private String title;
    private boolean isCompleted;
    private String description;

    public Task(Long id, String title, boolean isCompleted, String description)
    {
        this.id = id;
        this.title = title;
        this.isCompleted = isCompleted;
        this.description = description;
    }

    public Task(){}
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return isCompleted; }

    public void setCompleted(boolean completed) { isCompleted = completed; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString(){
        return "Task #" + this.getId() +
                "\nTitle" + this.getTitle() +
                "\n Description" + this.getDescription()
                + "Is Completed: " + this.isCompleted();
    }

}