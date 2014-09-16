package com.malikahmad.emailraffle;

import android.support.v7.app.ActionBarActivity;
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void startRaffle(View view) {
        // Do something in response to button click
    	EditText myInput = (EditText)findViewById(R.id.raffleName); 
    	
       // Toast.makeText(this, myInput.getText().toString(), Toast.LENGTH_LONG).show();
    	if(!isValid(myInput.getText().toString())) {
    		myInput.setError("Please create a name that is more than 3 characters");
    		myInput.requestFocus();
    	} else {
    		Intent intent = new Intent(this, AddContestantActivity.class);
    		intent.putExtra("raffleName", myInput.getText().toString());
    		startActivity(intent);
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
