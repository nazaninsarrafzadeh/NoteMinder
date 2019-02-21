package com.example.nazanin.notepad.model.dto;

public class CheckList {

    private boolean done;
    private int id;
    private String whatToDo;
    private String DateAndTime;
    private String date;
    private String time;
    private int category;

    public CheckList(){

    }

    public CheckList(boolean done, int id, String whatToDo, String dateAndTime, String date, String time,int category) {
        this.done = done;
        this.id = id;
        this.whatToDo = whatToDo;
        DateAndTime = dateAndTime;
        this.date = date;
        this.time = time;
        this.category = category;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhatToDo() {
        return whatToDo;
    }

    public void setWhatToDo(String whatToDo) {
        this.whatToDo = whatToDo;
    }

    public String getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        DateAndTime = dateAndTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
