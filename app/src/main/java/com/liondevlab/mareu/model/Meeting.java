package com.liondevlab.mareu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * MaRéu
 * Created by LioNDeVLaB on 25/01/2021
 * Model object representing a Meeting
 */
public class Meeting implements Serializable {

	/** Identifier */
	private long id;

	/** Reunion subject */
	private String subject;

	/** Location */
	private MeetingRoom location;

	/** Begin time */
	private Date startTime;

	/** End time */
	private Date endTime;

	/** Participant list */
	private List<MeetingParticipant> participants;


	/**
	 * Constructor
	 */
	public Meeting(long id, String subject, MeetingRoom location, Date startTime, Date endTime, List<MeetingParticipant> participants) {
		this.id = id;
		this.subject = subject;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.participants = participants;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return subject;
	}

	public void setName(String name) {
		this.subject = name;
	}

	public MeetingRoom getLocation() {
		return location;
	}

	public String getMeetingRoom() {
		return location.toString();
	}

	public void setLocation(MeetingRoom location) {
		this.location = location;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date beginTime) {
		this.startTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<MeetingParticipant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<MeetingParticipant> participants) {
		this.participants = participants;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Meeting meeting = (Meeting) o;
		return Objects.equals(id, meeting.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
