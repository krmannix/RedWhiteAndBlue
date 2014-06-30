package com.example.rwb_v1;

import com.parse.LogInCallback;
import com.parse.Parse; 
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SplashPage extends Activity {
	
	Context context = this;
	ImageView signup_button, login_button, register_button, my_profile, leaderboards, rewards, info, rate, write, signout_button;
	EditText password, username, firstname, lastname, new_pass_one, new_pass_two, username_register;
	AlertDialog login_dialog, register_dialog;
	TextView currentuser;
	Intent goToInfo, goToProfile, goToLeaderboards, goToRewards, goToWrite, goToRate;
	SharedPreferences prefs;
	String prefName = "MyPref";
	SharedPreferences.Editor editor;
	ParseUser signupUser;
	
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_page);
		new AsyncTask<Void, Void, Void>() {
		    @Override
		    public Void doInBackground(Void... params) {
		    	Parse.initialize(context, "a6YcVdnzUomdTpnfLFOYC5YImQmdlCfzkpNqtPZK", "5b09yCzGmh3db0WAPBSyMkt410dtjPLxYqkXZC5h"); 
				return null;
		    }
		    
		    @Override
		    public void onPostExecute(Void result) {
		    	checkLogin();
		    }
		}.execute();
		// Set up the login dialog
		AlertDialog.Builder login_dialog_builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View second_dialog_view = inflater.inflate(R.layout.login_dialog, null);
		login_dialog_builder.setView(second_dialog_view);
		// Create the login dialog
		login_dialog = login_dialog_builder.create();
		
		AlertDialog.Builder register_dialog_builder = new AlertDialog.Builder(context);
		View register_dialog_view = inflater.inflate(R.layout.register_dialog, null);
		register_dialog_builder.setView(register_dialog_view);
		// Create the register dialog
		register_dialog = register_dialog_builder.create();
		 
		// Get all views in login window
		password = (EditText) second_dialog_view.findViewById(R.id.password_login);
		username = (EditText) second_dialog_view.findViewById(R.id.username_login);
		signup_button = (ImageView) second_dialog_view.findViewById(R.id.signup_button);
		login_button = (ImageView) second_dialog_view.findViewById(R.id.login_button);
		
		// Get all views in register window
		firstname = (EditText) register_dialog_view.findViewById(R.id.first_name_edit);
		lastname = (EditText) register_dialog_view.findViewById(R.id.last_name_edit);
		new_pass_one = (EditText) register_dialog_view.findViewById(R.id.pass_1_edit);
		new_pass_two = (EditText) register_dialog_view.findViewById(R.id.pass_2_edit);	
		register_button = (ImageView) register_dialog_view.findViewById(R.id.register_button);
		username_register = (EditText) register_dialog_view.findViewById(R.id.username_edit);
		
		// Get all regular buttons
		my_profile = (ImageView) findViewById(R.id.splash_profile);
		leaderboards = (ImageView) findViewById(R.id.splash_leader);
		rewards  = (ImageView) findViewById(R.id.splash_rewards);
		info = (ImageView) findViewById(R.id.splash_info);
		rate = (ImageView) findViewById(R.id.splash_rate);
		write = (ImageView) findViewById(R.id.splash_write);
		signout_button = (ImageView) findViewById(R.id.signout);
		currentuser = (TextView) findViewById(R.id.current_user);
		
		
		// Make all the intents
		goToInfo = new Intent(this, AboutUsPage.class);
		goToLeaderboards = new Intent(this, LeaderboardsPage.class);
		goToRewards = new Intent(this, RewardsPageGrid.class);
		goToProfile = new Intent(this, ProfilePage.class);
		goToWrite = new Intent(context, WritePage.class);
		goToRate = new Intent(this, RatePage.class);
		
		// Get the preferences
		prefs = getSharedPreferences(prefName, MODE_PRIVATE);
		editor = prefs.edit();
		
		// onClicks
		login_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (password.getText() != null && username.getText() != null) {
					logIn();
				}
			}			
		});
		
		signup_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login_dialog.cancel();
				register_dialog.show();				
			}
		});
		
		signout_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (loggedIn()) {
					new GeneralAlert(context, "Logout Successful!").showAlert();
					ParseUser.logOut();
					currentuser.setText("No one logged in");
				} else {
					new GeneralAlert(context, "No one is currently logged in.").showAlert();	
				}
			}
		});
		
		register_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (allRegisterFieldsFilled()) {
					register();
				} else {
					AlertDialog.Builder b = new AlertDialog.Builder(context).setMessage("Not all fields filled out!");
					((TextView) findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
					b.create().show();
				}
			}
		});
		
		rate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loggedIn()) {
					startActivity(goToRate);
				} else {
					login_dialog.show();
				}
			}			
		});
		
		write.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loggedIn()) {
					startActivity(goToWrite);
				} else {
					login_dialog.show();
				}
			}			
		});
		
		my_profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loggedIn()) {
					startActivity(goToProfile);
				} else {
					login_dialog.show();
				}
			}			
		});
		
		leaderboards.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loggedIn()) {
					startActivity(goToLeaderboards);
				} else {
					login_dialog.show();
				}
			}			
		});
		
		rewards.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loggedIn()) {
					startActivity(goToRewards);
				} else {
					login_dialog.show();
				}
			}			
		});
		
		info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loggedIn()) {
					startActivity(goToInfo);
				} else {
					login_dialog.show();
				}
			}			
		});
	}
	
	protected void logIn() {
		ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			    	currentuser.setText(ParseUser.getCurrentUser().getUsername());
			    } else {
			    	Toast.makeText(context, "You are not logged in due to: " + e.toString(), Toast.LENGTH_LONG).show();
			    }
			  }
			});
	}

	private boolean loggedIn() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			currentuser.setText(currentUser.getUsername());
			return true;
		} else {
		  return false;
		}
	}
	
	private boolean allRegisterFieldsFilled() {
		boolean pass = false;
		boolean name = false;
		if (new_pass_one.getText() != null && new_pass_two.getText() != null) {
			if (new_pass_one.getText().toString().equals(new_pass_two.getText().toString()))
				pass = true;
		}
		if (firstname.getText() != null && lastname.getText() != null && username_register.getText() != null ) {
			if ((!firstname.getText().toString().equals("")) && (!lastname.getText().toString().equals("")) && (!username_register.getText().toString().equals("")))
				name = true;
		}
		return (name && pass);
	}
	
	private void register() {
		signupUser = new ParseUser();
		signupUser.setUsername(username_register.getText().toString());
		signupUser.put("first", firstname.getText().toString());
		signupUser.put("lastname", lastname.getText().toString());
		signupUser.put("XP", 0);
		signupUser.put("rank", 0);
		signupUser.put("medals", 0);
		signupUser.put("messagesSent", 0);
		signupUser.put("messagesRated", 0);
		signupUser.put("avgMessageRating", 0);
		signupUser.setPassword(new_pass_one.getText().toString());
		
		SignUpCallback back = new SignUpCallback() {
			  public void done(ParseException e) {
				    if (e == null) {
				    	new GeneralAlert(context, "Registration complete!").showAlert();
				    	register_dialog.cancel();
				    	currentuser.setText(username_register.getText().toString());
				    	BadgeContainer.initializeBadges(signupUser);
				    } else {
				    	if (e.getCode() == ParseException.USERNAME_TAKEN)
				    		Toast.makeText(context, "That username is already taken.", Toast.LENGTH_LONG).show();
				    	else
				    		Toast.makeText(context, "Something went wrong.", Toast.LENGTH_LONG).show();
				    }
				  }
				};	
		signupUser.signUpInBackground(back);	
	}
	
	public void checkLogin() {
		// Show the login dialog if not logged in
		if (!loggedIn()) {
			login_dialog.show();
		}
	}

}
