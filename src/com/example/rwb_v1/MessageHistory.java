package com.example.rwb_v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.rwb_v1.LeaderboardPageAdapter.LeaderboardType;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MessageHistory extends Activity {
	
	ImageView back;
	Intent goToProfilePage;
	Context context = this;
	ListView message_list;
	List<ParseObject> allMessages;
	ProgressBar loading;
	TextView empty_list;
	AlertDialog.Builder builder;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_history);
		
		back = (ImageView) findViewById(R.id.arrow_back);
		goToProfilePage = new Intent(context, ProfilePage.class);
		goToProfilePage.putExtra("username", getIntent().getStringExtra("username"));
		message_list = (ListView) findViewById(R.id.message_history_list);
		loading = (ProgressBar) findViewById(R.id.loading_message_history);
		empty_list = (TextView) findViewById(R.id.empty_results_message_history);
		// Create message list
		createMessageList();
		allMessages = new ArrayList<ParseObject>();
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(goToProfilePage);
			}
		});
		
		message_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
					View more_info_msg = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
							.inflate(R.layout.message_more_info, arg0, false);
					builder = new AlertDialog.Builder(context);
					((TextView) more_info_msg.findViewById(R.id.message_more_info_message)).setText(allMessages.get(pos).getString("message"));	
					((TextView) more_info_msg.findViewById(R.id.message_more_info_created)).setText(allMessages.get(pos).getCreatedAt().toString());
					((RatingBar) more_info_msg.findViewById(R.id.messge_more_info_star)).setRating((float) allMessages.get(pos).getDouble("rating"));
					builder.setView(more_info_msg);
					builder.create().show();
			}
		});
	}



	private void createMessageList() {
		empty_list.setVisibility(View.INVISIBLE);
		loading.setVisibility(View.VISIBLE);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
		if (getIntent().hasExtra("username")) { 
			query.whereEqualTo("RWBuser", getIntent().getStringExtra("username"));
			query.whereEqualTo("status", 1);
			Log.v("xxx", "Intent " + getIntent().hasExtra("username"));
		} else
			query.whereEqualTo("RWBuser", ParseUser.getCurrentUser().getUsername());
		query.addDescendingOrder("status");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					populateList(messages);
				} else {
					loading.setVisibility(View.INVISIBLE);
					Toast.makeText(context, "There's been an error retrieving requests", Toast.LENGTH_LONG).show();
				}
			}
		});
	}



	protected void populateList(List<ParseObject> messages) {
		allMessages = messages;
		MessageHistoryAdapter adapter = new MessageHistoryAdapter(context, allMessages);
		loading.setVisibility(View.INVISIBLE);
		if (allMessages.size() == 0)
			empty_list.setVisibility(View.VISIBLE);
		message_list.setAdapter(adapter);
	}

}
