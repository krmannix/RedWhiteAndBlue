package com.example.rwb_v1;

import java.util.List;

import com.parse.ParseObject;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LeaderboardMessAdapter extends ArrayAdapter<ParseObject> {

	List<ParseObject> messages;
	Context context;
	
	public LeaderboardMessAdapter(Context context, List<ParseObject> messages, int resource) {
		super(context, resource, messages);
		this.messages = messages;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_for_leaderboard_message, parent, false);
		
		TextView rank = (TextView) rowView.findViewById(R.id.leaderboard_message_rank);
		TextView name = (TextView) rowView.findViewById(R.id.leaderboard_message_msg); 
		RatingBar stars = (RatingBar) rowView.findViewById(R.id.rating_bar_message);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) name.getLayoutParams();
		params.setMargins(params.leftMargin, params.topMargin, (int) TypedValue.applyDimension(
		        TypedValue.COMPLEX_UNIT_DIP,
		        100, 
		        context.getResources().getDisplayMetrics()
		), params.rightMargin); //substitute parameters for left, top, right, bottom
		name.setLayoutParams(params);
		
		
		
		if (messages.get(position) != null) {
			rank.setText(Integer.toString(position + 1));
			stars.setRating((float) messages.get(position).getDouble("rating"));
			name.setText(messages.get(position).getString("message"));
		}
		
		
		return rowView;
		
	}
}
