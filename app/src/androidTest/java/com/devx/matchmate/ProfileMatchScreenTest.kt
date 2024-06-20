package com.devx.matchmate

import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.devx.domain.model.ProfileMatch
import com.devx.matchmate.ui.common.MatchMateAppBar
import com.devx.matchmate.ui.profileMatch.common.ProfileMatchItem
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ProfileMatchScreenTest {
    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verify_matchMateAppBar_isDisplayed() {
        composeTestRule.setContent {
            MatchMateAppBar(title = stringResource(id = R.string.app_bar_title))
        }
        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.app_bar_title))
            .assertIsDisplayed()
    }

    @Test
    fun verify_profileMatchItem_showAllRequiredInfo() {
        composeTestRule.setContent {
            ProfileMatchItem(
                profileMatch =
                    ProfileMatch(
                        userId = "1234",
                        name = "Swapnil Musale",
                        profilePicUrl = "https://www.google.com",
                        address = "Mumbai",
                    ),
                onProfileAccepted = {},
                onProfileDeclined = {},
            )
        }

        // verify user name is displayed
        composeTestRule.onNodeWithText(text = "Swapnil Musale").assertIsDisplayed()

        // verify address is displayed
        composeTestRule.onNodeWithText(text = "Mumbai").assertIsDisplayed()
    }
}
