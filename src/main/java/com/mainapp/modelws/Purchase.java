package com.mainapp.modelws;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import com.mainapp.model.Link;
import com.mainapp.model.Schedule;

public class Purchase {

    private int id;
    private Schedule schedule;
    private User client;
    private double price;
    private int status;
    private Date createdAt;
    private int account;
    private int agency;

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

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	public String getPriceVerbose() {
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
		return " " + moneyFormat.format(this.price);
	}
    
    public int getStatus() {
        return status;
    }

    public String getStatusVerbose() {
    	if(this.status == Purchase.EFFECTED) {
    		return "Compra efetuada";
    	}
    	return "Compra cancelada";
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
    
    public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getAgency() {
		return agency;
	}

	public void setAgency(int agency) {
		this.agency = agency;
	}

	public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public int getEffectedPurchase() {
    	return Purchase.EFFECTED;
    }
    
    public int getCanceledPurchase() {
    	return Purchase.CANCELED;
    }
}
