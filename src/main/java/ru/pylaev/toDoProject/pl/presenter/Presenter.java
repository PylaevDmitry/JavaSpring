package ru.pylaev.toDoProject.pl.presenter;

public interface Presenter {
    String askInput (String message);
    void show (String message);
    boolean isRunning ( );
}