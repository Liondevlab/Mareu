package com.liondevlab.mareu;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.liondevlab.mareu.di.DI;
import com.liondevlab.mareu.service.MeetingApiService;
import com.liondevlab.mareu.ui.MeetingListActivity;
import com.liondevlab.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
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
 * Test Class for List of Meetings
 */

@RunWith(AndroidJUnit4.class)
public class MeetingTests {

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
	 *  We ensure that our recyclerview is displaying at least one item
	 */
	@Test
	public void MeetingList_shouldNotBeEmpty() {
		onView(withId(R.id.list_meetings)).check(matches(hasMinimumChildCount(1)));
		onView(ViewMatchers.hasMinimumChildCount(1));
	}

	/**
	 * When we delete an item, the item is no more shown
	 */
	@Test
	public void MeetingList_deleteAction_shouldRemoveItem() {
		// Given : Remove element 1
		onView(withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT));
		// When performing a click on the delete button
		onView(withId(R.id.list_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
		// Then the number of items must be 4
		onView(withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT-1));
	}

	/**
	 * When we filter by room, the list show only meetings in this room
	 */
	@Test
	public void MeetingList_filterByRoom_shouldShowOnlyMeetingsInThisRoom() {
		// When : We filter meetings by meeting room named "Mario"
		onView(withId(R.id.item_meeting_filter)).perform(click());
		onView(withId(R.id.filter_by_room_checkbox)).perform(click());
		onView(withId(R.id.validate_filter_button)).perform(click());
		// Then : Must be one meeting left on the view
		onView(withId(R.id.list_meetings)).check(withItemCount(1));
	}

	/**
	 * When we filter by date, the list show only meetings at this date
	 * And when we Re validate filter without any checked box, the complete meeting list is back
	 */
	@Test
	public void MeetingList_filterByDate_shouldShowOnlyMeetingsAtThisDate() {
		// When : We filter meetings by date named "Mario"
		onView(withId(R.id.item_meeting_filter)).perform(click());
		onView(withId(R.id.filter_by_date_checkbox)).perform(click());
		onView(withId(R.id.filter_date_datepicker)).perform(PickerActions.setDate(2021, 2, 15));
		onView(withId(R.id.validate_filter_button)).perform(click());
		// Then : Must be one meeting left on the view
		onView(withId(R.id.list_meetings)).check(withItemCount(2));
		// When : We filter nothing (checkbox unchecked)
		onView(withId(R.id.item_meeting_filter)).perform(click());
		onView(withId(R.id.validate_filter_button)).perform((click()));
		// Then : The full meeting list is back
		onView(withId(R.id.list_meetings)).check(withItemCount(5));
	}

	/**
	 * Trying to create a meeting without Subject list should show a toast message
	 */
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
