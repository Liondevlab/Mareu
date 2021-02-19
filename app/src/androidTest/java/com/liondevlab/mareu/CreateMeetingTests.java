package com.liondevlab.mareu;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.rule.ActivityTestRule;

import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.service.MeetingApiService;
import com.liondevlab.mareu.ui.MeetingListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.liondevlab.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * MaRéu
 * Created by LioNDeVLaB on 19/02/2021
 */
public class CreateMeetingTests {
	//This is fixed
	private static final int ITEMS_COUNT = 5;
	private MeetingApiService mMeetingApiService = DI.getNewInstanceApiService();

	@Rule
	public ActivityTestRule<MeetingListActivity> mActivityTestRule =
			new ActivityTestRule(MeetingListActivity.class);

	@Before
	public void setUp() {
		MeetingListActivity activity = mActivityTestRule.getActivity();
		assertThat(activity,notNullValue());
	}

	/**
	 * Trying to create a meeting without Subject list should show a toast message
	 */
	//TODO
	@Test
	public void CreateMeeting_withoutSubject_shouldShowAToastMessage() {
		// Given : We try to create meeting without Subject or Participants
		onView(withId(R.id.create_meeting_button)).perform(click());
		// When : Trying to validate without subject
		onView(withId(R.id.create_meeting_validate_button)).perform(scrollTo(), click());
		// Then : A Toast message must appear with text : "Il manque le sujet de la réunion"
		onView(withText("Il manque le sujet de la réunion"))
				.inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
	}

	/**
	 * Trying to create a meeting without Participants should show a toast message
	 */
	//TODO
	@Test
	public void CreateMeeting_withoutParticipants_shouldShowAToastMessage() {
		// Given : We try to create meeting without Subject or Participants
		onView(withId(R.id.create_meeting_button)).perform(click());
		// When : filling subject but not participants
		onView(allOf(withId(R.id.create_meeting_subject))).perform(typeText("Subject"));
		onView(withId(R.id.create_meeting_validate_button)).perform(scrollTo(), click());
		// Then : A toast message must appear with text : "Il n'y a pas de participants"
		onView(withText("Il n'y a pas de participants"))
				.inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
	}

	/**
	 * When creating a meeting with success, the view should show the created meeting in the list
	 */
	@Test
	public void CreateMeeting_withSuccess_shouldAddTheNewMeetingInTheList() {
		// Given : We create meeting with success
		onView(withId(R.id.create_meeting_button)).perform(click());
		// When : filling the form
		onView(allOf(withId(R.id.create_meeting_subject))).perform(typeText("Subject"));
		onView(withId(R.id.create_meeting_autocomplete_search_participant)).perform(scrollTo(), typeText("l"));
		onData(equalTo("Luc@lamzone.com")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
		onView(withId(R.id.create_meeting_validate_button)).perform(scrollTo(), click());
		// Then : The meeting list size should be 6
		onView(withId(R.id.list_meetings)).check(withItemCount(6));
	}

	/**
	 * When creating a meeting overlaps another one, the creating activity should stay with a toast message
	 */
	@Test
	public void CreateMeeting_withOverlap_shouldShowAnErrorMessageAndStayOnCreateMeetingActivity() {
		// Given : We create meeting who overlaps another one
		onView(withId(R.id.create_meeting_button)).perform(click());
		// When : filling the form with overlap
		onView(allOf(withId(R.id.create_meeting_subject))).perform(typeText("Subject"));
		onView(withId(R.id.create_meeting_start_date_picker)).perform(scrollTo(), PickerActions.setDate(2021, 2, 15));
		onView(withId(R.id.create_meeting_start_time_picker)).perform(scrollTo(), PickerActions.setTime(16, 0));
		onView(withId(R.id.create_meeting_end_time_picker)).perform(scrollTo(), PickerActions.setTime(17, 0));
		onView(withId(R.id.create_meeting_autocomplete_search_participant)).perform(scrollTo(), typeText("l"));
		onData(equalTo("Luc@lamzone.com")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
		onView(withId(R.id.create_meeting_validate_button)).perform(scrollTo(), click());
		// Then : A toast message must appear with text : "L'horaire sélectionné entre en conflit avec une réunion déjà programmée dans cette salle"
		onView(withText("L'horaire sélectionné entre en conflit avec une réunion déjà programmée dans cette salle"))
				.inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
	}

}