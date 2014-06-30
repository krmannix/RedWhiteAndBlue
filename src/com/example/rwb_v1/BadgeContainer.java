package com.example.rwb_v1;

import java.util.ArrayList;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class BadgeContainer {
	
	
	public static ArrayList<Badge> getAllBadges() {
		return allBadges;
	}
	
	public static void initializeBadges(ParseUser user) {
		ParseObject badges = new ParseObject("badges");
		badges.put("username", user.getUsername());
		for (int i = 0; i < allBadges.size(); i++) 
			badges.put(allBadges.get(i).getParseCol(), -1);
		badges.saveInBackground();
	}
	
	public static int getBadgeSize() {
		return allBadges.size();
	}
	
	
	
	// Make all the badges
	// Badges
	static Badge carrierPidgeon = new Badge(badgeInfo.carrierPidgeonTitle,
									 badgeInfo.carrierPidgeonDesc,
									 badgeInfo.carrierPidgeonParse,
									 badgeInfo.carrierPidgeonRes);
	
	static Badge quickDraw = new Badge(badgeInfo.quickdrawTitle,
			 badgeInfo.quickdrawDesc,
			 badgeInfo.quickdrawParse,
			 badgeInfo.quickdrawRes); 
	
	static Badge commOp = new Badge(badgeInfo.commOpTitle,
			 badgeInfo.commOpDesc,
			 badgeInfo.commOpParse,
			 badgeInfo.commOpRes); 
	
	static Badge perfectEx = new Badge(badgeInfo.perfectExTitle,
			 badgeInfo.perfectExDesc,
			 badgeInfo.perfectExParse,
			 badgeInfo.perfectExRes); 
	
	static Badge comrade = new Badge(badgeInfo.comradeTitle,
			 badgeInfo.comradeDesc,
			 badgeInfo.comradeParse,
			 badgeInfo.comradeRes); 
	
	static Badge rapidFire = new Badge(badgeInfo.rapidFireTitle,
			 badgeInfo.rapidFireDesc,
			 badgeInfo.rapidFireParse,
			 badgeInfo.rapidFireRes); 
	
	static Badge single = new Badge(badgeInfo.singleTitle,
			 badgeInfo.singleDesc,
			 badgeInfo.singleParse,
			 badgeInfo.singleRes); 
	
	static Badge stamina = new Badge(badgeInfo.staminaTitle,
			 badgeInfo.staminaDesc,
			 badgeInfo.staminaParse,
			 badgeInfo.staminaRes); 
	
	static Badge explorer = new Badge(badgeInfo.explorerTitle,
			 badgeInfo.explorerDesc,
			 badgeInfo.explorerParse,
			 badgeInfo.explorerRes); 
	
	static Badge stencil = new Badge(badgeInfo.stencilTitle,
			 badgeInfo.stencilDesc,
			 badgeInfo.stencilParse,
			 badgeInfo.stencilRes); 
	
	static Badge swap = new Badge(badgeInfo.swapTitle,
			 badgeInfo.swapDesc,
			 badgeInfo.swapParse,
			 badgeInfo.swapRes); 
	
	static Badge recon = new Badge(badgeInfo.reconTitle,
			 badgeInfo.reconDesc,
			 badgeInfo.reconParse,
			 badgeInfo.reconRes); 
	
	static Badge leader = new Badge(badgeInfo.leaderTitle,
			 badgeInfo.leaderDesc,
			 badgeInfo.leaderParse,
			 badgeInfo.leaderRes); 
	
	static Badge service = new Badge(badgeInfo.serviceTitle,
			 badgeInfo.serviceDesc,
			 badgeInfo.serviceParse,
			 badgeInfo.serviceRes); 
	
	static Badge duty = new Badge(badgeInfo.dutyTitle,
			 badgeInfo.dutyDesc,
			 badgeInfo.dutyParse,
			 badgeInfo.dutyRes); 
	
	static Badge eye = new Badge(badgeInfo.eyeTitle,
			 badgeInfo.eyeDesc,
			 badgeInfo.eyeParse,
			 badgeInfo.eyeRes); 
	
	private static ArrayList<Badge> allBadges = new ArrayList<Badge>() {{
		add(carrierPidgeon);
		add(quickDraw);
		add(commOp);
		add(perfectEx);
		add(comrade);
		add(rapidFire);
		add(single);
		add(stamina);
		add(explorer);
		add(stencil);
		add(swap);
		add(recon);
		add(leader);
		add(service);
		add(duty);
		add(eye);
	}};
	
	static class badgeInfo {
		
		static // Carrier Pigeon
		String carrierPidgeonTitle = "Carrier Pigeon";
		static String carrierPidgeonDesc = "Total Messages Sent";
		static String carrierPidgeonParse = "carrierPigeon";
		static int carrierPidgeonRes = R.drawable.carrier_pigeon;
		
		// Quick Draw
		static String quickdrawTitle = "Quick Draw";
		static String quickdrawDesc = "Messages Sent in a Time Period (Users can get these multiple times)";
		static String quickdrawParse = "quickDraw";
		static int quickdrawRes = R.drawable.quick_draw;
		
		// Communications Operator
		static String commOpTitle = "Communications Operator";
		static String commOpDesc = "Messages Sent Consecutively";
		static String commOpParse = "commOperator";
		static int commOpRes = R.drawable.comm_op;
		
		// Perfect Execution
		static String perfectExTitle = "Perfect Execution";
		static String perfectExDesc = "Perfect 5 Star Message";
		static String perfectExParse = "perfectExecution"; 
		static int perfectExRes = R.drawable.perfect_ex;
		
		// Good Comrade
		static String comradeTitle = "Good Comrade";
		static String comradeDesc = "Total Messages Rated";
		static String comradeParse = "goodComrade";
		static int comradeRes = R.drawable.good_comrade;
		
		// Rapid Fire
		static String rapidFireTitle = "Rapid Fire";
		static String rapidFireDesc = "Messages Rated in a Time Period (Users can get these multiple times)";
		static String rapidFireParse = "rapidFire";
		static int rapidFireRes = R.drawable.rapid_fire;
		
		// Single Shooter
		static String singleTitle = "Single Shooter";
		static String singleDesc = "Messages Rated Consistently";
		static String singleParse = "singleShooter";
		static int singleRes = R.drawable.single_shooter;
		
		// Stamina
		static String staminaTitle = "Stamina";
		static String staminaDesc = "Messages Rated in a row";
		static String staminaParse = "stamina";
		static int staminaRes = R.drawable.stamina;
		
		// Explorer
		static String explorerTitle = "Explorer";
		static String explorerDesc = "Open every page in the app at least once!";
		static String explorerParse = "explorer";
		static int explorerRes = R.drawable.explorer;
		
		// New Stencil
		static String stencilTitle = "New Stencil";
		static String stencilDesc = "Change your font";
		static String stencilParse = "newStencil";
		static int stencilRes = R.drawable.stencil;
		
		// Gear Swap
		static String swapTitle = "Gear Swap";
		static String swapDesc = "Change your skin";
		static String swapParse = "getSwap";
		static int swapRes = R.drawable.gear_swap;
		
		// Recon
		static String reconTitle = "Recon";
		static String reconDesc = "View someone else's profile";
		static String reconParse = "recon";
		static int reconRes = R.drawable.recon;
		
		// Team Leader
		static String leaderTitle = "Team Leader";
		static String leaderDesc = "Top of the Active leaderboard";
		static String leaderParse = "teamLeader";
		static int leaderRes = R.drawable.team_leader;
		
		// Distinguished Service
		static String serviceTitle = "Distinguished Service";
		static String serviceDesc = "Top of the Rated leaderboard";
		static String serviceParse = "distinguishedService";
		static int serviceRes = R.drawable.dist_service;
		
		// Active Duty
		static String dutyTitle = "Active Duty";
		static String dutyDesc = "Top of the Messaging leaderboard";
		static String dutyParse = "activeDuty";
		static int dutyRes = R.drawable.active_duty;
		
		// Critical Eye
		static String eyeTitle = "Critical Eye";
		static String eyeDesc = "Top of the Rating leaderboard";
		static String eyeParse = "criticalEye";
		static int eyeRes = R.drawable.medal_one; 
		
	}
 

}
