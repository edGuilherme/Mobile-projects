package ufc.smd.esqueleto_placar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.TextView
import data.Placar
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets

import android.os.CountDownTimer
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class PlacarActivity : AppCompatActivity() {
    lateinit var placar: Placar
    lateinit var tvResultadoJogo: TextView
    var game = 0

    var countDownTimer: CountDownTimer? = null
    lateinit var tvCrono: TextView

    var isCronometroPausado = false
    var tempoRestante: Long = 0

    // CTRL Z
    val historicoPlacar1 = mutableListOf<String>()
    val historicoPlacar2 = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)
        placar = getIntent().getExtras()?.getSerializable("placar") as Placar
        tvResultadoJogo = findViewById(R.id.tvPlacar1)
        //Mudar o nome da partida
        val tvNomePartida = findViewById(R.id.tvNomePartida2) as TextView
        tvNomePartida.text = placar.nome_partida
        //ultimoJogos()

        // Mudar time 1
        val time1TextView = findViewById<TextView>(R.id.time1)
        time1TextView.text = placar.tvTime1

        // Mudar time 2
        val time2TextView = findViewById<TextView>(R.id.time2)
        time2TextView.text = placar.tvTime2

        tvCrono = findViewById(R.id.tvCrono)
        val sharedFilename = "configPlacar"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        val hasTimer = sp.getBoolean("has_timer", false)
        val tempoSegundos =
            sp.getLong("tempo", 300) // Valor padrão de 300 segundos (5 minutos) caso não exista

        if (hasTimer) {
            tvCrono = findViewById(R.id.tvCrono)
            iniciarCronometro(tempoSegundos) // Iniciar o cronômetro com o valor lido do SharedPreferences
        }
    }

