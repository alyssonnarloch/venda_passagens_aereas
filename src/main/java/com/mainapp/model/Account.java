package com.mainapp.model;

public class Account {

    private int id;
    private int account;
    private int agency;
    private double balance;
    
    public static int OK = 1;
    public static int BALANCE_NOT_ENOUGH = 2;
    public static int INVALID_DATA = 3;
    
    public static int DEBT = 1;
    public static int CREDIT = 2;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
