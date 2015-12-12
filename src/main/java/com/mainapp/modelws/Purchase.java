package com.mainapp.modelws;

import java.util.Date;
import java.util.List;

import com.mainapp.model.Link;
import com.mainapp.model.Schedule;

public class Purchase {

    private int id;
    private Schedule schedule;
    private Client client;
    private double price;
    private int status;
    private Date createdAt;

    private List<Link> links;

    public static int EFFECTED = 1;
    public static int CANCELED = 2;

    public Purchase() {
    	
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
