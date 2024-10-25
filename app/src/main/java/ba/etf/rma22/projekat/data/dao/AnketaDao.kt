package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Anketa

@Dao
interface AnketaDao {

    @Query("DELETE FROM anketa")
    suspend fun obrisiDb()

    @Query("SELECT * FROM anketa")
    suspend fun dajSveAnketeDb(): List<Anketa>

    @Query("SELECT * FROM anketa WHERE upisan=1")
    suspend fun dajUpisaneAnketeDb(): List<Anketa>

    @Query("SELECT * FROM anketa WHERE id = :anketaId")
    suspend fun dajByIdDb(anketaId : Int): Anketa

    @Insert
    suspend fun insertAll(ankete : List<Anketa>)

    @Insert
    suspend fun insertOne(anketa : Anketa)

    @Update
    suspend fun updateAnketu(model: Anketa): Int

}