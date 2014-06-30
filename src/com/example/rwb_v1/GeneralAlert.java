package com.example.rwb_v1;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GeneralAlert {
		
	private Context context; 
	private String message;
	private AlertDialog alert;
	
	GeneralAlert(Context context, String message) {
		this.context = context;
		this.message = message;
		createAlert();
	}
	
	GeneralAlert(Context context, String title, String desc, int resource) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View badge_dialog_view = inflater.inflate(R.layout.badge_alert, null);
		((TextView) badge_dialog_view.findViewById(R.id.title_badge)).setText(title);
		((TextView) badge_dialog_view.findViewById(R.id.main_text_badge)).setText(desc);
		((ImageView) badge_dialog_view.findViewById(R.id.image_badge)).setImageResource(resource);
		builder.setView(badge_dialog_view);
		this.alert = builder.create();
	}
	
	private void createAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View second_dialog_view = inflater.inflate(R.layout.general_alert, null);
		((TextView) second_dialog_view.findViewById(R.id.main_text)).setText(this.message);
		builder.setView(second_dialog_view);
		this.alert = builder.create();
	}
	
	public void showAlert() {
		this.alert.show();
	}

}
	

