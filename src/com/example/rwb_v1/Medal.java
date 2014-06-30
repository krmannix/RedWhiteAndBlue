package com.example.rwb_v1;

public class Medal {
	
	private String imagepath;
	private String name;
	private String desc;
	private boolean checked;
	
	Medal(String imagepath, String name, String desc) {
		this.imagepath = imagepath;
		this.name = name;
		this.desc = desc;
		this.checked = false;
	}
	
	public boolean isChecked() {
		return this.checked;
	}
	
	public void switchCheck() {
		this.checked = !this.checked;
	}
	
	public String getPath() {
		return this.imagepath;
	}
	
	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}
}
