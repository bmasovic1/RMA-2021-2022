package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.database.sqlite.SQLiteException
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PitanjeAnketaRepository {


    companion object{

        private lateinit var context: Context

        fun setContext(conte: Context){
            context = conte
        }

        suspend fun getPitanja(idAnkete:Int):List<Pitanje> {
            return withContext(Dispatchers.IO) {
                try {
                    var response = ApiAdapter.retrofit.getPitanja(idAnkete)
                    val responseBody = response.body()

                    for(pita in responseBody!!)
                        try {
                            val db = AppDatabase.getInstance(context)
                            val svaPIt = db.pitanjeDao().dajSvaPitanjaDb()
                            var test=0
                            for(pi in svaPIt) {
                                if(pi.AnketaId==idAnkete && pi.tekstPitanja==pita.tekstPitanja && pi.opcije==pita.opcije && pi.naziv==pita.naziv){
                                    test=1
                                    break
                                }
                            }
                            if(test==0){
                                pita.AnketaId=idAnkete
                                db.pitanjeDao().insertOne(pita)
                            }
                        } catch (e: SQLiteException) {}

                    return@withContext responseBody!!
                }
                catch (e: Exception){

                    try {
                        val db = AppDatabase.getInstance(context)
                        val pita = db.pitanjeDao().dajPitanjaZaAnketu(idAnkete)
                        return@withContext pita
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }

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

                anket!!.progres=br.toFloat()

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