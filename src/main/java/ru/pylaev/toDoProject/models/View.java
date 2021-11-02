package ru.pylaev.toDoProject.models;

public class View {
    private String message;
    private String answer;

    public View (String message, String answer) {
        this.message = message;
        this.answer = answer;
    }

    public String getMessage ( ) {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getAnswer ( ) {
        return answer;
    }

    public void setAnswer (String answer) {
        this.answer = answer;
    }

}
