package com.example.rwb_v1;

import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class LeaderboardsPage extends Activity {
	
	Button most_active, top_rated, messaging, rating, searchfriends;
	EditText friends;
	ImageView arrow_back, q_button;
	ListView leader_listview;
	Context context = this;
	LeaderboardPageAdapter userAdapter;
	LeaderboardMessAdapter messAdapter;
	Intent goToSplashPage, goToProfilePage;
	ProgressBar loader;
	TextView more_info_msg_text, more_info_created, no_results;
	RatingBar more_info_stars;
	List<ParseObject> messages;
	List<ParseUser> users;
	int currentClick;
	AlertDialog.Builder builder;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderboard);
		loader = (ProgressBar) findViewById(R.id.loading);
		loader.setVisibility(View.VISIBLE);
		most_active = (Button) findViewById(R.id.most_active);
		top_rated = (Button) findViewById(R.id.top_rated);
		messaging = (Button) findViewById(R.id.messaging);
		rating = (Button) findViewById(R.id.rating);
		leader_listview = (ListView) findViewById(R.id.leader_listview);
		leader_listview.setSelector(android.R.color.transparent);
		arrow_back = (ImageView) findViewById(R.id.arrow_back);
		q_button = (ImageView) findViewById(R.id.question);
		searchfriends = (Button) findViewById(R.id.search_friends_button);
		friends = (EditText) findViewById(R.id.search_friends);
		no_results = (TextView) findViewById(R.id.empty_results);
		goToSplashPage = new Intent(this, SplashPage.class);
		goToProfilePage = new Intent(this, ProfilePage.class);
		currentClick = LeaderboardType.TOP_RATED;
		updateLeaderboard(LeaderboardType.TOP_RATED);
		
		// Click Listeners
		arrow_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(goToSplashPage);
			}			
		});
		
		q_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new GeneralAlert(context, "Insert Info about Leaderboards Page").showAlert();
			}
		});
		
		leader_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if (currentClick == LeaderboardType.TOP_RATED || currentClick == LeaderboardType.ACTIVE) {
					View more_info_msg = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
							.inflate(R.layout.message_more_info, arg0, false);
					builder = new AlertDialog.Builder(context);
					((TextView) more_info_msg.findViewById(R.id.message_more_info_message)).setText(messages.get(pos).getString("message"));	
					((TextView) more_info_msg.findViewById(R.id.message_more_info_created)).setText(messages.get(pos).getCreatedAt().toString());
					((RatingBar) more_info_msg.findViewById(R.id.messge_more_info_star)).setRating((float) messages.get(pos).getDouble("rating"));
					builder.setView(more_info_msg);
					builder.create().show();
				} else {
					goToProfilePage.putExtra("username", users.get(pos).getUsername());
					startActivity(goToProfilePage);
				}
			}
		});
		

		searchfriends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				leader_listview.setAdapter(null);
				no_results.setVisibility(View.INVISIBLE);
				loader.setVisibility(View.VISIBLE);
				ParseQuery<ParseUser> userquery = ParseUser.getQuery();
				userquery.setLimit(20);
				userquery.whereStartsWith("username", friends.getText().toString());
				userquery.findInBackground(new FindCallback<ParseUser>() {
					@Override
					public void done(List<ParseUser> objects, ParseException e) {
						// How do we search?
						userAdapter = new LeaderboardPageAdapter(context, objects,
								LeaderboardPageAdapter.LeaderboardType.MESSAGING);
						leader_listview.setAdapter(userAdapter);
						loader.setVisibility(View.INVISIBLE);
						leader_listview.setVisibility(View.VISIBLE);
						userAdapter.notifyDataSetChanged();
						users = objects;
						if (objects.size() == 0)
							no_results.setVisibility(View.VISIBLE);
					}
				});
			}
		});
		
		most_active.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				leader_listview.setAdapter(null);
				loader.setVisibility(View.VISIBLE);
				no_results.setVisibility(View.INVISIBLE);
				currentClick = LeaderboardType.ACTIVE;
				updateLeaderboard(LeaderboardType.ACTIVE);
			}		
		});
		
		top_rated.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				leader_listview.setAdapter(null);
				loader.setVisibility(View.VISIBLE);
				no_results.setVisibility(View.INVISIBLE);
				currentClick = LeaderboardType.TOP_RATED;
				updateLeaderboard(LeaderboardType.TOP_RATED);
			}		
		});
		
		messaging.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				leader_listview.setAdapter(null);
				loader.setVisibility(View.VISIBLE);
				no_results.setVisibility(View.INVISIBLE);
				currentClick = LeaderboardType.MESSAGING;
				updateLeaderboard(LeaderboardType.MESSAGING);
			}		
		});
		
		rating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				leader_listview.setAdapter(null);
				loader.setVisibility(View.VISIBLE);
				no_results.setVisibility(View.INVISIBLE);
				currentClick = LeaderboardType.RATING;
				updateLeaderboard(LeaderboardType.RATING);
			}		
		});
		
	}

	private void getUsers(int type) {
		ParseQuery<ParseUser> userquery = ParseUser.getQuery();
		userquery.setLimit(20);
		ParseQuery<ParseObject> messagequery = ParseQuery.getQuery("message");
		messagequery.setLimit(20);
		if (type == LeaderboardType.ACTIVE) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			//messagequery.whereNotEqualTo("status", 0);
			messagequery.whereNotEqualTo("rating", 0);
			messagequery.whereGreaterThanOrEqualTo("createdAt", cal.getTime());
			messagequery.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					messAdapter = new LeaderboardMessAdapter(context, objects, 
							LeaderboardPageAdapter.LeaderboardType.ACTIVE);
					leader_listview.setAdapter(messAdapter);
					loader.setVisibility(View.INVISIBLE);
					leader_listview.setVisibility(View.VISIBLE);
			        messAdapter.notifyDataSetChanged();
			        messages = objects;
			        if (objects.size() == 0)
			        	no_results.setVisibility(View.VISIBLE);
				}
			});
		} else if (type == LeaderboardType.TOP_RATED) {
			messagequery.addDescendingOrder("rating");
			messagequery.whereEqualTo("timesRated", 3);
			messagequery.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					messAdapter = new LeaderboardMessAdapter(context, objects, 
							LeaderboardPageAdapter.LeaderboardType.TOP_RATED);
					leader_listview.setAdapter(messAdapter);
					loader.setVisibility(View.INVISIBLE);
					leader_listview.setVisibility(View.VISIBLE);
			        messAdapter.notifyDataSetChanged();
			        messages = objects;
			        if (objects.size() == 0)
			        	no_results.setVisibility(View.VISIBLE);
				}
			});
		} else if (type == LeaderboardType.MESSAGING) {
			userquery.addDescendingOrder("messagesSent");
			userquery.findInBackground(new FindCallback<ParseUser>() {
				@Override
				public void done(List<ParseUser> objects, ParseException e) {
					userAdapter = new LeaderboardPageAdapter(context, objects,
							LeaderboardPageAdapter.LeaderboardType.MESSAGING);
					leader_listview.setAdapter(userAdapter);
					loader.setVisibility(View.INVISIBLE);
					leader_listview.setVisibility(View.VISIBLE);
					userAdapter.notifyDataSetChanged();
					users = objects;
					if (objects.size() == 0)
			        	no_results.setVisibility(View.VISIBLE);
				}
			});
		} else if (type == LeaderboardType.RATING) {
			userquery.addDescendingOrder("avgMessageRating");
			userquery.findInBackground(new FindCallback<ParseUser>() {
				@Override
				public void done(List<ParseUser> objects, ParseException e) {
					userAdapter = new LeaderboardPageAdapter(context, objects,
							LeaderboardPageAdapter.LeaderboardType.RATING);
					leader_listview.setAdapter(userAdapter);
					loader.setVisibility(View.INVISIBLE);
					leader_listview.setVisibility(View.VISIBLE);
					userAdapter.notifyDataSetChanged();
					users = objects;
					if (objects.size() == 0)
			        	no_results.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	protected void updateLeaderboard(int type) {
		getUsers(type);
	}

}
