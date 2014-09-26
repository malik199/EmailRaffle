package com.malikahmad.emailraffle;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ExportCSV extends Activity {

	private String str_raffleName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_csv);

		Intent intent = getIntent();
		str_raffleName = intent.getStringExtra("raffleName");

		if(isExternalStorageWritable()){
			Toast.makeText(getApplicationContext(), "This is writable",
					Toast.LENGTH_LONG).show();
		}else if(isExternalStorageReadable()){
			Toast.makeText(getApplicationContext(), "This is readable",
					Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "Not readable or writable",
					Toast.LENGTH_LONG).show();
		}
		
		getAlbumStorageDir("myCSV");
	}

	public void startNewRaffle(View view) {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	public File getAlbumStorageDir(String albumName) {
	    // Get the directory for the user's public pictures directory. 
	    File file = new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES), albumName);
	    if (!file.mkdirs()) {
	        //Log.e(LOG_TAG, "Directory not created");
	    	Toast.makeText(getApplicationContext(), "Directory not created",
					Toast.LENGTH_LONG).show();
	    }
	    return file;
	}

}
