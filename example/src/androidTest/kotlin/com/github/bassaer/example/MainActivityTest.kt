package com.github.bassaer.example

import android.content.Intent
import androidx.test.espresso.DataInteraction
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.github.bassaer.example.util.lazyActivityScenarioRule
import org.hamcrest.Matchers.anything
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MainActivity Test
 * Created by nakayama on 2017/07/30.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val rule = lazyActivityScenarioRule<MainActivity>(launchActivity = false)

    @Test
    fun checkMenuList() {
        rule.launch()
        lateinit var menu: Array<String>
        rule.getScenario().onActivity {
            menu = it.gettMenu()
        }
        for (i in menu.indices) {
            onRow(i).check(matches(withText(menu[i])))
        }
    }

    private fun onRow(position: Int): DataInteraction {
        return onData(anything())
                .inAdapterView(withId(R.id.menu_list)).atPosition(position)
    }
}