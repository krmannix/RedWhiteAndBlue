package com.example.rwb_v1;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class TrackStats {
	
	private static int badgeXP = 50;
	private static int messageSentXP = 10;
	private static int messageRatedXP = 5;

	public static void incrementSentMessages(ParseUser currentUser) {
		currentUser.increment("messagesSent");
		currentUser.increment("XP", messageSentXP);
		if (updateRank(currentUser))
			currentUser.put("rank", Rank.getRankIntFromXP(currentUser.getInt("XP")));
		checkCarrierPigeon(currentUser);
		currentUser.saveInBackground();
	}
	
	public static void incrementRatedMessages(ParseUser currentUser) {
		currentUser.increment("messagesRated");
		currentUser.increment("XP", messageRatedXP);
		if (updateRank(currentUser))
			currentUser.put("rank", Rank.getRankIntFromXP(currentUser.getInt("XP")));
		currentUser.saveInBackground();
	}
	
	private static boolean updateRank(ParseUser currentUser) {
		if (currentUser.getInt("rank") == Rank.getRankIntFromXP(currentUser.getInt("XP")))
			return false;
		else
			return true;
	}
	
	public static void checkStamina(ParseUser currentUser, int number) {
		if (number == 15 || number == 30)
			stamina(currentUser, number);
	}
	
	public static void stamina(ParseUser currentUser, final int number) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("badges");
		query.whereEqualTo("username", currentUser.getUsername());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (objects.size() > 0) {
					objects.get(0).put("stamina", number);
					objects.get(0).saveInBackground();
				}
			}
		});
	}
	
	public static void reconBadge(ParseUser currentUser) {
		currentUser.put("recon", 0);
		currentUser.saveInBackground();
	}
	
	public static void checkCarrierPigeon(ParseUser currentUser) {
		int messagesSent = currentUser.getInt("messagesSent");
		switch (messagesSent) {
			case 10:
				carrierPigeon(currentUser, messagesSent);
				break;
			case 25:
				carrierPigeon(currentUser, messagesSent);
				break;
			case 50:
				carrierPigeon(currentUser, messagesSent);
				break;
			case 100:
				carrierPigeon(currentUser, messagesSent);
				break;
			case 150:
				carrierPigeon(currentUser, messagesSent);
				break;
			case 200:
				carrierPigeon(currentUser, messagesSent);
				break;
		}
	}
	
	private static void carrierPigeon(ParseUser currentUser, final int messageNumber) {
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("badges");

		query.whereEqualTo("username", currentUser.getUsername());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (objects.size() > 0) {
					objects.get(0).put("carrierPigeon", messageNumber);
					objects.get(0).saveInBackground();
				}
			}
		});
	}

}
