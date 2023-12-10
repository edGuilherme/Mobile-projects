package data

import android.os.CountDownTimer

object CronometroManager {
    var countDownTimer: CountDownTimer? = null
    var isCronometroPausado = false
    var tempoRestante: Long = 0
}