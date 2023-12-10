package data

import java.io.Serializable

data class Placar( var nome_partida:String, var resultado:String, var resultadoLongo:String, var has_timer:Boolean,  var tvTime1: String, var tvTime2: String):Serializable {

}
