package com.example.rwb_v1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RewardGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Badge> allBadges;
    private ImageView mainImage;
    private TextView textDesc, header;
    private ImageView currentSelectedImage;
 // references to our images

    public RewardGridAdapter(Context c, ImageView mainImage, TextView textDesc, TextView header) {
        this.context = c;
        this.mainImage = mainImage;
        this.textDesc = textDesc;
        this.header = header;
        this.allBadges = BadgeContainer.getAllBadges();
    }

	public int getCount() {
        return allBadges.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        
        // if it's not recycled, initialize some attributes
        if (convertView == null) {  
            imageView = insertImage(allBadges.get(position).getImageResource());
            if (position == 0) {
            	mainImage.setImageResource(allBadges.get(position).getImageResource());
            	textDesc.setText(allBadges.get(position).getDescription());
				header.setText(allBadges.get(position).getTitle());
            	currentSelectedImage = imageView;
            	}
        } else {
            imageView = (ImageView) convertView;
        }
        
        // Set clickListener
        final int pos = position;
        final ImageView image = imageView;
        imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				currentSelectedImage.setBackgroundResource(0);
				image.setBackgroundResource(R.drawable.rank_background);
				currentSelectedImage = image;
				textDesc.setText(allBadges.get(pos).getDescription());
				header.setText(allBadges.get(pos).getTitle());
				mainImage.setImageResource(allBadges.get(pos).getImageResource());
			}
        });
        return imageView;
    }
    
    ImageView insertImage(int resource){	     
	     ImageView imageView = new ImageView(context);
         imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
         imageView.setPadding(8, 8, 8, 8);
	     imageView.setImageBitmap(CreateImage.getImage(context, resource, 130, 130));
	     return imageView;
    }
  }