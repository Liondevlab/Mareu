package com.liondevlab.mareu.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.liondevlab.mareu.R;
import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.service.MeetingApiService;

public class CreateMeetingActivity extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener*/ {

	MeetingApiService mMeetingApiService = DI.getMeetingApiService();
	Spinner mRoomSpinner;
	private Button mValidateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_meeting);
		mValidateButton = findViewById(R.id.create_meeting_validate_button);
		mRoomSpinner = findViewById(R.id.create_meeting_room_spinner);
		/*mRoomSpinner.setOnItemSelectedListener(this);*/
		addRoomsInSpinner();
		addListenerOnButton();
	}



	public void addRoomsInSpinner() {
		mRoomSpinner = mRoomSpinner.findViewById(R.id.create_meeting_room_spinner);
		//List creation for rooms in spinner
		String[] roomList = new String[]{
				"Mario", "Luigi", "Toad", "Peach", "Daisy", "Yoshi", "Wario", "Waluigi", "Koopa", "Bowser"
		};

        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
		ArrayAdapter adapter = new ArrayAdapter(CreateMeetingActivity.this, android.R.layout.simple_spinner_item, roomList);/*this, android.R.layout.simple_spinner_item,	roomList*/

		/* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Enfin on passe l'adapter au Spinner et c'est tout
		mRoomSpinner.setAdapter(adapter);

	}

	//get the selected dropdown list value
	public void addListenerOnButton() {

		mRoomSpinner = mRoomSpinner.findViewById(R.id.create_meeting_room_spinner);
		mValidateButton = mValidateButton.findViewById(R.id.create_meeting_validate_button);

		mValidateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(CreateMeetingActivity.this, "OK", Toast.LENGTH_LONG).show();

			}

		});
	}

/*	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}*/
}