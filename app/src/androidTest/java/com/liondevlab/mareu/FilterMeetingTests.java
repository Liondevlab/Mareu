package com.liondevlab.mareu;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.liondevlab.mareu.ui.MeetingListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.liondevlab.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * MaRéu
 * Created by LioNDeVLaB on 19/02/2021
 */
@RunWith(AndroidJUnit4.class)
public class FilterMeetingTests {

	@Rule
	public ActivityTestRule<MeetingListActivity> mActivityTestRule =
			new ActivityTestRule<>(MeetingListActivity.class);

	@Before
	public void setUp() {
		MeetingListActivity activity = mActivityTestRule.getActivity();
		assertThat(activity,notNullValue());
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

}