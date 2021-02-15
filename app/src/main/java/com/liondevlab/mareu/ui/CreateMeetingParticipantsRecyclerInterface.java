package com.liondevlab.mareu.ui;

import com.liondevlab.mareu.model.MeetingParticipant;

import java.util.List;

/**
 * MaRéu
 * Created by LioNDeVLaB on 05/02/2021
 */
public interface CreateMeetingParticipantsRecyclerInterface {
	List<MeetingParticipant> getNewMeetingParticipants(List<String> Participants);
	void addParticipantToList(String participant);
	void deleteParticipantFromList(String participant);

}
