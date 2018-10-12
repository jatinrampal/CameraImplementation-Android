package com.example.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Page2Activity extends BaseActivity {

	SharedPreferences settings;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page2);

		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		initImageView(SETTINGS_PREFS_AVATAR, R.id.ImageView_Avatar);
		TextView text = (TextView) findViewById(R.id.TextView_FirstNameP2);
    }
	
	public void initImageView(String key, int id)
	{
		if(!settings.contains(key))return;

		ImageView imageView = (ImageView) findViewById(id);
		String uriString = settings.getString(key, "");

		if(uriString.equals("")) return;
		Uri imageUri = Uri.parse(uriString);
		imageView.setImageURI(imageUri);
	}
}
