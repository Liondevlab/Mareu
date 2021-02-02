package com.liondevlab.mareu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.liondevlab.mareu.R;
import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.model.Meeting;
import com.liondevlab.mareu.service.MeetingApiService;

import java.util.ArrayList;

public class MeetingListActivity extends AppCompatActivity implements MeetingRecyclerInterface{

	// User Interface elements
	RecyclerView mRecyclerView;
	FloatingActionButton mCreateMeetingButton;
	MeetingsRecyclerViewAdapter mMeetingsRecyclerViewAdapter;
	MeetingApiService mMeetingApiService = DI.getMeetingApiService();
	ArrayList<Meeting> mMeetingListAsArray = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meeting_list);
		this.configureToolbar();
		mRecyclerView = findViewById(R.id.list_meetings);
		mCreateMeetingButton = findViewById(R.id.create_meeting_button);
		initList();
		mCreateMeetingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent createMeetingIntent = new Intent(getApplicationContext(), CreateMeetingActivity.class);
				startActivity(createMeetingIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.meeting_menu, menu);
		return true;
	}

	/**
	 * Initializing list of meetings
	 */
	private void initList() {
		mMeetingListAsArray = mMeetingApiService.getMeetings();
		mMeetingsRecyclerViewAdapter = new MeetingsRecyclerViewAdapter(mMeetingListAsArray, this);
		mRecyclerView.setAdapter(mMeetingsRecyclerViewAdapter);
/*
		mMeetingListAsArray.clear();
		mMeetingListAsArray.add();
		mMeetingListAsArray.addAll(mMeetingApiService.getMeetings());
*/
	}

	private void configureToolbar() {
		Toolbar toolbar = findViewById(R.id.meeting_toolbar);
		setSupportActionBar(toolbar);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item_meeting_filter) {
			showEditDialog();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showEditDialog() {
		FragmentManager fm = getSupportFragmentManager();
		MeetingFilteringDialog editNameDialogFragment = MeetingFilteringDialog.newInstance("Some Title");
		editNameDialogFragment.show(fm, "meeting_filter_dialog");
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		mMeetingApiService.deleteMeeting(meeting);
		mMeetingListAsArray.clear();
		mMeetingListAsArray.addAll(mMeetingApiService.getMeetings());
		Log.d("TOTO", "deleteMeeting: " + mMeetingListAsArray.size());
		mMeetingsRecyclerViewAdapter.notifyDataSetChanged();
	}
}