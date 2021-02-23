package com.liondevlab.mareu;

import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.model.Meeting;
import com.liondevlab.mareu.model.MeetingParticipant;
import com.liondevlab.mareu.model.MeetingRoom;
import com.liondevlab.mareu.service.DummyMeetingGenerator;
import com.liondevlab.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Tests
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

	private MeetingApiService service;

	@Before
	public void setup() {
		service = DI.getNewInstanceApiService();
	}

	@Test
	public void getMeetingWithSuccess() {
		List<Meeting> meetings = service.getMeetings();
		List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
		assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedMeetings.toArray())));
	}

	@Test
	public void createMeetingWithSuccess() {
		Meeting newMeeting = new Meeting(999, "Test", service.getMeetingRooms().get(1), new Date(), new Date(), service.getMeetingParticipants());
		service.createMeeting(newMeeting);
		service.getMeetings();
		assertTrue(service.getMeetings().contains(newMeeting));
	}

	@Test
	public void deleteMeetingWithSuccess() {
		Meeting meetingToDelete = service.getMeetings().get(0);
		service.deleteMeeting(meetingToDelete);
		assertFalse(service.getMeetings().contains(meetingToDelete));
	}

	@Test
	public void getMeetingRoomsWithSuccess() {
		List<MeetingRoom> meetingRooms = service.getMeetingRooms();
		List<MeetingRoom> expectedMeetingRooms = DummyMeetingGenerator.DUMMY_MEETINGROOMS;
		assertThat(meetingRooms, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedMeetingRooms.toArray())));
	}

	@Test
	public void getMeetingParticipantsWithSuccess() {
		List<MeetingParticipant> meetingParticipants = service.getMeetingParticipants();
		List<MeetingParticipant> expectedMeetingParticipants = DummyMeetingGenerator.DUMMY_PARTICIPANTS;
		assertThat(meetingParticipants, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedMeetingParticipants.toArray())));
	}

	@Test
	public void getFilterMeetingWithSuccess() {
		Meeting meetingTargetedToFilter = service.getMeetings().get(0);
		Date dateFiltered = meetingTargetedToFilter.getStartTime();
		String meetingRoomFiltered = meetingTargetedToFilter.getLocation().getRoomName();
		List<Meeting> filteredMeetings = service.getFilteredMeetings(true, meetingRoomFiltered, true, dateFiltered);
		assertTrue(filteredMeetings.contains(meetingTargetedToFilter));
	}

}