package com.androiddev.artemqa.compmasterclientversion.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by artemqa on 30.01.2018.
 */

public class Order extends RealmObject {

    @PrimaryKey
    private Integer id;
    @Required
    private String status;
    private Client client;
    @Required private Date dateOrder;
    private Employee employee;
    @Required private RealmList<Price> priceListPosition;
    private String descriptionOrder;

    public Order(){}

    public Order (int id , String status,Client client, Date dateOrder){
        this.id = id;
        this.status = status;
        this.client = client;
        this.dateOrder = dateOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public RealmList<Price> getPriceListPosition() {
        return priceListPosition;
    }

    public void setPriceListPosition(RealmList<Price> priceListPosition) {
        this.priceListPosition = priceListPosition;
    }

    public String getDescriptionOrder() {
        return descriptionOrder;
    }

    public void setDescriptionOrder(String descriptionOrder) {
        this.descriptionOrder = descriptionOrder;
    }

}