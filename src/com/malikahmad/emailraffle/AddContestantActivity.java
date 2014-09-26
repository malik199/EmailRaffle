package com.malikahmad.emailraffle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddContestantActivity extends Activity {

	private String str_raffleName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contestant);

		Intent intent = getIntent();
		str_raffleName = intent.getStringExtra("raffleName");
		TextView raffleNameWidget = (TextView) findViewById(R.id.RaffleDisplayWidget);
		raffleNameWidget.setText("Raffle Name: " + str_raffleName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// THIS WILL SEND YOU TO THE VIEW ENTRIES CLASS
		switch (id) {
		case R.id.view_entries:
			Intent intent = new Intent(AddContestantActivity.this,
					ViewEntries.class);
			intent.putExtra("raffleName", str_raffleName);
			startActivity(intent);
			break;
		case R.id.end_raffle:
			Intent intent2 = new Intent(AddContestantActivity.this,
					EndRaffle.class);
			intent2.putExtra("raffleName", str_raffleName);
			startActivity(intent2);
			break;
		default:
			break;

		}
		/*
		 * if (id == R.id.view_entries) { return true; }
		 */
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	// /---------------------------------------------
	int contNumb = 1;
	String jsonString = null;
	JSONArray jsonArr = new JSONArray();

	public void submitContestant(View view) throws JSONException, IOException {

		EditText strEmailAddress = (EditText) findViewById(R.id.emailAddress);
		EditText strFirstName = (EditText) findViewById(R.id.firstName);
		EditText strLastName = (EditText) findViewById(R.id.lastName);
		// EditText strNumberEntries = (EditText)
		// findViewById(R.id.numberEntries);

		if (!isValidEmail(strEmailAddress.getText().toString())) {
			strEmailAddress.setError("Invalid Email");
			strEmailAddress.requestFocus();
		} else if (!isValid(strFirstName.getText().toString())) {
			strFirstName.setError("Invalid First Name");
			strFirstName.requestFocus();
		} else if (!isValid(strLastName.getText().toString())) {
			strLastName.setError("Invalid Last Name");
			strLastName.requestFocus();
		} else {

			// Grab new info and put into an array
			JSONObject jsonObj;
			jsonObj = new JSONObject();
			jsonObj.put("EmailAddress", strEmailAddress.getText().toString());
			jsonObj.put("FirstName", strFirstName.getText().toString());
			jsonObj.put("LastName", strLastName.getText().toString());

			// Check to see if a new file exits
			File file = getBaseContext().getFileStreamPath(str_raffleName);
			if (file.exists()) {

				// Read old file
				FileInputStream fis = openFileInput(str_raffleName);
				BufferedInputStream bis = new BufferedInputStream(fis);
				StringBuffer b = new StringBuffer();
				while (bis.available() != 0) {
					char c = (char) bis.read();
					b.append(c);
				}

				// put new array into old array
				JSONArray jsonArr2 = new JSONArray(b.toString());

				jsonArr2.put(jsonObj);
				jsonString = jsonArr2.toString();

				// Write the new array to disk
				try {
					FileOutputStream fos = new FileOutputStream(file, false);
					fos.write(jsonString.getBytes());
					fos.close();

				} catch (FileNotFoundException ex) {
					System.out.println("FileNotFoundException : " + ex);
				} catch (IOException ioe) {
					System.out.println("IOException : " + ioe);
				}

				Toast.makeText(this,
						"Data was written to the file: " + str_raffleName,
						Toast.LENGTH_LONG).show();
			} else {

				JSONArray jsonArr3 = new JSONArray();
				jsonArr3.put(jsonObj);
				jsonString = jsonArr3.toString();
				try {
					FileOutputStream fos = openFileOutput(str_raffleName,
							MODE_PRIVATE);
					fos.write(jsonString.getBytes());
					fos.close();
					Toast.makeText(this, "New file has been written",
							Toast.LENGTH_LONG).show();// do nothing
					// TextView textViewOutput = (TextView)
					// findViewById(R.id.textViewOutput);
					// textViewOutput.setText(jsonString);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/*
			 * Log.d("myMessage1", strEmailAddress.getText().toString());
			 * Log.d("myMessage2", strFirstName.getText().toString());
			 * Log.d("myMessage3", strLastName.getText().toString());
			 * Log.d("myMessage4", strNumberEntries.getText().toString());
			 */

			// Toast.makeText(this, "Contestant Sumbitted",
			// Toast.LENGTH_LONG).show();
			TextView intContestantNumber = (TextView) findViewById(R.id.contestantNumber);
			contNumb++;
			intContestantNumber.setText("Contestant #" + contNumb);
			resetFields(strEmailAddress, strFirstName, strLastName);
		}
	}

	private void displayData(String myString) {
		TextView textViewOutput = (TextView) findViewById(R.id.textViewOutput);
		textViewOutput.setText(myString);
	}

	private void resetFields(Object o1, Object o2, Object o3) {
		// int resetNumb = 1;
		((EditText) o1).setText(null);
		((EditText) o1).requestFocus();

		((EditText) o2).setText(null);
		((EditText) o3).setText(null);
		// ((EditText) o4).setText(String.valueOf(resetNumb));

	}

	// validating email id
	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	// validating password with retype password
	private boolean isValid(String pass) {
		if (pass != null && pass.length() > 2) {
			return true;
		} else {
			return false;
		}
	}
}