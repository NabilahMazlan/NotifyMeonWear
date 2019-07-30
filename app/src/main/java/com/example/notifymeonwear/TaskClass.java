package com.example.notifymeonwear;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class TaskClass implements Serializable {
    private int seconds;
    private String name, description, id;

    public TaskClass(int seconds, String name, String description) {
        this.seconds = seconds;
        this.name = name;
        this.description = description;
    }

    public TaskClass() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
