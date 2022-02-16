package ru.pylaev.toDoProject.pl.presenters;

public interface Presenter {
    String askInput (String message);
    void show (String message);
    boolean isRunning ( );
}