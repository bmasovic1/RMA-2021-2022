package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.AnketaTaken

@Dao
interface AnketaTakenDao {

    @Query("DELETE FROM anketataken")
    suspend fun obrisiDb()

    @Insert
    suspend fun insertOne(anketaTaken : AnketaTaken)

    @Query("SELECT * FROM anketataken")
    suspend fun dajSveAnketaTakenDb(): List<AnketaTaken>

    @Query("SELECT * FROM anketataken WHERE AnketumId =:idAnkete")
    suspend fun dajZapocetu(idAnkete: Int): AnketaTaken

}