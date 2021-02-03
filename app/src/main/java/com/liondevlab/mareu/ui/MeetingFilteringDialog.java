package com.liondevlab.mareu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.liondevlab.mareu.R;
import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.service.MeetingApiService;


public class MeetingFilteringDialog extends DialogFragment {


	MeetingApiService mMeetingApiService = DI.getMeetingApiService();
	Spinner mRoomSpinner;
	private Button mValidateButton;



	public void EditNameDialogFragment() {

		// Empty constructor is required for DialogFragment

		// Make sure not to add arguments to the constructor

		// Use `newInstance` instead as shown below

	}



	public static MeetingFilteringDialog newInstance(String title) {

		MeetingFilteringDialog frag = new MeetingFilteringDialog();

		Bundle args = new Bundle();

		args.putString("title", title);

		frag.setArguments(args);

		return frag;

	}



	@Override

	public View onCreateView(LayoutInflater inflater, ViewGroup container,

	                         Bundle savedInstanceState) {

		return inflater.inflate(R.layout.dialog_box_meeting_filtering, container);

	}



	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mRoomSpinner = view.findViewById(R.id.filter_meeting_room_spinner);
		getActivity();
		addRoomsInSpinner();
		addListenerOnButton();

	}

	public void addRoomsInSpinner() {

		mRoomSpinner = mRoomSpinner.findViewById(R.id.filter_meeting_room_spinner);
		//Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
		String[] roomList = new String[]{
				"Mario", "Luigi", "Toad", "Peach", "Daisy", "Yoshi", "Wario", "Waluigi", "Koopa", "Bowser"
		};

        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
		ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, roomList);/*this, android.R.layout.simple_spinner_item,	roomList*/

		/* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Enfin on passe l'adapter au Spinner et c'est tout
		mRoomSpinner.setAdapter(adapter);

	}

	//get the selected dropdown list value
	public void addListenerOnButton() {

		mRoomSpinner = mRoomSpinner.findViewById(R.id.filter_meeting_room_spinner);
		//mValidateButton = mValidateButton.findViewById(R.id.validate_filter_button);

/*		mValidateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				//Toast.makeText(MeetingFilteringDialog.this, "OK", Toast.LENGTH_LONG);

			}

		});*/
	}


}