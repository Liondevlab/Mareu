package com.liondevlab.mareu.ui;

import java.util.List;

/**
 * MaRéu
 * Created by LioNDeVLaB on 05/02/2021
 */
public interface CreateMeetingParticipantsRecyclerInterface {
	List<String> getNewMeetingParticipants();
	void addParticipantToList(String participant);
	void deleteParticipantFromList(String participant);

}
