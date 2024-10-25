package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface OdgovorDao {

    @Query("DELETE FROM odgovor")
    suspend fun obrisiDb()

    @Insert
    suspend fun insertOne(odgovor : Odgovor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun dodajOdgovor(odgovor: Odgovor)

    @Query("SELECT * FROM odgovor")
    suspend fun getAllOdgovori(): List<Odgovor>

    @Query("SELECT * from Odgovor where AnketaId = :anketaId")
    fun getOdgovorZaAnketaTaken(anketaId: Int) : List<Odgovor>

}