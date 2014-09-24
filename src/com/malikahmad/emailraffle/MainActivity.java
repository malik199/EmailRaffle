package com.malikahmad.emailraffle;

import java.io.File;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}


	public void startRaffle(View view) {
		// Do something in response to button click
		final EditText myInput = (EditText) findViewById(R.id.raffleName);
		String myInputString = myInput.getText().toString();
		// Toast.makeText(this, myInput.getText().toString(),
		// Toast.LENGTH_LONG).show();
		if (!isValid(myInputString)) {
			myInput.setError("Please create a name that is more than 3 characters");
			myInput.requestFocus();
		} else {

			File file = getBaseContext().getFileStreamPath(myInputString);
			if (file.exists()) {
				//File exist so ask to continue old raffle.
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						this);
				alertDialogBuilder.setMessage(R.string.already_raffle);
				alertDialogBuilder.setPositiveButton(R.string.new_raffle,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								myInput.requestFocus();

							}
						});
				alertDialogBuilder.setNegativeButton(R.string.continue_raffle,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										getApplicationContext(),
										AddContestantActivity.class);
								intent.putExtra("raffleName", myInput.getText()
										.toString());
								startActivity(intent);
							}
						});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			} else {
				//File does not exist so create a new raffle.
				Intent intent = new Intent(getApplicationContext(),
						AddContestantActivity.class);
				intent.putExtra("raffleName", myInput.getText().toString());
				startActivity(intent);
			}

		}
	}

	private boolean isValid(String pass) {
		if (pass != null && pass.length() > 2) {
			return true;
		} else {
			return false;
		}
	}
}
