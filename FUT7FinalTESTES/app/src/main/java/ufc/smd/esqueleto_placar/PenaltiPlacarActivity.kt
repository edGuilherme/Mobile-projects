package ufc.smd.esqueleto_placar

import android.annotation.SuppressLint
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

class PenaltiPlacarActivity : AppCompatActivity() {
    lateinit var placar: Placar
    lateinit var tvResultadoJogo: TextView

    // CTRL Z
    val historicoPlacar3 = mutableListOf<String>()
    val historicoPlacar4 = mutableListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penalti)
        placar = getIntent().getExtras()?.getSerializable("placar") as Placar



// Recupera o valor do Intent
        val time1Value = intent.getStringExtra("time1")
        val time2Value = intent.getStringExtra("time2")

        val placar1Value = intent.getStringExtra("placar1")
        val placar2Value = intent.getStringExtra("placar2")

        val partidaValue = intent.getStringExtra("partida")

// Define os textos com os valores acima
        val time3TextView = findViewById<TextView>(R.id.time3)
        time3TextView.text = time1Value

        val time4TextView = findViewById<TextView>(R.id.time4)
        time4TextView.text = time2Value

        val tvPlacar3 = findViewById<TextView>(R.id.tvPlacar3)
        tvPlacar3.text = placar1Value

        val tvPlacar4 = findViewById<TextView>(R.id.tvPlacar4)
        tvPlacar4.text = placar2Value

        val tvPartida = findViewById<TextView>(R.id.tvNomePartida3)
        tvPartida.text = partidaValue


    }




    fun alteraPlacar(view: View) {



        val placar3TextView = findViewById<TextView>(R.id.novotvPlacar1)
        val placar4TextView = findViewById<TextView>(R.id.novotvPlacar2)

        // Obtém o texto atual dos TextViews
        val placar3Text = placar3TextView.text.toString().trim() // Remove espaços em branco
        val placar4Text = placar4TextView.text.toString().trim() // Remove espaços em branco

        //CTRL Z
        // Salva os valores anteriores antes de fazer alterações
        val placarAnterior3 = placar3TextView.text.toString().trim()
        val placarAnterior4 = placar4TextView.text.toString().trim()


        val placar3 =
            placar3Text.toIntOrNull() ?: 0 // Tenta converter a string em um número inteiro
        val placar4 =
            placar4Text.toIntOrNull() ?: 0 // Tenta converter a string em um número inteiro

        // Atualiza os placares
        when (view.id) {
            R.id.novotvPlacar1 -> {
                placar3TextView.text = (placar3 + 1).toString()
            }
            R.id.novotvPlacar2 -> {
                placar4TextView.text = (placar4 + 1).toString()
            }
        }

        //CTRL Z
        historicoPlacar3.add(placarAnterior3)
        historicoPlacar4.add(placarAnterior4)
    }

    // para ser chamada no botão
    fun saveGame(v: View) {

        //atualizar o placar


        // Atualize o objeto placar com as informações da interface do usuário
        val placar3TextView = findViewById<TextView>(R.id.novotvPlacar1)
        val placar4TextView = findViewById<TextView>(R.id.novotvPlacar2)
        placar.resultado = "${placar3TextView.text}x${placar4TextView.text} - Penaltis"

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
        val mensagem = "Placar salvo com sucesso!"
        val toast = Toast.makeText(applicationContext, mensagem, Toast.LENGTH_SHORT)
        toast.show()
        finish()
        val intent = Intent(this, MainActivity::class.java).apply{
           // putExtra("placar", placar)
        }
        startActivity(intent)



    }



    fun voltarPlacar(view: View) {
        if (historicoPlacar3.isNotEmpty()) {
            // Restaure a última mudança feita no histórico
            val placarAnterior3 = historicoPlacar3.removeAt(historicoPlacar3.size - 1)


            // Atualize a interface do usuário para refletir o placar restaurado
            val placar3TextView = findViewById<TextView>(R.id.novotvPlacar1)

            placar3TextView.text = placarAnterior3
        }
        if (historicoPlacar4.isNotEmpty()) {
            // Restaure a última mudança feita no histórico
            val placarAnterior4 = historicoPlacar4.removeAt(historicoPlacar4.size - 1)


            // Atualize a interface do usuário para refletir o placar restaurado

            val placar4TextView = findViewById<TextView>(R.id.novotvPlacar2)
            placar4TextView.text = placarAnterior4
        }

    }




}







