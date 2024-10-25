package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.static.dajPitanjaAnketa
import ba.etf.rma22.projekat.data.static.dajSvaPitanja
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class PitanjeAnketaRepository {


    companion object{

        suspend fun getPitanja(idAnkete:Int):List<Pitanje> {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getPitanja(idAnkete)
                val responseBody = response.body()
                return@withContext responseBody!!
            }
        }


        suspend fun getRezultatAnkete(idAnkete: Int, idPitanje: Int, odgovorInt: Int): Int{
            return withContext(Dispatchers.IO){
                val poceteAnkete = TakeAnketaRepository.getPoceteAnkete()
                var anketId = -1
                if (poceteAnkete != null) {
                    for(anke in poceteAnkete){
                        if(anke.id == idAnkete)
                            anketId = anke.AnketumId
                    }
                }
                val pitanja = getPitanja(anketId)
                val odgovori = OdgovorRepository.getOdgovoriAnketa(anketId)
                var rezultat = 0.0
                for(pitanje in pitanja){
                    for(odgovor in odgovori){
                        if(odgovor.pitanjeId == pitanje.id ){
                            rezultat += (1/pitanja.size.toDouble())
                        }
                    }
                    if(pitanje.id == idPitanje){
                        rezultat += (1/pitanja.size.toDouble())
                    }
                }

                var anket = AnketaRepository.getById(anketId)

                var br= zaokruzi(rezultat.toFloat())

                anket.progres=br.toFloat()

                return@withContext zaokruzi(rezultat.toFloat())
            }
        }

        fun zaokruzi(value1: Float): Int {
            var value = value1*100
            var mult = 20
            if (value < 0) {
                mult = -20
                value = -value
            }
            return ((mult * ((value.toInt() + 10) / 20)))
        }

    }

}