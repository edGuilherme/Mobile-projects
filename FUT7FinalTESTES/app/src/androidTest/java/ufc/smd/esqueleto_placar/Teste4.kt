package ufc.smd.esqueleto_placar

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.snackbar.Snackbar
import data.Placar
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ufc.smd.esqueleto_placar.PlacarActivity

class Teste4 {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(PlacarActivity::class.java, false, false)

    @Test
    fun testSnackbar() {

        val placar = Placar("Jogo sem Config", "0x0", "20/05/20 10h", false, "Time1", "Time2")

        val intent = Intent().apply {
            putExtra("placar", placar)
        }

        activityTestRule.launchActivity(intent)

        onView(withId(R.id.btSalvarPlacar)).perform(click())

        onView(withText("Placar salvo com sucesso!")).check(matches(isDisplayed()))

    }
}
