package com.example.camera;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends BaseActivity {
  
 private static final int TAKE_AVATAR_CAMERA_REQUEST = 1;
 SharedPreferences settings;

    public static final String SETTINGS_PREFS = "SETTINGS PREFS";
    public static final String SETTINGS_PREFS_FIRST_NAME = "FIRST NAME";
    public static final String SETTINGS_PREFS_AVATAR = "AVATAR";

    Button btn;

 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);


  settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
  btn = (Button) findViewById(R.id.Button_Submit);
  initAvatar();
 }

 private void initAvatar(){
     ImageButton avatarButton = (ImageButton) findViewById(R.id.ImageButton_Picture);


     if(settings.contains(SETTINGS_PREFS_AVATAR))
     {
         String avatarUri = settings.getString(SETTINGS_PREFS_AVATAR, "");
         if(avatarUri != "")
         {
             Uri imageUri = Uri.parse(avatarUri);
             avatarButton.setImageURI(imageUri);
         }
         else
         {
             avatarButton.setImageResource(R.drawable.avatar);
         }
     }
     else {
         avatarButton.setImageResource(R.drawable.avatar);
     }

     avatarButton.setOnClickListener(new ChooseCameraListener());
 }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode)
        {
            case TAKE_AVATAR_CAMERA_REQUEST:
                if(resultCode == Activity.RESULT_CANCELED)
                {

                }
                else if(resultCode == Activity.RESULT_OK)
                {
                    Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
                    if(cameraPic != null )
                    {
                        try{
                            saveAvatar(cameraPic);
                        }
                        catch (Exception e)
                        {

                        }
                    }
                }
        }


    }

    private void saveAvatar(Bitmap avatar) {
        String avatarFilename = "avatar.jpg";
        try
        {
            avatar.compress(Bitmap.CompressFormat.JPEG, 100, openFileOutput(avatarFilename, MODE_PRIVATE));

        }
        catch (Exception e)
        {

        }
        Uri avatarUri = Uri.fromFile(new File(this.getFilesDir(), avatarFilename));

        ImageButton avatarButton = (ImageButton) findViewById(R.id.ImageButton_Picture);
        avatarButton.setImageURI(null);
        avatarButton.setImageURI(avatarUri);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SETTINGS_PREFS_AVATAR,avatarUri.getPath());
        editor.commit();
    }

    public void Continue(View view){
        EditText edit = (EditText) findViewById(R.id.EditText_FirstName);
        String name = edit.getText().toString();
        Intent intent = new Intent(MainActivity.this, Page2Activity.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    private class ChooseCameraListener implements View.OnClickListener {

        public void onClick(View arg0) {
            Intent pictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(Intent.createChooser(pictureIntent,"TAKE YOUR PHOTO"), TAKE_AVATAR_CAMERA_REQUEST);

        }
    }

}

