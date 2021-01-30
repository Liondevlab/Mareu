package com.liondevlab.mareu.service;

import com.liondevlab.mareu.model.Meeting;
import com.liondevlab.mareu.model.MeetingParticipant;
import com.liondevlab.mareu.model.MeetingRoom;

import java.util.List;

/**
 * MaRéu
 * Created by LioNDeVLaB on 25/01/2021
 */
public interface MeetingApiService {
	/**
	 * Get Meetings
	 * @return {@link List}
	 */
	List<Meeting> getMeetings();

	/**
	 * Create a Meeting
	 */
	void createMeeting(Meeting meeting);

	/**
	 * Delete a Meeting
	 */
	void deleteMeeting(Meeting meeting);

	/**
	 * Get Meeting Rooms
	 * @return {@link List}
	 */
	List<MeetingRoom> getMeetingRooms();

	/**
	 * Get  Meeting Participants
	 * @return {@link List}
	 */
	List<MeetingParticipant> getMeetingParticipants();

}
