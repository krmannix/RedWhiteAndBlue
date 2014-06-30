package com.example.rwb_v1;

import java.util.Date;

public class MessageClass {
	
	public String message;
	public int status;
	public Date date_sent;
	double rating = 0;
	
	MessageClass(String message, int status, Date date_sent) {
		this.message = message;
		this.status = status;
		this.date_sent = date_sent;
	}
	
	MessageClass(String message, int status, Date date_sent, double rating) {
		this.message = message;
		this.status = status;
		this.date_sent = date_sent;
		this.rating = rating;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	
	

	public static class Status {	
		static int PENDING = 0;
		static int APPROVED = 1;
		static int DENIED = -1;		
	}




	public String getDateString() {
		// TODO Auto-generated method stub
		return this.date_sent.toString();
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public float getRating() {
		return (float) this.rating;
	}
	
}


