package com.liondevlab.mareu.model;

import java.io.Serializable;
import java.util.Objects;


/**
 * MaRéu
 * Created by LioNDeVLaB on 25/01/2021
 * Model object representing a MeetingRoom implementing Serializable for details
 */
public class MeetingRoom implements Serializable {

	/** Identifier */
	private long id;
	/** Full name */
	private String roomName;
	/** Avatar */
	private MeetingRoomColor avatarColor;


	/**
	 * Constructor
	 * @param id type long unique id
	 * @param roomName String
	 * @param avatarColor MeetingRoomColor from enum
	 */
	public MeetingRoom(long id, String roomName, MeetingRoomColor avatarColor) {
		this.id = id;
		this.roomName = roomName;
		this.avatarColor = avatarColor;
	}

	public MeetingRoom(String roomName, MeetingRoomColor avatarColor) {
		this.roomName = roomName;
		this.avatarColor = avatarColor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String name) {
		this.roomName = name;
	}

	public MeetingRoomColor getAvatarColor() {
		return avatarColor;
	}

	public void setAvatarColor(MeetingRoomColor avatarColor) {
		this.avatarColor = avatarColor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MeetingRoom meetingRoom = (MeetingRoom) o;
		return Objects.equals(id, meetingRoom.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
