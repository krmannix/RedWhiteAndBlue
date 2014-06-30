package com.example.rwb_v1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilePage extends Activity {
	
	ImageView back, message_history, q_button, background_rect;
	Context context = this;
	Intent goToSplashPage, goToMessageHistory;
	LinearLayout myGallery;
	String defPackage = "com.example.rwb_v1";
	int medalSize = 150;
	List<MessageClass> allMessages;
	ParseUser user;
	
	TextView rank_title, written, rated, total, username, no_badges;
	ImageView rankIcon;
	ProgressBar progress, loading, loading_badges;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.own_profile);
		
		username  = (TextView) findViewById(R.id.profile);
		rank_title = (TextView) findViewById(R.id.rank_text);
		written = (TextView) findViewById(R.id.profile_written);
		rated = (TextView) findViewById(R.id.profile_rated);
		total = (TextView) findViewById(R.id.profile_total);
		rankIcon = (ImageView) findViewById(R.id.current_rank_icon);
		progress = (ProgressBar) findViewById(R.id.profile_progress_bar);
		loading = (ProgressBar) findViewById(R.id.loading_profile);
		loading_badges = (ProgressBar) findViewById(R.id.loading_badges);
		background_rect = (ImageView) findViewById(R.id.background_rect);
		no_badges = (TextView) findViewById(R.id.no_badges);
		setInvisible();
		
		// Set all the object variables
		back = (ImageView) findViewById(R.id.arrow_back);
		message_history = (ImageView) findViewById(R.id.my_profile_message_history);
		q_button = (ImageView) findViewById(R.id.question);
		goToSplashPage = new Intent(context, SplashPage.class);
		goToMessageHistory = new Intent(context, MessageHistory.class);
		
		if (getIntent().hasExtra("username")) {
			goToMessageHistory.putExtra("username", getIntent().getStringExtra("username"));
			TrackStats.reconBadge(ParseUser.getCurrentUser());
			ParseUser.getQuery().whereEqualTo("username", getIntent().getStringExtra("username"))
			.findInBackground(new FindCallback<ParseUser>() {
				@Override
				public void done(List<ParseUser> objects, ParseException e) {
					if (e == null)
						setUpProfilePage(objects.get(0));
					else
						setUpProfilePage(ParseUser.getCurrentUser());
				}
			});
		} else {
			Parse.initialize(context, "a6YcVdnzUomdTpnfLFOYC5YImQmdlCfzkpNqtPZK", "5b09yCzGmh3db0WAPBSyMkt410dtjPLxYqkXZC5h"); 
			setUpProfilePage(ParseUser.getCurrentUser());
		}
		
		// Setting up the horizontal gallery
		myGallery = (LinearLayout)findViewById(R.id.mygallery);	
		
		// Click Listeners
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(goToSplashPage);
			}			
		});
		
		q_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new GeneralAlert(context, "Insert Info about Profile Page").showAlert();
			}
		});
		
		message_history.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(goToMessageHistory);
			}
		});
		
	}
	
	
	private void setUpProfilePage(ParseUser userIn) {
		try {
			userIn.fetch();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setBadges(userIn);
		username.setText(userIn.getUsername());
		rank_title.setText(Rank.getRankString(userIn.getInt("rank")));
		rankIcon.setImageResource(Rank.getRankResource(userIn.getInt("rank")));
		written.setText("Written: " + userIn.getInt("messagesSent"));
		rated.setText("Rated: " + userIn.getInt("messagesRated"));
		total.setText("Total " + (userIn.getInt("messagesSent")+userIn.getInt("messagesRated")));
		progress.setProgress(Rank.getLeftOverXP(userIn.getInt("XP")));
		setVisible();
	}


	private void setBadges(ParseUser userIn) {
		loading_badges.setVisibility(View.VISIBLE);
		Log.v("xxx","in setBadges");
		ParseQuery<ParseObject> badgeQuery = new ParseQuery<ParseObject>("badges");
		badgeQuery.clearAllCachedResults();
		badgeQuery.whereEqualTo("username", userIn.getUsername());
		badgeQuery.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (objects.size() == 0) {
					// Empty
				} else {
					boolean noBadges = true;
					for (int i = 0; i < BadgeContainer.getBadgeSize(); i++) {
						if (objects.get(0).getInt(BadgeContainer.getAllBadges().get(i).getParseCol()) != -1) {
							myGallery.addView(insertPhoto(BadgeContainer.getAllBadges().get(i), 
									objects.get(0).getInt(BadgeContainer.getAllBadges().get(i).getParseCol())));
							noBadges = false;
						}
					}
					loading_badges.setVisibility(View.INVISIBLE);
					if (noBadges)
						no_badges.setVisibility(View.VISIBLE);
				}
			}
		});
	}


	// Methods for horizontal gallery
	View insertPhoto(final Badge badge, int multiplier){
		 RelativeLayout layout = new RelativeLayout(context);
	     layout.setLayoutParams(new LayoutParams(medalSize, medalSize));
	     layout.setGravity(Gravity.CENTER);
	     
	     ImageView imageView = new ImageView(context);
	     imageView.setLayoutParams(new LayoutParams(medalSize - 15, medalSize - 15));
	     imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	     imageView.setImageResource(badge.getImageResource());
	     
	     layout.addView(imageView);
	     if (multiplier > 0) {
		     TextView textViewCorner = new TextView(context);
		     textViewCorner.setBackgroundResource(R.drawable.red_circle);
		     textViewCorner.setText("+" + badge.getMultiplier());
		     RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(medalSize/3, medalSize/3);
		     lp.setMargins(0, 0, 0, 10);
		     lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		     lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		     textViewCorner.setLayoutParams(lp);
		     textViewCorner.setTextColor(Color.WHITE);
		     textViewCorner.setGravity(Gravity.CENTER);
		     layout.addView(textViewCorner);
	     }
	     
	     layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new GeneralAlert(context, badge.getTitle(), badge.getDescription(), badge.getImageResource()).showAlert();
				}
		     });
	     return layout;
	    }

	private void setInvisible() {
		loading.setVisibility(View.VISIBLE);
		background_rect.setVisibility(View.INVISIBLE);
		username.setVisibility(View.INVISIBLE);
		rank_title.setVisibility(View.INVISIBLE);
		written.setVisibility(View.INVISIBLE);
		rated.setVisibility(View.INVISIBLE);
		total.setVisibility(View.INVISIBLE);
		rankIcon.setVisibility(View.INVISIBLE);
		progress.setVisibility(View.INVISIBLE);
	}
	
	private void setVisible() {
		loading.setVisibility(View.INVISIBLE);
		background_rect.setVisibility(View.VISIBLE);
		username.setVisibility(View.VISIBLE);
		rank_title.setVisibility(View.VISIBLE);
		written.setVisibility(View.VISIBLE);
		rated.setVisibility(View.VISIBLE);
		total.setVisibility(View.VISIBLE);
		rankIcon.setVisibility(View.VISIBLE);
		progress.setVisibility(View.VISIBLE);
	}

}
