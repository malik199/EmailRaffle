package com.malikahmad.emailraffle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ViewEntries extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_entries);
		
		initList();
		ListView listView = (ListView) findViewById(R.id.listView1);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, employeeList,
				android.R.layout.simple_list_item_1,
				new String[] { "employees" }, new int[] { android.R.id.text1 });
		listView.setAdapter(simpleAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

	private void initList() {	
		
		// ReadFile
		FileInputStream fis = null;
		try {
			fis = openFileInput("test");
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
		
		Toast.makeText(getApplicationContext(), b.toString(),
				Toast.LENGTH_SHORT).show();
		
		try {
			
			JSONArray data = new JSONArray(b.toString());

			for (int i = 0; i < data.length(); i++) {

				String email = data.getJSONObject(i).getString("EmailAddress");
				String firstname = data.getJSONObject(i).getString("FirstName");
				String lastname = data.getJSONObject(i).getString("LastName");
				String outPut = (i+1) + ". " + email + " - " + firstname + " " + lastname;
				employeeList.add(createEmployee("employees", outPut));
			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	private HashMap<String, String> createEmployee(String name, String number) {
		HashMap<String, String> employeeNameNo = new HashMap<String, String>();
		employeeNameNo.put(name, number);
		return employeeNameNo;
	}

}