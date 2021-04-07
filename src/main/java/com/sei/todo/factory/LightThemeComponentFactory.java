package com.sei.todo.factory;

public class LightThemeComponentFactory extends ComponentFactory
{

    public LightThemeComponentFactory(){
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
