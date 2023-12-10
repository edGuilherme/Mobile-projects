package ufc.smd.esqueleto_placar

import android.content.Intent
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import data.Placar
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class Teste3 {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(PlacarActivity::class.java, false, false)

    @Test
    fun testAumentarPlacar1() {
        val placar = Placar("Jogo sem Config", "0x0", "20/05/20 10h", false, "Time1", "Time2")

        val intent = Intent().apply {
            putExtra("placar", placar)
        }

        activityTestRule.launchActivity(intent)

        onView(withId(R.id.tvPlacar1)).perform(click())

        onView(withId(R.id.tvPlacar1)).check(matches(withText("1")))
    }

    @Test
    fun testAumentarPlacar2() {
        val placar = Placar("Jogo sem Config", "0x0", "20/05/20 10h", false, "Time1", "Time2")

        val intent = Intent().apply {
            putExtra("placar", placar)
        }

        activityTestRule.launchActivity(intent)


        onView(withId(R.id.tvPlacar2)).perform(click())

        onView(withId(R.id.tvPlacar2)).check(matches(withText("1")))
    }
}


