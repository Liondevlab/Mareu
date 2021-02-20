package com.liondevlab.mareu.ui;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.Date;

public class MeetingListActivity extends AppCompatActivity implements MeetingRecyclerInterface,MeetingFilteringDialog.OnValidateFilterListener  {

	// User Interface elements
	RecyclerView mRecyclerView;
	FloatingActionButton mCreateMeetingButton;
	MeetingsRecyclerViewAdapter mMeetingsRecyclerViewAdapter;
	final MeetingApiService mMeetingApiService = DI.getMeetingApiService();
	final ArrayList<Meeting> mMeetingListAsArray = new ArrayList<>();
	boolean mRoomFilterChecked = false;
	boolean mDateFilterChecked = false;
	String mRoomFilteredName;
	Date mDateFiltered;

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
		mMeetingListAsArray.clear();
		mMeetingListAsArray.addAll(mMeetingApiService.getFilteredMeetings(mRoomFilterChecked, mRoomFilteredName, mDateFilterChecked, mDateFiltered));
		mMeetingsRecyclerViewAdapter = new MeetingsRecyclerViewAdapter(mMeetingListAsArray, this);
		mRecyclerView.setAdapter(mMeetingsRecyclerViewAdapter);
		mMeetingsRecyclerViewAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		initList();
	}
	private void configureToolbar() {
		Toolbar toolbar = findViewById(R.id.meeting_toolbar);
		setSupportActionBar(toolbar);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item_meeting_filter) {
			showMeetingFilteringDialog();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showMeetingFilteringDialog() {
		FragmentManager fm = getSupportFragmentManager();
		MeetingFilteringDialog meetingFilteringDialog = MeetingFilteringDialog.newInstance("filter dialog");
		meetingFilteringDialog.show(fm, "meeting_filter_dialog");
	}

	@Override
	public void onValidateFilter(boolean pRoomFilterChecked, String pRoomFilteredName, boolean pDateFilterChecked, Date pDateFiltered) {
		mRoomFilterChecked = pRoomFilterChecked;
		mRoomFilteredName = pRoomFilteredName;
		mDateFilterChecked = pDateFilterChecked;
		mDateFiltered = pDateFiltered;
		initList();
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		mMeetingApiService.deleteMeeting(meeting);
		initList();
	}

}