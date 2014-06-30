package com.example.rwb_v1;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Rank {
	
	public static String getRankString(int rankInt) {
		switch(rankInt) {
		case 0:
			return "Private";
		case 1:
			return "Private First Class";
		case 2:
			return "Corporal";
		case 3:
			return "Staff Sergeant";
		case 4:
			return "Sergeant";
		case 5:
			return "Sergeant First Class";
		case 6:
			return "First Lieutenant";
		case 7:
			return "Colonel";
		case 8: 
			return "Major General";
		case 9:
			return "General";
		default:
			return "Private";
		}
	}
	
	public static int getRankIntFromXP(int xp) {
		if (xp > 20000)
			return 9;
		else if (xp > 15000)
			return 8;
		else if (xp > 11400)
			return 7;
		else if (xp > 8300)
			return 6;
		else if (xp > 5700) 
			return 5;
		else if (xp > 3600) 
			return 4;
		else if (xp > 2000)
			return 3;
		else if (xp > 900)
			return 2;
		else if (xp > 300)
			return 1;
		else 
			return 0;
	}
	
	public static String getRankStringFromXP(int xp) {
		return getRankString(getRankIntFromXP(xp));
	}
	
	public static int getLeftOverXP(int xp) {
		if (xp > 20000)
			return xp%20000;
		else if (xp > 15000)
			return xp%15000;
		else if (xp > 11400)
			return xp%11400;
		else if (xp > 8300)
			return xp%8300;
		else if (xp > 5700) 
			return xp%5700;
		else if (xp > 3600) 
			return xp%3600;
		else if (xp > 2000)
			return xp%2000;
		else if (xp > 900)
			return xp%900;
		else if (xp > 300)
			return xp%300;
		else
			return xp;
	}
	
	public static int getRankResource(int rankInt) {
	 switch(rankInt) {
		case 0:
			return R.drawable.private_rank_icon;
		case 1:
			return R.drawable.private_first_class;
		case 2:
			return R.drawable.corporal;
		case 3:
			return R.drawable.sergeant;
		case 4:
			return R.drawable.staff_sergeant;
		case 5:
			return R.drawable.sergeant_first_class;
		case 6:
			return R.drawable.first_lieutenant;
		case 7:
			return R.drawable.colonel;
		case 8: 
			return R.drawable.major_general;
		case 9:
			return R.drawable.general;
		default:
			return R.drawable.private_rank_icon;
		}
	}

}
