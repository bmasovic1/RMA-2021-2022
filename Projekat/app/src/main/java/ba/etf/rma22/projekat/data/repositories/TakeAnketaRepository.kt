package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.database.sqlite.SQLiteException
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TakeAnketaRepository {

    companion object {

        private lateinit var context: Context

        fun setContext(conte: Context){
            context = conte
        }

        suspend fun zapocniAnketu(idAnkete:Int): AnketaTaken {
            return withContext(Dispatchers.IO) {
                try {
                    val rezultat = ApiAdapter.retrofit.zapocniAnketu(idAnkete, AccountRepository.getHash())
                    try {
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.anketaTakenDao().insertOne(rezultat)
                        } catch (e: SQLiteException) {}
                        return@withContext rezultat
                    }
                    catch (e: Exception){

                        try {
                            val db = AppDatabase.getInstance(context)
                            val anke = db.anketaTakenDao().dajZapocetu(idAnkete)
                            return@withContext anke
                        }catch (e: Exception){
                            return@withContext null!!
                        }

                    }

                }catch (e: Exception){
                    return@withContext null!!
                }
            }
        }

        suspend fun getPoceteAnkete():List<AnketaTaken>?{

            return withContext(Dispatchers.IO) {

                try {
                    val rezultat = ApiAdapter.retrofit.getPoceteAnkete(AccountRepository.getHash())
                    if(rezultat.isEmpty())
                        return@withContext null
                    return@withContext rezultat
                }catch(e: Exception){

                    try {
                        val db = AppDatabase.getInstance(context)
                        val anke = db.anketaTakenDao().dajSveAnketaTakenDb()
                        return@withContext anke
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }

            }


        }


        suspend fun tetstInternet():Boolean{

            return withContext(Dispatchers.IO) {

                try {
                    val rezultat = ApiAdapter.retrofit.dajGrupe()
                    return@withContext true
                }catch(e: Exception){

                    return@withContext false

                }

            }


        }

    }
}