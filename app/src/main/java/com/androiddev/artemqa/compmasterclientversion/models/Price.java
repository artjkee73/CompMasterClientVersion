package com.androiddev.artemqa.compmasterclientversion.models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Price extends RealmObject {
    @PrimaryKey @Required private Integer id;
    @Required private String namePosition;
    @Required private String descriptionPosition;
    @Required private Integer timeExecuting;
    @Required private Integer costPosition;
    public Price(){

    }

    public Price(Integer id,String namePosition,String descriptionPosition,Integer timeExecuting, Integer costPosition){
        this.id = id;
        this.namePosition = namePosition;
        this.descriptionPosition = descriptionPosition;
        this.timeExecuting = timeExecuting;
        this.costPosition = costPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCostPosition() {
        return costPosition;
    }

    public void setCostPosition(Integer costPosition) {
        this.costPosition = costPosition;
    }
    public Integer getTimeExecuting() {
        return timeExecuting;
    }

    public void setTimeExecuting(Integer timeExecuting) {
        this.timeExecuting = timeExecuting;
    }
}