package com.example.conversormoeda;

public class Moeda {
	private String To, From;
	private double Rate;
	
	public Moeda(String To, String from, double rate){
		setTo(To);
		setFrom(from);
		setRate(rate);
	}
	
	public void setTo(String to) {
		this.To = to;
	}
	
	public void setRate(double rate) {
		this.Rate = rate;
	}
	
	public void setFrom(String from) {
		this.From = from;
	}
	
	public String getTo() {
		return To;
	}
	
	public double getRate() {
		return Rate;
	}
	
	public String getFrom() {
		return From;
	}
}
