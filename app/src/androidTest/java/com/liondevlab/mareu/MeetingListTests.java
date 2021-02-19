package com.liondevlab.mareu;

import androidx.test.espresso.contrib.RecyclerViewActions;
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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.liondevlab.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test Class for List of Meetings
 */

@RunWith(AndroidJUnit4.class)
public class MeetingListTests {

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

}
