package com.mainapp.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {

    @Id
    @GeneratedValue
	private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
	private User client;
	
    @Column(name = "start_destination_id")
	private int startDestinationId;
	
    @Column(name = "end_destination_id")
    private int endDestinationId;
	
    @Column(name = "start_at")
	private Date startAt;
	
    @Column(name = "end_at")
	private Date endAt;
	
	private double price;
	
	private int status;
	
	private int account;
	
	private int agency;
	
	@Column(name = "create_at")
	private Date createAt;
	
	@Column(name = "start_destination_name")
	private String startDestinationName;

	@Column(name = "end_destination_name")
	private String endDestinationName;
	
	public static int EFFECTED = 1;
	public static int CANCELED = 2;
	
	public Purchase() {
		
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public int getStartDestinationId() {
		return startDestinationId;
	}

	public void setStartDestinationId(int startDestinationId) {
		this.startDestinationId = startDestinationId;
	}

	public int getEndDestinationId() {
		return endDestinationId;
	}

	public void setEndDestinationId(int endDestinationId) {
		this.endDestinationId = endDestinationId;
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

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusVerbose() {
		if(this.getStatus() == Purchase.EFFECTED) {
			return "Efetuado";
		} else {
			return "Cancelado";
		}
	}
	
	public void setStatus(int status) {
		this.status = status;
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getStartDestinationName() {
		return startDestinationName;
	}

	public void setStartDestinationName(String startDestinationName) {
		this.startDestinationName = startDestinationName;
	}

	public String getEndDestinationName() {
		return endDestinationName;
	}

	public void setEndDestinationName(String endDestinationName) {
		this.endDestinationName = endDestinationName;
	}
	
	public String getPriceVerbose() {
		NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
		return " " + moneyFormat.format(this.price);
	}
}
