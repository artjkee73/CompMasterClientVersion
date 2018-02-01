package com.androiddev.artemqa.compmasterclientversion.models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Price extends RealmObject {
    @PrimaryKey @Required private int id;
    @Required private String namePosition;
    @Required private String descriptionPosition;
    @Required private int timeExecuting;
    @Required private int costPosition;
    public Price(){

    }

    public Price(int id,String namePosition,String descriptionPosition,int timeExecuting, int costPosition){
        this.id = id;
        this.namePosition = namePosition;
        this.descriptionPosition = descriptionPosition;
        this.timeExecuting = timeExecuting;
        this.costPosition = costPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamePosition() {
        return namePosition;
    }

    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }

    public String getDescriptionPosition() {
        return descriptionPosition;
    }

    public void setDescriptionPosition(String descriptionPosition) {
        this.descriptionPosition = descriptionPosition;
    }

    public double getCostPosition() {
        return costPosition;
    }

    public void setCostPosition(int costPosition) {
        this.costPosition = costPosition;
    }
    public int getTimeExecuting() {
        return timeExecuting;
    }

    public void setTimeExecuting(int timeExecuting) {
        this.timeExecuting = timeExecuting;
    }
}