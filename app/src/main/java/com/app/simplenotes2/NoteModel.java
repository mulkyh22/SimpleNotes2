package com.app.simplenotes2;

import java.sql.Timestamp;

public class NoteModel {
    String title;
    String description;
    String createdTime;
    String uid;
    private String key;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public NoteModel(String title, String description, String createdTime, String uid) {
        this.title = title;
        this.description = description;
        this.createdTime = createdTime;
        this.uid = uid;
    }

    public NoteModel(){

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() { return description; }

    public String getCreatedTime() { return createdTime; }

    public String getUid() { return uid; }
}

//Signature :
//10120146 - Irshal Mulky H - IF4