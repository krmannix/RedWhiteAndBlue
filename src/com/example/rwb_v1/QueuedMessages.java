package com.example.rwb_v1;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class QueuedMessages {

	private List<ParseObject> messages;
	private boolean noError;
	private TextView messageBoard;
	private int currentMessage = 0;
	private boolean noClick = false;
	private Context context;
	private int approveLimit;
	private GetMessages queue = new GetMessages();

	QueuedMessages(TextView messageBoard, Context context, int approveLimit) {
		this.messageBoard = messageBoard;
		this.context = context;
		this.approveLimit = approveLimit;
		this.messages = new ArrayList<ParseObject>();
		getTenMessages();
	}
	
	private void getTenMessages() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
		query.whereEqualTo("status", 0);
		query.whereNotEqualTo("RWBuser", ParseUser.getCurrentUser().getUsername());
		query.whereNotEqualTo("first_rate_user", ParseUser.getCurrentUser().getUsername());
		query.whereNotEqualTo("second_rate_user", ParseUser.getCurrentUser().getUsername());
		query.setLimit(10);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> incomingMessages, ParseException e) {
				if (e == null) {
					queue.setQueue(incomingMessages);
					noError = true;
					if (numMessages() == 0) {
						generateAlert("There are no messages to show. Check back soon.");
						noClick = false;
					}
					else {
						generateMessage();
						noClick = false;
					}
				} else {
					Log.w("RWB error", e);
					noError = false;
					generateAlert("There was an error with the server. Please try again later.");
					noClick = true;
				}
			}
		});
		
		setQueue();
	}
	
	private void setQueue() {
		this.messages = queue.getQueue();
	}

	public boolean noError() {
		return noError;
	}

	public int numMessages() { 
		if (this.messages != null) 
			return this.messages.size();
		 else 
			return 0;
	}
	
	public void generateMessage() {
		if (currentMessage < (numMessages())) {
			messageBoard.setText(this.messages.get(currentMessage).getString("message"));			
			currentMessage++;
		} else {
			messages = null;
			currentMessage = 0;
			getTenMessages();
		}	
	}
	
	private void generateAlert(String message) {
		new GeneralAlert(context, message).showAlert();
	}

	public void submitRating(double currentRating) {
		if (numMessages() == 0) {
			// Don't do anything
		} else {
			this.messages.get(currentMessage-1).put("newRating", currentRating);
			this.messages.get(currentMessage-1).saveInBackground();	
		}
	}
	
}
