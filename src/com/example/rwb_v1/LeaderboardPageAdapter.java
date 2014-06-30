package com.example.rwb_v1;

import java.io.IOException;
import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class LeaderboardPageAdapter extends ArrayAdapter<ParseUser> {
	private final Context context;
	private final List<ParseUser> users;
	private final int leaderboard;
	private LayoutInflater inflater;
	
	public LeaderboardPageAdapter(Context context, List<ParseUser> users, int leaderboard) {
		super(context, R.layout.row_for_leaderboard, users);
		this.context = context;
		this.users = users;
		this.leaderboard = leaderboard;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.w("xxx","In get view");
		View rowView = inflater.inflate(R.layout.row_for_leaderboard, parent, false);
		TextView rank = (TextView) rowView.findViewById(R.id.leaderboard_rank);
		TextView score = (TextView) rowView.findViewById(R.id.leaderboard_score);
		TextView name = (TextView) rowView.findViewById(R.id.leaderboard_username); 
		/*ImageView rankIcon = (ImageView) rowView.findViewById(R.id.rank_icon);

		}*/
			if (users.get(position) != null) {
				// Set rank
				rank.setText(Integer.toString(position + 1));		
				// Set score from user
				score.setText(Integer.toString(users.get(position).getInt("XP")));
				score.setVisibility(View.VISIBLE);
				// Set name
				name.setText(users.get(position).getUsername().toString());
			}
		return rowView;
	}
	
	public static class LeaderboardType {
		public static int ACTIVE = 0;
		public static int TOP_RATED = 1;
		public static int MESSAGING = 2;
		public static int RATING = 3;
	}
	
	Bitmap insertPhotoBitmap(String path, Context context){
	     Bitmap bm;
		 bm = decodeSampledBitmapFromResource(path, 80, 80, context);	     
	     return bm;
	    }

public static Bitmap decodeSampledBitmapFromResource(String path,
        int reqWidth, int reqHeight, Context context) {

    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    try {
		BitmapFactory.decodeStream(context.getAssets().open(path), null, options);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    try {
		return BitmapFactory.decodeStream(context.getAssets().open(path), null, options);
	} catch (IOException e) {
		return null;
	}
}

public static int calculateInSampleSize(
       BitmapFactory.Options options, int reqWidth, int reqHeight) {
	// Raw height and width of image
	final int height = options.outHeight;
	final int width = options.outWidth;
	int inSampleSize = 1;
	
	if (height > reqHeight || width > reqWidth) {
	
	   final int halfHeight = height / 2;
	   final int halfWidth = width / 2;
	
	   // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	   // height and width larger than the requested height and width.
	   while ((halfHeight / inSampleSize) > reqHeight
	           && (halfWidth / inSampleSize) > reqWidth) {
	       inSampleSize *= 2;
	   }
	}
	
	return inSampleSize;
	}
}
