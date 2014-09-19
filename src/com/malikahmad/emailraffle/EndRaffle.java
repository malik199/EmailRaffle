package com.malikahmad.emailraffle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
            	Toast.makeText(getApplicationContext(), "WE ARE READY TO END!", Toast.LENGTH_LONG).show();
            }
        });
	}
}
