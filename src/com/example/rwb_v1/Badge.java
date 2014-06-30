package com.example.rwb_v1;

import java.util.ArrayList;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class Badge {
	
	private int resource;
	private String description;
	private String title;
	private String parseCol; // This gives us the title for the parse column relating to this badge
	
	Badge(String title, String description, String parseCol, int resource) {
		this.resource = resource;
		this.description = description;
		this.title = title;
		this.parseCol = parseCol;
	}
	
	public int getImageResource() {
		return this.resource;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getParseCol() {
		return this.parseCol;
	}
	
	public int getMultiplier() {
		return 1;
	}

}
