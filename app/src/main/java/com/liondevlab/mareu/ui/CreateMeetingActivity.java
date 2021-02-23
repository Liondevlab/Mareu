package com.liondevlab.mareu.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.liondevlab.mareu.R;
import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.model.Meeting;
import com.liondevlab.mareu.model.MeetingParticipant;
import com.liondevlab.mareu.model.MeetingRoom;
import com.liondevlab.mareu.model.MeetingRoomColor;
import com.liondevlab.mareu.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateMeetingActivity extends AppCompatActivity implements CreateMeetingParticipantsRecyclerInterface,MeetingRecyclerInterface, AdapterView.OnItemClickListener {

	final MeetingApiService mMeetingApiService = DI.getMeetingApiService();
	Meeting mMeeting;
	String mMeetingRoomName;
	MeetingRoomColor mMeetingRoomColor;
	MeetingRoom mMeetingRoom;
	final List<MeetingParticipant> mMeetingParticipants = new ArrayList<>();
	RecyclerView mRecyclerView;
	CreateMeetingParticipantsRecyclerView mCreateMeetingParticipantsRecyclerView;
	String mParticipantSelected;
	final ArrayList<String> mParticipantsAdded = new ArrayList<>();
	final ArrayList<String> mParticipantSelectedList = new ArrayList<>();
	EditText mEditTextSubject;
	Spinner mRoomSpinner;
	DatePicker mStartDatePicker;
	TimePicker mStartTimePicker, mEndTimePicker;
	Date mStartTime, mEndTime;
	AutoCompleteTextView mSearchParticipant;
	final List<String> mSearchParticipantsList = Arrays.asList(
			"Paul@lamzone.com",
			"Maxime@lamzone.com",
			"Amandine@lamzone.com",
			"Aurélie@lamzone.com",
			"Vivianne@lamzone.com",
			"Luc@lamzone.com",
			"Alex@lamzone.com",
			"Justine@lamzone.com",
			"Caroline@lamzone.com",
			"Stéphane@lamzone.com",
			"Céline@lamzone.com",
			"Michel@lamzone.com");
	private Button mValidateButton;
	private Button mCancelButton;
	private boolean mIsSomeFieldsEmpty;
	private boolean mIsMeetingOverlaps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_meeting);
		mEditTextSubject = findViewById(R.id.create_meeting_subject);
		mValidateButton = findViewById(R.id.create_meeting_validate_button);
		mCancelButton = findViewById(R.id.create_meeting_cancel_button);
		mRoomSpinner = findViewById(R.id.create_meeting_room_spinner);
		mStartDatePicker = findViewById(R.id.create_meeting_start_date_picker);
		mStartTimePicker = findViewById(R.id.create_meeting_start_time_picker);
		mEndTimePicker = findViewById(R.id.create_meeting_end_time_picker);
		mStartTimePicker.setIs24HourView(true);
		mEndTimePicker.setIs24HourView(true);
		mRecyclerView = findViewById(R.id.create_meeting_added_participant_recyclerview);
		ArrayAdapter<String> participantsList = new ArrayAdapter<>(this,
				android.R.layout.simple_dropdown_item_1line, mSearchParticipantsList);
		mSearchParticipant = findViewById(R.id.create_meeting_autocomplete_search_participant);
		initializeSearchParticipant(mSearchParticipant);
		mSearchParticipant.setAdapter(participantsList);
		mSearchParticipant.setOnItemClickListener(this);
		addRoomsInSpinner();
		addListenerOnButton();
	}

	// Set behavior of AutoCompleteTextView
	private void initializeSearchParticipant(final AutoCompleteTextView pSearchParticipant) {
		pSearchParticipant.setThreshold(1);
		pSearchParticipant.setDropDownWidth(-1);
	}

	// Refresh participant list after add or suppress an item
	private void refreshList() {
		mParticipantSelectedList.clear();
		mParticipantSelectedList.addAll(mParticipantsAdded);
		mCreateMeetingParticipantsRecyclerView = new CreateMeetingParticipantsRecyclerView(mParticipantSelectedList, this);
		mRecyclerView.setAdapter(mCreateMeetingParticipantsRecyclerView);
		mCreateMeetingParticipantsRecyclerView.notifyDataSetChanged();
		mSearchParticipant.setText("");
	}

	// Add rooms in spinner
	public void addRoomsInSpinner() {
		mRoomSpinner = mRoomSpinner.findViewById(R.id.create_meeting_room_spinner);
		// List for rooms in spinner
		String[] roomList = new String[]{
				"Mario", "Luigi", "Toad", "Peach", "Daisy", "Yoshi", "Wario", "Waluigi", "Koopa", "Bowser"
		};
		ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateMeetingActivity.this, android.R.layout.simple_list_item_1, roomList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mRoomSpinner.setAdapter(adapter);
	}

	public void addListenerOnButton() {
		mValidateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(
							AdapterView<?> adapterView, View view,
							int i, long l) {
						mMeetingRoomName = mRoomSpinner.getItemAtPosition(i).toString();
					}

					public void onNothingSelected(
							AdapterView<?> adapterView) {
					}
				});
				mMeetingRoomName = mRoomSpinner.getSelectedItem().toString();
				generateMeeting();
			}
		});
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CreateMeetingActivity.this.finish();
			}
		});
	}

	// Check if meeting overlap another one by room name
	private void getIfMeetingOverlaps(MeetingRoom meetingRoom, Date startTime, Date endTime) {
		mIsMeetingOverlaps = false;
		for (int i = 0; i < mMeetingApiService.getMeetings().size(); i++){
			Date existingMeetingBeginTime = mMeetingApiService.getMeetings().get(i).getStartTime();
			Date existingMeetingEndTime = mMeetingApiService.getMeetings().get(i).getEndTime();

			if (meetingRoom.getRoomName().equals(mMeetingApiService.getMeetings().get(i).getLocation().getRoomName())){
				if (startTime.before(existingMeetingEndTime) && existingMeetingBeginTime.before(endTime))
				mIsMeetingOverlaps = true;
			}
		}
	}

	// Generate meeting after gathering information
	private void generateMeeting() {
		getIfSomeFieldsAreEmpty(mEditTextSubject.getText().toString(), mParticipantsAdded);
		if (mIsSomeFieldsEmpty) {
			ScrollView sv = findViewById(R.id.create_meeting_scrollview);
			sv.scrollTo(0, sv.getTop());
		} else {
			String vSubject = mEditTextSubject.getText().toString();
			long vMeetingId = System.currentTimeMillis();
			generateMeetingRoomColor(mMeetingRoomName);
			getNewMeetingParticipants(mParticipantsAdded);
			mMeetingRoom = new MeetingRoom(mMeetingRoomName, mMeetingRoomColor);
			mStartTime = getStartTime(mStartDatePicker, mStartTimePicker);
			mEndTime = getEndTime(mStartDatePicker, mEndTimePicker);
			mMeeting = new Meeting(vSubject, mStartTime, mEndTime);
			mMeeting.setId(vMeetingId);
			mMeeting.setLocation(mMeetingRoom);
			mMeeting.setParticipants(mMeetingParticipants);
			getIfMeetingOverlaps(mMeetingRoom, mStartTime, mEndTime);
			if (mIsMeetingOverlaps) {
				Toast.makeText(CreateMeetingActivity.this, "L'horaire sélectionné entre en conflit avec une réunion déjà programmée dans cette salle", Toast.LENGTH_LONG).show();
			} else {
				createMeeting(mMeeting);
				Toast.makeText(CreateMeetingActivity.this, "La réunion a été ajoutée", Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}

	// Check if subject or participant list are empty
	private void getIfSomeFieldsAreEmpty(String subject, ArrayList<String> participantsAdded) {
		if (subject.isEmpty()) {
			Toast.makeText(CreateMeetingActivity.this, "Il manque le sujet de la réunion", Toast.LENGTH_LONG).show();
			mIsSomeFieldsEmpty = true;
		} else if (participantsAdded.size() == 0) {
			Toast.makeText(CreateMeetingActivity.this, "Il n'y a pas de participants", Toast.LENGTH_LONG).show();
			mIsSomeFieldsEmpty = true;
		} else {
			mIsSomeFieldsEmpty = false;
		}
	}

	// Give the avatar color by room name
	private void generateMeetingRoomColor(String pRoomName) {
		mMeetingRoomName = pRoomName;
		switch (mMeetingRoomName) {
			case "Mario":
				mMeetingRoomColor = MeetingRoomColor.RED;
				break;
			case "Luigi":
				mMeetingRoomColor = MeetingRoomColor.GREEN;
				break;
			case "Toad":
				mMeetingRoomColor = MeetingRoomColor.BLUE;
				break;
			case "Peach":
				mMeetingRoomColor = MeetingRoomColor.PINK;
				break;
			case "Daisy":
				mMeetingRoomColor = MeetingRoomColor.PEACH;
				break;
			case "Yoshi":
				mMeetingRoomColor = MeetingRoomColor.YELLOW;
				break;
			case "Wario":
				mMeetingRoomColor = MeetingRoomColor.DARK_RED;
				break;
			case "Waluigi":
				mMeetingRoomColor = MeetingRoomColor.PURPLE;
				break;
			case "Koopa":
				mMeetingRoomColor = MeetingRoomColor.LIGHT_BLUE;
				break;
			case "Bowser":
				mMeetingRoomColor = MeetingRoomColor.ORANGE;
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + mMeetingRoom.getRoomName());
		}
	}

	// Transform DatePicker and TimePicker in a Date for Start
	public static java.util.Date getStartTime(DatePicker startDatePicker, TimePicker startTimePicker) {
		Calendar calendar = Calendar.getInstance();
		int day = startDatePicker.getDayOfMonth();
		int month = startDatePicker.getMonth();
		int year = startDatePicker.getYear();
		int hour = startTimePicker.getCurrentHour();
		int minute = startTimePicker.getCurrentMinute();
		calendar.set(year, month, day, hour, minute);

		return calendar.getTime();
	}

	// Transform DatePicker and TimePicker in a Date for End
	public static java.util.Date getEndTime(DatePicker startDatePicker, TimePicker endTimePicker) {
		Calendar calendar = Calendar.getInstance();
		int day = startDatePicker.getDayOfMonth();
		int month = startDatePicker.getMonth();
		int year = startDatePicker.getYear();
		int hour = endTimePicker.getCurrentHour();
		int minute = endTimePicker.getCurrentMinute();
		calendar.set(year, month, day, hour, minute);

		return calendar.getTime();
	}


	private void getNewMeetingParticipants(@NonNull List<String> participant) {
		for (int i = 0; i < participant.size(); i++) {
			String vParticipant = participant.get(i);
			MeetingParticipant vMeetingParticipant = new MeetingParticipant(vParticipant);
			mMeetingParticipants.add(vMeetingParticipant);
		}
	}

	private void addParticipantToList() {
		mParticipantsAdded.add(mParticipantSelected);
	}

	@Override
	public void deleteParticipantFromList(String participant) {
		mParticipantsAdded.remove(participant);
		mParticipantSelectedList.clear();
		mParticipantSelectedList.addAll(mParticipantsAdded);
		mCreateMeetingParticipantsRecyclerView.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mParticipantSelected = parent.getItemAtPosition(position).toString();
		if (mParticipantsAdded.contains(mParticipantSelected)) {
			Toast.makeText(CreateMeetingActivity.this, "L'utilisateur est déja dans la liste...", Toast.LENGTH_LONG).show();
			mSearchParticipant.setText("");
		} else {
			addParticipantToList();
			refreshList();
		}
	}

	@Override
	public void deleteMeeting(Meeting meeting) {

	}

	private void createMeeting(Meeting meeting) {
		mMeetingApiService.createMeeting(meeting);
	}
}