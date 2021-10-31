package com.example.non_trivialapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivities extends Activity {
	private Button start_btn;
	private Button start_btn1;
	@Override
	// main interface with two button
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mains);
		start_btn = (Button) findViewById(R.id.start_btn);
		start_btn1 = (Button) findViewById(R.id.start_btn1);
		//click image to start slip
		start_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivities.this, MapsActivity.class);
				startActivity(intent);
			}
		});
		start_btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivities.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
}

