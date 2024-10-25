package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Pitanje

@Dao
interface PitanjeDao {

    @Query("DELETE FROM pitanje")
    suspend fun obrisiDb()

    @Insert
    suspend fun insertOne(pitanje : Pitanje)

    @Insert
    suspend fun insertAll(ankete : List<Pitanje>)

    @Query("SELECT * FROM pitanje")
    suspend fun dajSvaPitanjaDb(): List<Pitanje>

    @Query("SELECT * FROM pitanje WHERE AnketaId =:anketaId")
    suspend fun dajPitanjaZaAnketu(anketaId: Int): List<Pitanje>

}