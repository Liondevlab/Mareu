package com.liondevlab.mareu.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.liondevlab.mareu.R;

public class CreateMeetingActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_meeting);
	}


	/**
	 * Used to navigate to this activity
	 * @param activity
	 */
	public static void navigate(FragmentActivity activity) {
		Intent intent = new Intent(activity, CreateMeetingActivity.class);
		ActivityCompat.startActivity(activity, intent, null);
	}
}