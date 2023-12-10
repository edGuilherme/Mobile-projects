package ufc.smd.esqueleto_placar

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import data.Placar
import org.junit.Rule
import org.junit.Test

class Teste5 {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(PlacarActivity::class.java, false, false)

    @Test
    fun testPausaCronometro() {

        val placar = Placar("Jogo sem Config", "0x0", "20/05/20 10h", false, "Time1", "Time2")

        val intent = Intent().apply {
            putExtra("placar", placar)
        }

        activityTestRule.launchActivity(intent)


        // Espera que a atividade seja carregada
        Espresso.onView(withId(R.id.tvCrono)).check(matches(withText("Tempo Restante: 299 segundos")))

        // Clica no botão de pausa
        Espresso.onView(withId(R.id.btPausa)).perform(click())

        // Espera um curto período de tempo
        Thread.sleep(2000)

        // Verifica se o valor de tvCrono não foi alterado
        Espresso.onView(withId(R.id.tvCrono)).check(matches(withText("Tempo Restante: 299 segundos")))

        // Clica no botão de pausa
        Espresso.onView(withId(R.id.btPausa)).perform(click())

        // Espera um curto período de tempo
        Thread.sleep(2000)

        // Verifica se o valor de tvCrono não foi alterado
        Espresso.onView(withId(R.id.tvCrono)).check(matches(withText("Tempo Restante: 297 segundos")))

    }
}

