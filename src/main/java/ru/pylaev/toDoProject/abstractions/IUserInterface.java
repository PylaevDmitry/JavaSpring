package ru.pylaev.toDoProject.abstractions;

public interface IUserInterface{
    String askInput (String message);
    void show (String message);
    boolean isRunning ( );
}