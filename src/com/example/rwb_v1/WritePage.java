package com.example.rwb_v1;

import java.sql.Date;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class WritePage extends Activity {

	EditText write;
	ImageView learn_more, send, yes, no, back, q_button;
	AlertDialog sent_message;
	Context context = this;
	Intent goToSplashPage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_page);
		
		
		// Build the alert dialogs
		AlertDialog.Builder alert_builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View sent_message_dialog_view = inflater.inflate(R.layout.sent_message_dialog, null);
		alert_builder.setView(sent_message_dialog_view);
		sent_message = alert_builder.create();
		
		// Define views
		yes = (ImageView) sent_message_dialog_view.findViewById(R.id.send_another_button);
		no = (ImageView) sent_message_dialog_view.findViewById(R.id.send_another_no_button);
		write = (EditText) findViewById(R.id.message_input);
		learn_more = (ImageView) findViewById(R.id.learn_more);
		send = (ImageView) findViewById(R.id.send);
		back = (ImageView) findViewById(R.id.arrow_back);
		q_button = (ImageView) findViewById(R.id.question);
		
		// Define Intents
		goToSplashPage = new Intent(context, SplashPage.class);
		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(goToSplashPage);				
			}
		});
		
		q_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new GeneralAlert(context, "Insert Info about Write Page").showAlert();
			}
		});
		
		learn_more.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(goToSplashPage);
			}
		});
		
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sent_message.cancel();
				write.setText("");
			}
		});
		
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				sent_message.cancel();
				write.setText("");
			}
		});
		
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (write.getText().toString().trim().equals("")) 
					Toast.makeText(context, "Please write a message", Toast.LENGTH_LONG).show();
				else if (write.getText().toString().trim().length() < 30)
					Toast.makeText(context, "Please write a longer message", Toast.LENGTH_LONG).show();
				else {
					ParseUser currentUser = ParseUser.getCurrentUser();
					ParseObject message = new ParseObject("message");
					TrackStats.incrementSentMessages(currentUser);
					/*
					 * A message shoud have the user name of the current user
					 * the message
					 * rating
					 * # of times rated
					 * status	(-1 is denied, 0 is pending, 1 is approved)			 
					 * time created (done by Parse)
					 */
					message.put("RWBuser", currentUser.getUsername());
					message.put("message", write.getText().toString());
					message.put("rating", 0.0);
					message.put("timesRated", 0);
					message.put("status", 0);
					message.saveInBackground();
					sent_message.show();				
				}
			}
		});
		
	}
	
}
