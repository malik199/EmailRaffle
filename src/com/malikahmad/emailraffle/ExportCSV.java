package com.malikahmad.emailraffle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
				//WRITING THE FILE...
				File f = getExternalFilesDir(null);
				String path = f.getAbsolutePath();

				try {
					writeCSV(str_raffleName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String fullpathstring = "File written to: " + path + "/" + str_raffleName + ".csv";
				final TextView filepath = (TextView) findViewById(R.id.filepath);
				filepath.setVisibility(View.VISIBLE);
				filepath.setText(fullpathstring);
			
			
		}else if(isExternalStorageReadable()){
			Toast.makeText(getApplicationContext(), "This is readable",
					Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "Device not readable or writable",
					Toast.LENGTH_LONG).show();
		}
		
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
	
	private File file_csv;
	private void writeCSV(String rafname) throws IOException {

		// ReadFile
		FileInputStream fis = null;
		try {
			fis = openFileInput(rafname);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);
		StringBuffer b = new StringBuffer();
		try {
			while (bis.available() != 0) {
				char c = (char) bis.read();
				b.append(c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	/*	Toast.makeText(getApplicationContext(), b.toString(),
				Toast.LENGTH_SHORT).show();*/
		String outPut = null;
		try {

			JSONArray data = new JSONArray(b.toString());

			for (int i = 0; i < data.length(); i++) {

				String email = data.getJSONObject(i).getString("EmailAddress");
				String firstname = data.getJSONObject(i).getString("FirstName");
				String lastname = data.getJSONObject(i).getString("LastName");
				outPut = outPut + email + "," + firstname + "," + lastname + "\n";
				//employeeList.add(createEmployee("employees", outPut));
			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	    String csv_string = outPut;
		
		File extDir = getExternalFilesDir(null);
		file_csv = new File(extDir, rafname+".csv");
		FileOutputStream fos = new FileOutputStream(file_csv);
		fos.write(csv_string.getBytes());
		fos.close();
	}
	
	
/*	public File getAlbumStorageDir(String albumName) {
	    // Get the directory for the user's public pictures directory. 
	    File file = new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES), albumName);
	    if (!file.mkdirs()) {
	        //Log.e(LOG_TAG, "Directory not created");
	    	Toast.makeText(getApplicationContext(), "Directory not created",
					Toast.LENGTH_LONG).show();
	    }
	    return file;
	}*/

}
