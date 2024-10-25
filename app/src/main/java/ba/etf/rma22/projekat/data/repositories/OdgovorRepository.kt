package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.OdgovorSlanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OdgovorRepository {

    companion object{

        suspend fun getOdgovoriAnketa(idAnkete:Int):List<Odgovor> {
            return withContext(Dispatchers.IO) {
                val acc = AccountRepository()
                val pokrenuteAnkete = ApiAdapter.retrofit.getPoceteAnkete(acc.getHash())
                var anke : Int = -1
                for(anketaa in pokrenuteAnkete){
                    if(anketaa.AnketumId == idAnkete){
                        anke = anketaa.id
                        break
                    }
                }
                if(anke == -1)
                    return@withContext emptyList<Odgovor>()
                try {
                    val rezultat = ApiAdapter.retrofit.getOdgovoriAnketa(anke, acc.getHash())
                    return@withContext rezultat
                }
                catch (e: Exception){
                    return@withContext emptyList<Odgovor>()
                }
            }
        }


        suspend fun postaviOdgovorAnketa(idAnketaTaken:Int,idPitanje:Int,odgovor:Int):Int{
            return withContext(Dispatchers.IO){
                val bodovi = PitanjeAnketaRepository.getRezultatAnkete(idAnketaTaken, idPitanje, odgovor)
                val odgovor = OdgovorSlanje(odgovor = odgovor, pitanje = idPitanje, progres = bodovi)
                val acc = AccountRepository()
                try {
                    ApiAdapter.retrofit.postaviOdgovorAnketi(acc.getHash(), idAnketaTaken, odgovor)
                    return@withContext bodovi
                }catch(e: Exception){
                    return@withContext -1
                }
            }
        }




    }

}