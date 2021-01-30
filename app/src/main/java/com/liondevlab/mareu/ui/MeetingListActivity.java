package com.liondevlab.mareu.ui;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.liondevlab.mareu.R;
import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.model.Meeting;
import com.liondevlab.mareu.model.MeetingRoom;
import com.liondevlab.mareu.service.MeetingApiService;

import java.util.ArrayList;
import java.util.List;

public class MeetingListActivity extends AppCompatActivity {

	// User Interface elements
	Toolbar mToolbar;
	RecyclerView mRecyclerView;
	FloatingActionButton mCreateMeetingButton;
	MeetingsRecyclerViewAdapter mMeetingsRecyclerViewAdapter;
	MeetingApiService mMeetingApiService = DI.getMeetingApiService();
	List<Meeting> mMeetingListAsArray = new ArrayList<>();
	MeetingRoom  mMeetingRoom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meeting_list);
		mToolbar = findViewById(R.id.toolbar);
		mRecyclerView = findViewById(R.id.list_meetings);
		mCreateMeetingButton = findViewById(R.id.create_meeting);
		setSupportActionBar(mToolbar);
		initList();
	}

	/**
	 * Initializing list of meetings
	 */
	private void initList() {
		mMeetingListAsArray = mMeetingApiService.getMeetings();
		mRecyclerView.setAdapter(new MeetingsRecyclerViewAdapter(mMeetingListAsArray));
/*
		mMeetingListAsArray.clear();
		mMeetingListAsArray.add();
		mMeetingListAsArray.addAll(mMeetingApiService.getMeetings());
*/
	}

	@Override
	public void onResume() {
		super.onResume();

	}
	private void setSupportActionBar(Toolbar toolbar) {
	}

	/**
	@OnClick(R.id.create_meeting)
	void createMeeting() {
		CreateMeetingActivity.navigate(this);
	}
	*/
}