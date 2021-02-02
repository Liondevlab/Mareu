package com.liondevlab.mareu.service;

import com.liondevlab.mareu.model.Meeting;
import com.liondevlab.mareu.model.MeetingParticipant;
import com.liondevlab.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * MaRéu
 * Created by LioNDeVLaB on 25/01/2021
 */
public class DummyMeetingApiService implements MeetingApiService{

	private ArrayList<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();
	private List<MeetingRoom> mMeetingRooms = DummyMeetingGenerator.generateMeetingRooms();
	private List<MeetingParticipant> mMeetingParticipants = DummyMeetingGenerator.generateMeetingParticipants();

	@Override
	public ArrayList<Meeting> getMeetings() {
		return mMeetings;
	}

	@Override
	public void createMeeting(Meeting meeting) {
		mMeetings.add(meeting);
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		mMeetings.remove(meeting);
	}

	@Override
	public List<MeetingRoom> getMeetingRooms() {
		return mMeetingRooms;
	}

	@Override
	public List<MeetingParticipant> getMeetingParticipants() {
		return mMeetingParticipants;
	}

}
