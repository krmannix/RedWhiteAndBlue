package com.example.rwb_v1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AboutUsPage extends Activity {
	
	ImageView arrow_back, q_button;
	Intent goToSplashPage;
	Context context = this;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		
		arrow_back = (ImageView) findViewById(R.id.arrow_back);
		q_button = (ImageView) findViewById(R.id.question);
		
		goToSplashPage = new Intent(this, SplashPage.class);
		
		arrow_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(goToSplashPage);				
			}
		});
		
		q_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new GeneralAlert(context, "Insert Info about the About Us Page").showAlert();
			}
		});
	}

}
