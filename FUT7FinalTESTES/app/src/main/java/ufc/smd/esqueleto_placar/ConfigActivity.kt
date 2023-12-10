package ufc.smd.esqueleto_placar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import data.Placar

class ConfigActivity : AppCompatActivity() {
    var placar: Placar= Placar("Jogo sem Config","0x0", "20/05/20 10h",false,  "Time1", "Time2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
       // placar= getIntent().getExtras()?.getSerializable("placar") as Placar
        //Log.v("PDM22",placar.nome_partida)
        //Log.v("PDM22",placar.has_timer.toString())


        openConfig()
        initInterface()

    }
    fun saveConfig(){
        val sharedFilename = "configPlacar"
        val sp:SharedPreferences = getSharedPreferences(sharedFilename,Context.MODE_PRIVATE)
        var edShared = sp.edit()

        // Salvar o valor de editTextTeamName1 nas preferências compartilhadas
        val editTextTeamName1 = findViewById<EditText>(R.id.editTextTeamName1)
        edShared.putString("teamName1", placar.tvTime1)

        // Salvar o valor de editTextTeamName2 nas preferências compartilhadas
        val editTextTeamName2 = findViewById<EditText>(R.id.editTextTeamName2)
        edShared.putString("teamName2", placar.tvTime2)

        // No método saveConfig, após salvar o valor do tempo:
        val editTextTempo = findViewById<EditText>(R.id.editTextTempo)
        val tempo = editTextTempo.text.toString().toLong()
        val tempoSegundos = tempo * 60 * 1000 // minutos e milessegundos
        edShared.putLong("tempo", tempoSegundos)


        edShared.putString("matchname",placar.nome_partida)
        edShared.putBoolean("has_timer",placar.has_timer)
        edShared.commit()
    }
    fun openConfig()
    {
        val sharedFilename = "configPlacar"
        val sp:SharedPreferences = getSharedPreferences(sharedFilename,Context.MODE_PRIVATE)
        placar.nome_partida=sp.getString("matchname","Jogo Padrão").toString()
        placar.has_timer=sp.getBoolean("has_timer",false)


        // Recuperar o valor de editTextTeamName1 das preferências compartilhadas e atribuí-lo a placar.tvTime1
        placar.tvTime1 = sp.getString("teamName1", "").toString()

        // Recuperar o valor de editTextTeamName2 das preferências compartilhadas e atribuí-lo a placar.tvTime2
        placar.tvTime2 = sp.getString("teamName2", "").toString()



                 // Gambiarra pra recuperar o valor de Tempo sem precisar mexer no placar
                // É uma gambiarra pois não faz o caminho padrão das outras configs:
                // openConfig -> initInterface
        // Recupere o valor do tempo em milissegundos das preferências compartilhadas
        val tempo = sp.getLong("tempo", 0)
        // Converta o valor do tempo de milissegundos para minutos
        val minutos = tempo / 60000
        // Atribua o valor de minutos a alguma variável, se necessário
        val editTextTempo = findViewById<EditText>(R.id.editTextTempo)
        editTextTempo.setText(minutos.toString())


    }
    fun initInterface(){
        val tv= findViewById<EditText>(R.id.editTextGameName)
        tv.setText(placar.nome_partida)
        val sw= findViewById<Switch>(R.id.swTimer)
        sw.isChecked=placar.has_timer

        // Ao iniciar, seta o texto recuperado nos campos dos times 1 e 2
        val editTextTeamName1 = findViewById<EditText>(R.id.editTextTeamName1)
        editTextTeamName1.setText(placar.tvTime1)

        val editTextTeamName2 = findViewById<EditText>(R.id.editTextTeamName2)
        editTextTeamName2.setText(placar.tvTime2)


    }

    fun updatePlacarConfig(){
        val tv= findViewById<EditText>(R.id.editTextGameName)
        val sw= findViewById<Switch>(R.id.swTimer)
        placar.nome_partida= tv.text.toString()
        placar.has_timer=sw.isChecked

        // Time 1
        val editTextTeamName1 = findViewById<EditText>(R.id.editTextTeamName1)
        placar.tvTime1 = editTextTeamName1.text.toString()

        //Time 2
        val editTextTeamName2 = findViewById<EditText>(R.id.editTextTeamName2)
        placar.tvTime2 = editTextTeamName2.text.toString()
    }

    fun openPlacar(v: View){ //Executa ao click do Iniciar Jogo
        updatePlacarConfig() //Pega da Interface e joga no placar
        saveConfig() //Salva no Shared preferences
        val intent = Intent(this, PlacarActivity::class.java).apply{
            putExtra("placar", placar)
        }
        startActivity(intent)
    }
}