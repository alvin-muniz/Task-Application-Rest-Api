package com.sei.todo.factory;

public abstract class ComponentFactory {

    private String theme;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public ComponentFactory(String theme){
        this.theme = theme;
    }
    abstract Navigation createNavigation();
    abstract ScrollBar createScrollBar();

}