/*
    override fun onPause() {
        super.onPause()

        val placar1TextView = findViewById<TextView>(R.id.tvPlacar1)
        val placar2TextView = findViewById<TextView>(R.id.tvPlacar2)


        // Salve o tempo restante do cronômetro nas preferências compartilhadas
        val sharedFilename = "CronometroPreferences"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        val edShared = sp.edit()
       // edShared.putLong("tempoRestante", tempoRestante)
        edShared.putInt("placar1", placar1TextView.text.toString().toInt())
        edShared.putInt("placar2", placar2TextView.text.toString().toInt())
        edShared.commit()

        // Pausa o cronômetro
      //  if (countDownTimer != null && !isCronometroPausado) {
       //     countDownTimer?.cancel()
      //  }
    }

    override fun onResume() {
        super.onResume()

        val placar1TextView = findViewById<TextView>(R.id.tvPlacar1)
        val placar2TextView = findViewById<TextView>(R.id.tvPlacar2)

        // Recupere o tempo restante do cronômetro das preferências compartilhadas
        val sharedFilename = "CronometroPreferences"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)

        val placar1Salvo = sp.getInt("placar1", 0)
        val placar2Salvo = sp.getInt("placar2", 0)

        placar1TextView.text = placar1Salvo.toString()
        placar2TextView.text = placar2Salvo.toString()



       // val tempoRestanteSalvo = sp.getLong("tempoRestante", 0)

        // Se o tempo restante for maior que zero, retome o cronômetro com o tempo restante salvo
       // if (tempoRestanteSalvo > 0) {
       //     iniciarCronometro(tempoRestanteSalvo)
       // }
    }
*/
    fun iniciarCronometro(duracao: Long) {
        if (!isCronometroPausado) {
            tempoRestante = duracao
        }

        countDownTimer = object : CountDownTimer(tempoRestante, 1000) {
            var onFinishCalled = false
            override fun onTick(millisUntilFinished: Long) {
                tempoRestante = millisUntilFinished
                val segundosRestantes = millisUntilFinished / 1000
                tvCrono.text = "Tempo Restante: $segundosRestantes segundos"

                if (segundosRestantes <= 0 && !onFinishCalled) {
                    onFinish()
                    onFinishCalled = true
                }
            }

            override fun onFinish() {
               // tempoRestante = 0
               // tvCrono.text = "Tempo Restante: 0 segundos"
                // Faça algo quando o cronômetro terminar
                //salva o jogo
               openPenalti()
                //finaliza
                finish()
            }
        }

        countDownTimer?.start()
    }


    fun btPausa(view: View) {
        isCronometroPausado = !isCronometroPausado

        if (isCronometroPausado) {
            countDownTimer?.cancel()
        } else {
            iniciarCronometro(tempoRestante)
        }
    }


    fun alteraPlacar(view: View) {



        val placar1TextView = findViewById<TextView>(R.id.tvPlacar1)
        val placar2TextView = findViewById<TextView>(R.id.tvPlacar2)

        // Obtém o texto atual dos TextViews
        val placar1Text = placar1TextView.text.toString().trim() // Remove espaços em branco
        val placar2Text = placar2TextView.text.toString().trim() // Remove espaços em branco

        //CTRL Z
        // Salva os valores anteriores antes de fazer alterações
        val placarAnterior1 = placar1TextView.text.toString().trim()
        val placarAnterior2 = placar2TextView.text.toString().trim()


        val placar1 =
            placar1Text.toIntOrNull() ?: 0 // Tenta converter a string em um número inteiro
        val placar2 =
            placar2Text.toIntOrNull() ?: 0 // Tenta converter a string em um número inteiro

        // Atualiza os placares
        when (view.id) {
            R.id.tvPlacar1 -> {
                placar1TextView.text = (placar1 + 1).toString()
            }
            R.id.tvPlacar2 -> {
                placar2TextView.text = (placar2 + 1).toString()
            }
        }

        //CTRL Z
        historicoPlacar1.add(placarAnterior1)
        historicoPlacar2.add(placarAnterior2)
    }

    // para ser chamada no botão
    fun saveGame(v: View) {

        //atualizar o placar


        // Atualize o objeto placar com as informações da interface do usuário
        val placar1TextView = findViewById<TextView>(R.id.tvPlacar1)
        val placar2TextView = findViewById<TextView>(R.id.tvPlacar2)
        placar.resultado = "${placar1TextView.text}x${placar2TextView.text}"

        //Atualizar o placar com data e hora do sistema do usuário
         val date = Calendar.getInstance().time
        var dateTimeFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
        val data_hora = dateTimeFormat.format(date)

        placar.resultadoLongo = data_hora



        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        var edShared = sp.edit()
        //Salvar o número de jogos já armazenados
        var numMatches = sp.getInt("numberMatch", 0) + 1
        edShared.putInt("numberMatch", numMatches)

        //Escrita em Bytes de Um objeto Serializável
        var dt = ByteArrayOutputStream()
        var oos = ObjectOutputStream(dt);
        oos.writeObject(placar);

        //Salvar como "match"
        edShared.putString("match" + numMatches, dt.toString(StandardCharsets.ISO_8859_1.name()))
        edShared.commit() //Não esqueçam de comitar!!!
        // Exibir mensagem de sucesso
        Snackbar.make(v, "Placar salvo com sucesso!", Snackbar.LENGTH_SHORT).show()


    }

    //para ser chamada no cronometro - não vai ser mais usada
    fun saveGame() {

        //atualizar o placar


        // Atualize o objeto placar com as informações da interface do usuário
        val placar1TextView = findViewById<TextView>(R.id.tvPlacar1)
        val placar2TextView = findViewById<TextView>(R.id.tvPlacar2)
        placar.resultado = "${placar1TextView.text}x${placar2TextView.text}"

        //Atualizar o placar com data e hora do sistema do usuário
        val date = Calendar.getInstance().time
        var dateTimeFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
        val data_hora = dateTimeFormat.format(date)

        placar.resultadoLongo = data_hora



        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        var edShared = sp.edit()
        //Salvar o número de jogos já armazenados
        var numMatches = sp.getInt("numberMatch", 0) + 1
        edShared.putInt("numberMatch", numMatches)

        //Escrita em Bytes de Um objeto Serializável
        var dt = ByteArrayOutputStream()
        var oos = ObjectOutputStream(dt);
        oos.writeObject(placar);

        //Salvar como "match"
        edShared.putString("match" + numMatches, dt.toString(StandardCharsets.ISO_8859_1.name()))
        edShared.commit() //Não esqueçam de comitar!!!

        // Exibir mensagem de sucesso com um Toast
        val mensagem = "Placar salvo com sucesso!"
        val toast = Toast.makeText(applicationContext, mensagem, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun voltarPlacar(view: View) {
        if (historicoPlacar1.isNotEmpty()) {
            // Restaure a última mudança feita no histórico
            val placarAnterior1 = historicoPlacar1.removeAt(historicoPlacar1.size - 1)


            // Atualize a interface do usuário para refletir o placar restaurado
            val placar1TextView = findViewById<TextView>(R.id.tvPlacar1)

            placar1TextView.text = placarAnterior1
        }
        if (historicoPlacar2.isNotEmpty()) {
            // Restaure a última mudança feita no histórico
            val placarAnterior2 = historicoPlacar2.removeAt(historicoPlacar2.size - 1)


            // Atualize a interface do usuário para refletir o placar restaurado

            val placar2TextView = findViewById<TextView>(R.id.tvPlacar2)
            placar2TextView.text = placarAnterior2
        }

    }

    fun openPenalti(v: View){ //Executa ao click
        val placar1TextView = findViewById<TextView>(R.id.tvPlacar1)
        val placar1Value = placar1TextView.text.toString()

        val placar2TextView = findViewById<TextView>(R.id.tvPlacar2)
        val placar2Value = placar2TextView.text.toString()

        val partidaTextView = findViewById<TextView>(R.id.tvNomePartida2)
        val partidaValue = partidaTextView.text.toString()


        val intent = Intent(this, PenaltiPlacarActivity::class.java).apply{
            putExtra("placar", placar)

            putExtra("time1", placar.tvTime1)
            putExtra("time2", placar.tvTime2)

            putExtra("placar1", placar1Value)
            putExtra("placar2", placar2Value)

            putExtra("partida", partidaValue)


        }
        startActivity(intent)
    }

    fun openPenalti(){ //Executa no cronometro
        val intent = Intent(this, PenaltiPlacarActivity::class.java).apply{
            putExtra("placar", placar)
        }
        startActivity(intent)
    }


}






/*
    fun lerUltimosJogos(view: View) {
        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = view.context.getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)

        val meuObjString: String = sp.getString("match1", "") ?: ""

        if (meuObjString.isNotEmpty()) {
            val data = Base64.decode(meuObjString, Base64.DEFAULT)
            val dis = ByteArrayInputStream(data)
            val ois = ObjectInputStream(dis)
            val placarAntigo = ois.readObject() as Placar
            Log.v("SMD26", placarAntigo.resultado)
        }
    }




    fun ultimoJogos () {
        val sharedFilename = "PreviousGames"
        val sp:SharedPreferences = getSharedPreferences(sharedFilename,Context.MODE_PRIVATE)
        var matchStr:String=sp.getString("match1","").toString()
       // Log.v("PDM22", matchStr)
        if (matchStr.length >=1){
            var dis = ByteArrayInputStream(matchStr.toByteArray(Charsets.ISO_8859_1))
            var oos = ObjectInputStream(dis)
            var prevPlacar:Placar = oos.readObject() as Placar
            Log.v("PDM22", "Jogo Salvo:"+ prevPlacar.resultado)
        }

    }
}
*/

