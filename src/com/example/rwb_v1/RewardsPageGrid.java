package com.example.rwb_v1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class RewardsPageGrid extends Activity {
	
		ImageView bigPic;
		TextView text, header;
		ImageView arrow_back, q_button;
		Intent goToSplashPage;
		Context context = this;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rewards_page_grid);
		
		GridView g = (GridView) findViewById(R.id.gridview_reward);
		bigPic = (ImageView) findViewById(R.id.current_medal_picture);
		text = (TextView) findViewById(R.id.row_text);
		header = (TextView) findViewById(R.id.row_header);
		arrow_back = (ImageView) findViewById(R.id.arrow_back);
		q_button = (ImageView) findViewById(R.id.question);
		goToSplashPage = new Intent(context, SplashPage.class);
		
		g.setAdapter(new RewardGridAdapter(this, bigPic, text, header));
		
		arrow_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(goToSplashPage);
			}
		});
		
		q_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new GeneralAlert(context, "Insert Info about the Rewards Page").showAlert();
			}
		});
		
	}

}
