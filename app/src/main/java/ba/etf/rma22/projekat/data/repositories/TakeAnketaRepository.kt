package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TakeAnketaRepository {

    companion object {

        suspend fun zapocniAnketu(idAnkete:Int): AnketaTaken {
            return withContext(Dispatchers.IO) {
                val acc = AccountRepository()
                try {
                    val rezultat = ApiAdapter.retrofit.zapocniAnketu(idAnkete, acc.getHash())
                    return@withContext rezultat
                }catch (e: Exception){
                    return@withContext null!!
                }
            }
        }

        suspend fun getPoceteAnkete():List<AnketaTaken>?{

            return withContext(Dispatchers.IO) {
                val acc = AccountRepository()
                try {
                    val rezultat = ApiAdapter.retrofit.getPoceteAnkete(acc.getHash())
                    if(rezultat.isEmpty())
                        return@withContext null
                    return@withContext rezultat
                }catch(e: Exception){
                    return@withContext null
                }

            }


        }

    }
}