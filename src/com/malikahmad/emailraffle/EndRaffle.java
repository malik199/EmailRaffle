package com.malikahmad.emailraffle;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EndRaffle extends Activity {
	private String str_raffleName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_raffle);

		Intent intent = getIntent();
		str_raffleName = intent.getStringExtra("raffleName");
		TextView raffleNameWidget = (TextView) findViewById(R.id.RaffleDisplayNameWidget);
		raffleNameWidget.setText("Raffle Name: " + str_raffleName);

		final Button button = (Button) findViewById(R.id.calculate_raffle);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				calculateScore(str_raffleName);
			}
		});
	}

	private void calculateScore(String therafflename) {

		// ReadFile
		FileInputStream fis = null;
		try {
			fis = openFileInput(therafflename);
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

		try {
			final Button button = (Button) findViewById(R.id.calculate_raffle);
			button.setVisibility(View.GONE);

			JSONArray data = new JSONArray(b.toString());
			// get the final random number
			int finalrandom_int = getRandomNumber(data.length() - 1);
			// Get the winner based on the list:
			String email = data.getJSONObject(finalrandom_int).getString(
					"EmailAddress");
			String firstname = data.getJSONObject(finalrandom_int).getString(
					"FirstName");
			String lastname = data.getJSONObject(finalrandom_int).getString(
					"LastName");
			final String outPut = (finalrandom_int) + ". " + email + " - "
					+ firstname + " " + lastname;

			// String theFinalRandomNumber =
			// Integer.toString(getRandomNumber(data
			// .length()));
			final ImageView timergraphic = (ImageView) findViewById(R.id.timerclock);
			timergraphic.setVisibility(View.VISIBLE);
			Toast.makeText(getApplicationContext(), "Calculating Winner...",
					Toast.LENGTH_LONG).show();
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					timergraphic.setVisibility(View.GONE);
					final TextView winningEmail = (TextView) findViewById(R.id.winningEmail);
					winningEmail.setVisibility(View.VISIBLE);
					winningEmail.setText(outPut);
					final TextView thewinner = (TextView) findViewById(R.id.thewinneris);
					thewinner.setVisibility(View.VISIBLE);
					Button exportcsv = (Button) findViewById(R.id.export_csv);
					exportcsv.setVisibility(View.VISIBLE);
				}
			}, 5000);

		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					5000).show();
		}

	}

	private int getRandomNumber(int numberofentries) {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(numberofentries);
		return randomInt;
	}
	
	public void exportCSV(View view) {
		Intent intent = new Intent(getApplicationContext(),
				ExportCSV.class);
		intent.putExtra("raffleName", str_raffleName);
		startActivity(intent);
	}

}
