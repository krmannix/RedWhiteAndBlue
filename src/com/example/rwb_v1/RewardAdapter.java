package com.example.rwb_v1;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RewardAdapter extends ArrayAdapter<Medal> {
	private static Context context;
	private final List<Medal> medals;

	public RewardAdapter(Context context, List<Medal> medals) {
		super(context, R.layout.row_for_rewards, medals);
		this.context = context;
		this.medals = medals;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_for_rewards, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.medal_name);
		ImageView checkmark = (ImageView) rowView.findViewById(R.id.checkmark);
		ImageView icon = (ImageView) rowView.findViewById(R.id.medal_image);
		AssetManager manager = context.getAssets();
		name.setText(medals.get(position).getName());
		if (medals.get(position).isChecked()) {
			checkmark.setImageResource(R.drawable.btn_check_on);
		} else {
			checkmark.setImageResource(R.drawable.btn_check_off);
		}
		
		Drawable d;
		try {
			d = Drawable.createFromStream(manager.open("medals/" + medals.get(position).getPath()), null);
			icon.setImageDrawable(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage(medals.get(position).getDesc()).setTitle(medals.get(position).getName());
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		final AlertDialog alert = builder.create();
		rowView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				alert.show();
			}
		});
		
		Toast.makeText(context, "Sending back rowView", Toast.LENGTH_SHORT);
		return rowView;
	}
	
	static class ViewHolder {   
	    ImageView Image;
	    TextView  Text;
	}
	
	

}
