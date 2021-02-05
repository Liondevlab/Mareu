package com.liondevlab.mareu.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.liondevlab.mareu.R;
import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.service.MeetingApiService;

import java.util.Arrays;
import java.util.List;

public class CreateMeetingActivity extends AppCompatActivity implements CreateMeetingParticipantsRecyclerInterface {

	MeetingApiService mMeetingApiService = DI.getMeetingApiService();
	CreateMeetingParticipantsRecyclerView mCreateMeetingParticipantsRecyclerView;
	List<String> mSearchParticipantsList = Arrays.asList("Paul", "Maxime", "Amandine", "Aurélie", "Vivianne", "Luc", "Alex", "Justine", "Caroline", "Stéphane", "Céline", "Michel");
	List<String> mParticipantsAdded;
	EditText mEditTextSubject;
	Spinner mRoomSpinner;
	TimePicker mStartTimePicker;
	TimePicker mEndTimePicker;
	AutoCompleteTextView mSearchParticipant;
	private Button mAddParticipantButton;
	private Button mValidateButton;
	private Button mCancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_meeting);
		mEditTextSubject = findViewById(R.id.create_meeting_subject);
		mAddParticipantButton = findViewById(R.id.create_meeting_add_participant_button);
		mValidateButton = findViewById(R.id.create_meeting_validate_button);
		mCancelButton = findViewById(R.id.create_meeting_cancel_button);
		mRoomSpinner = findViewById(R.id.create_meeting_room_spinner);
		mStartTimePicker = findViewById(R.id.create_meeting_start_time_picker);
		mEndTimePicker = findViewById(R.id.create_meeting_end_time_picker);
		/*mRoomSpinner.setOnItemSelectedListener(this);*/
		mStartTimePicker.setIs24HourView(true);
		mEndTimePicker.setIs24HourView(true);
		ArrayAdapter<String> participantsList = new ArrayAdapter<>(this,
				android.R.layout.simple_dropdown_item_1line, mSearchParticipantsList);
		mSearchParticipant = (AutoCompleteTextView) findViewById(R.id.create_meeting_autocomplete_search_participant);
		initializeSearchParticipant(mSearchParticipant);
		mSearchParticipant.setAdapter(participantsList);
		addRoomsInSpinner();
		addListenerOnButton();
	}

	private void initializeSearchParticipant(final AutoCompleteTextView pSearchParticipant) {

		pSearchParticipant.setThreshold(1);
		pSearchParticipant.setDropDownWidth(-1);
		pSearchParticipant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String vParticipantSelected = mSearchParticipantsList.get(position);
				mParticipantsAdded.add(vParticipantSelected);
			}
		});

	}

	public void addRoomsInSpinner() {
		mRoomSpinner = mRoomSpinner.findViewById(R.id.create_meeting_room_spinner);
		//List creation for rooms in spinner
		String[] roomList = new String[]{
				"Mario", "Luigi", "Toad", "Peach", "Daisy", "Yoshi", "Wario", "Waluigi", "Koopa", "Bowser"
		};
		ArrayAdapter adapter = new ArrayAdapter(CreateMeetingActivity.this, android.R.layout.simple_list_item_1, roomList);/*this, android.R.layout.simple_spinner_item,	roomList*/
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mRoomSpinner.setAdapter(adapter);
	}

	public void addListenerOnButton() {
		mRoomSpinner = mRoomSpinner.findViewById(R.id.create_meeting_room_spinner);
		mAddParticipantButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(CreateMeetingActivity.this, "OK", Toast.LENGTH_LONG).show();
			}
		});
		mValidateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(CreateMeetingActivity.this, "OK", Toast.LENGTH_LONG).show();
			}
		});
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CreateMeetingActivity.this.finish();
			}
		});
	}

	@Override
	public List<String> getNewMeetingParticipants() {
		return mParticipantsAdded;
	}

	@Override
	public void addParticipantToList(String participant){

	}

	@Override
	public void deleteParticipantFromList(String participant) {

	}

}