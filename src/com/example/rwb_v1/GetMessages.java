package com.example.rwb_v1;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.ParseObject;

public class GetMessages {

	static List<ParseObject> queue;
	
	GetMessages() {
		queue = new ArrayList<ParseObject>();
	}
	
	public void setQueue(List<ParseObject> list) {
		queue.clear();
		for (int i = 0; i < list.size(); i++) {
			queue.add(list.get(i));
		}
	}
	
	public List<ParseObject> getQueue() {
		return queue;
	}
	
	public void checkQueue() {
		Log.w("xxx", "size of the queue is " + queue.size());
	}
}
