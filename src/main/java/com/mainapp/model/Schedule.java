package com.mainapp.model;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Schedule extends Hateoas {

	private int id;
	private Date startAt;
	private Date endAt;
	private double price;
	private int totalSeats;
	private int availableSeats;
	private City startDestination;
	private City endDestination;
	
	public Schedule() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public double getPrice() {
		return price;
	}

	public String getPriceVerbose() {
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
		return " " + moneyFormat.format(this.price);
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public City getStartDestination() {
		return startDestination;
	}

	public void setStartDestination(City startDestination) {
		this.startDestination = startDestination;
	}

	public City getEndDestination() {
		return endDestination;
	}

	public void setEndDestination(City endDestination) {
		this.endDestination = endDestination;
    }

}
