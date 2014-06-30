package com.example.rwb_v1;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class RatePage extends Activity {
	
	ImageView skip, submit, confirm_button, arrow_back, deny_button, q_button;
	TextView message, dialog_title, dialog_text;
	QueuedMessages queuedMessages;
	AlertDialog confirm_dialog;
	Context context = this;
	Intent goToSplashPage;
	RatingBar stars;
	int messagesRatedInRow;
	static List<ParseObject> queue;
	double currentRating = 3;
	int approveLimit = 3; // This can be changed based on how harsh we want to approve messages
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approve_message);
		messagesRatedInRow = 0;
		AlertDialog.Builder login_dialog_builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rate_dialog_view = inflater.inflate(R.layout.rate_dialog, null);
		login_dialog_builder.setView(rate_dialog_view);
		confirm_dialog = login_dialog_builder.create();
		
		// Define dialog views
		confirm_button = (ImageView) rate_dialog_view.findViewById(R.id.confirm);
		deny_button = (ImageView) rate_dialog_view.findViewById(R.id.deny);
		dialog_title = (TextView) rate_dialog_view.findViewById(R.id.rate_dialog_title);
		dialog_text = (TextView) rate_dialog_view.findViewById(R.id.rate_dialog_more); 
		
		// Define views
		skip = (ImageView) findViewById(R.id.skip);
		submit = (ImageView) findViewById(R.id.submit);
		message = (TextView) findViewById(R.id.message_text);
		arrow_back = (ImageView) findViewById(R.id.arrow_back);
		q_button = (ImageView) findViewById(R.id.question);
		
		// Define Intents
		goToSplashPage = new Intent(context, SplashPage.class);
		
		// Rating stars
		stars = (RatingBar) findViewById(R.id.star_rating);
		stars.setOnRatingBarChangeListener(new OnRatingBarChangeListener(){
	        @Override
	        public void onRatingChanged(RatingBar ratingBar, float rating,
	                boolean fromUser) {
	        	currentRating = rating;
	        }
	    }); 
		
		queue = new ArrayList<ParseObject>();
		queuedMessages = new QueuedMessages(message, context, approveLimit);
			
		// Click listeners
		arrow_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				messagesRatedInRow = 0;
				startActivity(goToSplashPage);				
			}
		});
		
		q_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new GeneralAlert(context, "Insert Info about Rate Page").showAlert();
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog_title.setText("Submit Rating?");
				confirm_dialog.show();
			}
		});
		
		skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog_title.setText("Skip Message?");
				confirm_dialog.show();
			}
		});
		
		confirm_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitRating();
				generateMessage();
				TrackStats.incrementRatedMessages(ParseUser.getCurrentUser());
				confirm_dialog.dismiss();
				stars.setRating((float) 3.0);
				messagesRatedInRow++;
				TrackStats.checkStamina(ParseUser.getCurrentUser(), messagesRatedInRow);
			}
		}); 
		
		deny_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				generateMessage();
				confirm_dialog.dismiss();
			}
		});	
	}
	
	public static void setQueue(List<ParseObject> list) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i);
			queue.add(list.get(i));
		}
	}
	
	protected void submitRating() {
		queuedMessages.submitRating(currentRating);
	}

	protected void generateMessage() {
		queuedMessages.generateMessage();
	}

}
