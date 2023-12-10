@file:Suppress("DEPRECATION")

package ufc.smd.esqueleto_placar

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Teste2 {

    @get:Rule
    var activityRule = ActivityTestRule(ConfigActivity::class.java)

    @Test
    fun testTypeTextInTeamName1() {
        // Digitar o texto "Vasco" no campo editTextTeamName1
        val teamName1 = "Vasco"
        onView(withId(R.id.editTextTeamName1)).perform(typeText(teamName1))

        // Verificar se o texto "Vasco" foi digitado corretamente
        onView(withId(R.id.editTextTeamName1)).check(matches(withText("Vasco")))
    }

    @Test
    fun testTypeTextInTeamName2() {
        // Digitar o texto "Cruzeiro" no campo editTextTeamName2
        val teamName2 = "Cruzeiro"
        onView(withId(R.id.editTextTeamName2)).perform(typeText(teamName2))

        // Verificar se o texto "Cruzeiro" foi digitado corretamente
        onView(withId(R.id.editTextTeamName2)).check(matches(withText("Cruzeiro")))
    }
}


