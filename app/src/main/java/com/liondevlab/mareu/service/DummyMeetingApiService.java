package com.liondevlab.mareu.service;

import com.liondevlab.mareu.model.Meeting;
import com.liondevlab.mareu.model.MeetingParticipant;
import com.liondevlab.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		return new ArrayList<>(mMeetings);
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

	@Override
	public ArrayList<Meeting> getFilteredMeetings(boolean isFilteredByRoom, String roomFiltered, boolean isFilteredByDate, Date dateFiltered) {
		ArrayList<Meeting> mFilteredMeetings = new ArrayList<>();
		if (isFilteredByRoom || isFilteredByDate) {
			for (int i = 0; i < getMeetings().size(); i++) {

				//Check room name of a meeting in the list
				String vMeetingRoomLocation = getMeetings().get(i).getLocation().getRoomName();
				//Check if the room name is equal to the filter
				boolean vFilteredRoomMatch = vMeetingRoomLocation.equals(vMeetingRoomLocation);
				//Get the date of the meeting in the list and those of the filter
				Calendar vMeetingDate = Calendar.getInstance();
				vMeetingDate.setTime(getMeetings().get(i).getStartTime());
				Calendar vFilteredMeetingDate = Calendar.getInstance();
				vFilteredMeetingDate.setTime(dateFiltered);
				//Compare dates to match
				boolean vFilteredDateMatch = vMeetingDate == vFilteredMeetingDate;

				if (isFilteredByRoom && isFilteredByDate) {
					//If room & date filter match, add meetings to list
					if (vFilteredRoomMatch && vFilteredDateMatch) {
						mFilteredMeetings.add(getMeetings().get(i));
					}
				} else if (isFilteredByRoom) {
					//If only room filter match, add meetings to list
					if (vFilteredRoomMatch) {
						mFilteredMeetings.add(getMeetings().get(i));
					}
				} else {
					//If only date filter match, add meetings to list
					if (vFilteredDateMatch) {
						mFilteredMeetings.add(getMeetings().get(i));
					}
				}
			}
		} else {
			mFilteredMeetings = getMeetings();
		}
		return mFilteredMeetings;
	}

}
