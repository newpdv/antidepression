package com.example.antidepression.db;

public class Pleasure {
    private final long id;
    private final String text;

    public Pleasure(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
