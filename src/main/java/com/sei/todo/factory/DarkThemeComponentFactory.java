package com.sei.todo.factory;

public class DarkThemeComponentFactory extends ComponentFactory
{

    public DarkThemeComponentFactory(){
        super("Dark");
    }

    @Override
    Navigation createNavigation() {
        return new Navigation(this.getTheme());
    }

    @Override
    ScrollBar createScrollBar() {
        return new ScrollBar(this.getTheme());
    }
}
