package com.example.rwb_v1;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MessageHistoryProfileAdapter extends ArrayAdapter<MessageClass> {
	private final Context context;
	private final List<MessageClass> messages;

	public MessageHistoryProfileAdapter(Context context, List<MessageClass> messages) {
		super(context, R.layout.row_for_message_history, messages);
		this.context = context;
		this.messages = messages;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_for_profile_message_history, parent, false);
		TextView message_text = (TextView) rowView.findViewById(R.id.message_text);
		TextView date = (TextView) rowView.findViewById(R.id.sent_text);
		// Sent date sent
		date.setText(Html.fromHtml("<bold>" + messages.get(position).getDateString() + "</bold>"));
		message_text.setText(messages.get(position).getMessage());
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View alertView = inflater.inflate(R.layout.general_alert, parent, false);
		TextView alertText = (TextView) alertView.findViewById(R.id.main_text);
		alertText.setText(messages.get(position).getMessage());
		final AlertDialog alert = builder.create();
		rowView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.show();
			}
		});
			
		return rowView;
	}

}