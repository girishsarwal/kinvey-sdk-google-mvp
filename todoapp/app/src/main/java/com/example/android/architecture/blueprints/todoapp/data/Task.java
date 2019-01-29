package com.example.android.architecture.blueprints.todoapp.data;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyFile;
import com.kinvey.java.model.KinveyMetaData;

public class Task extends KinveyFile {

    @Key
    private String subject;
    @Key
    private String description;
    @Key
    private String completed;
    @Key("_kmd")
    private KinveyMetaData meta;
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public boolean isActive(){
        return getCompleted() != "false";
    }
    public boolean isCompleted(){
        return getCompleted() != "false";
    }

    public String getTitle(){
        if(subject != "") {
            return subject;
        } else return description;
    }


    public Task() {
    }

    public Task(String subject, String description) {
        this.subject = subject;
        this.description = description;
        this.completed = "false";
    }

    public Task(String id, String subject, String description) {
        this(subject, description);
        this.setId(id);
    }
}