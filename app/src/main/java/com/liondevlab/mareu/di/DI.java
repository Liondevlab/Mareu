package com.liondevlab.mareu.di;

import com.liondevlab.mareu.service.DummyMeetingApiService;
import com.liondevlab.mareu.service.MeetingApiService;

/**
 * MaRéu
 * Created by LioNDeVLaB on 25/01/2021
 * Dependency injector to get instance of services
 */
public class DI {

	private static final MeetingApiService service = new DummyMeetingApiService();

	/**
	 * Get an instance on @{@link MeetingApiService}
	 * @return
	 */
	public static MeetingApiService getMeetingApiService() {
		return service;
	}

	/**
	 * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
	 * @return
	 */
	public static MeetingApiService getNewInstanceApiService() {
		return new DummyMeetingApiService();
	}
}
