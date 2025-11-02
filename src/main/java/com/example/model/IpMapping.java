package com.example.model;



public class IpMapping {
    private String id;
    private long from;
    private long to;
    private String country;
    private String state;


    public IpMapping() {
    }

    public IpMapping(long from, long to, String country, String state) {
        this.from = from;
        this.to = to;
        this.country = country;
        this.state = state;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}