@file:Suppress("DEPRECATION")

package ufc.smd.esqueleto_placar

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Teste1 {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testButtonClickOpensConfigActivity() {
        // Clique no botão "btIniciar" na MainActivity
        Espresso.onView(ViewMatchers.withId(R.id.btIniciar)).perform(ViewActions.click())

        // Verifique se uma intent para ConfigActivity foi lançada
        Intents.intended(IntentMatchers.hasComponent(ConfigActivity::class.java.name))
    }
}
