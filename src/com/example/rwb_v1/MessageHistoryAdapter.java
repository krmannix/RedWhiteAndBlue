package com.example.rwb_v1;

import java.util.List;

import com.parse.ParseObject;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class MessageHistoryAdapter extends ArrayAdapter<ParseObject> {
	private final Context context;
	private final List<ParseObject> messages;

	public MessageHistoryAdapter(Context context, List<ParseObject> messages) {
		
		super(context, R.layout.row_for_message_history, messages);
		this.context = context;
		this.messages = messages;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_for_message_history, parent, false);
		TextView status_text = (TextView) rowView.findViewById(R.id.status_text);
		TextView message_text = (TextView) rowView.findViewById(R.id.message_text);
		TextView date = (TextView) rowView.findViewById(R.id.sent_text);
		RatingBar stars = (RatingBar) rowView.findViewById(R.id.ratingBar1);
		stars.setRating((float) messages.get(position).getDouble("rating"));
		// Set status
		if (messages.get(position).getInt("status") == -1)
			status_text.setText(Html.fromHtml("Status: <font color='red'>Denied</font>"));
		else if (messages.get(position).getInt("status") == 1)
			status_text.setText(Html.fromHtml("Status: <font color='green'>Approved</font>"));
		else
			status_text.setText(Html.fromHtml("Status: <font color='yellow'>Pending</font>"));
		
		// Sent date sent
		date.setText(Html.fromHtml("<bold>" + messages.get(position).getCreatedAt() + "</bold>"));
		
		message_text.setText(messages.get(position).getString("message"));
			
		return rowView;
	}

}
