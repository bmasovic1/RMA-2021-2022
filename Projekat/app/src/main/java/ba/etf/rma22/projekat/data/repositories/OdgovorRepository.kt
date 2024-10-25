package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.database.sqlite.SQLiteException
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.OdgovorSlanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OdgovorRepository {

    companion object{

        private lateinit var context: Context

        fun setContext(conte: Context){
            context = conte
        }

        suspend fun getOdgovoriAnketa(idAnkete:Int):List<Odgovor> {
            return withContext(Dispatchers.IO) {
                try {
                    val acc = AccountRepository()
                    val pokrenuteAnkete =
                        ApiAdapter.retrofit.getPoceteAnkete(AccountRepository.getHash())
                    var anke: Int = -1
                    for (anketaa in pokrenuteAnkete) {
                        if (anketaa.AnketumId == idAnkete) {
                            anke = anketaa.id
                            break
                        }
                    }
                    if (anke == -1)
                        return@withContext emptyList<Odgovor>()
                    try {
                        val rezultat =
                            ApiAdapter.retrofit.getOdgovoriAnketa(anke, AccountRepository.getHash())
                        return@withContext rezultat
                    } catch (e: Exception) {
                        return@withContext emptyList<Odgovor>()
                    }
                }
                catch (e: Exception){
                    try {
                        val db = AppDatabase.getInstance(context)
                        val anke = db.anketaTakenDao().dajZapocetu(idAnkete)
                        val odgovori = db.odgovorDao().getOdgovorZaAnketaTaken(anke.id)
                        return@withContext odgovori
                    } catch (e: Exception) {}
                    return@withContext emptyList<Odgovor>()
                }
            }
        }


        suspend fun postaviOdgovorAnketa(idAnketaTaken:Int,idPitanje:Int,odgovor:Int):Int{
            return withContext(Dispatchers.IO){

                try{
                    val bodovi = PitanjeAnketaRepository.getRezultatAnkete(idAnketaTaken, idPitanje, odgovor)
                    val odgovorr = OdgovorSlanje(odgovor = odgovor, pitanje = idPitanje, progres = bodovi)

                    try {

                        ApiAdapter.retrofit.postaviOdgovorAnketi(AccountRepository.getHash(), idAnketaTaken, odgovorr)

                        try {

                            val db = AppDatabase.getInstance(context)

                            try {
                                val brOd=db.odgovorDao().getAllOdgovori()
                                val odg = Odgovor(brOd.size+1,odgovor,idPitanje,idAnketaTaken)
                                db.odgovorDao().insertOne(odg)
                            } catch (e: SQLiteException) {
                                val odg = Odgovor(1,odgovor,idPitanje,idAnketaTaken)
                                db.odgovorDao().insertOne(odg)
                            }


                        } catch (e: SQLiteException) {}

                        return@withContext bodovi
                    }catch(e: Exception){
                        return@withContext -1
                    }
                } catch (e: Exception) {
                    return@withContext null!!
                }
            }
        }

    }

}