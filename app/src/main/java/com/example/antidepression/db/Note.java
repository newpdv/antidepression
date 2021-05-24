package com.example.antidepression.db;

public class Note {

    private long id;
    private String text;
    private int state;

    public Note(long id, String text, int state){
        this.id = id;
        this.text = text;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.text + ". Current state: " + this.state;
    }
}