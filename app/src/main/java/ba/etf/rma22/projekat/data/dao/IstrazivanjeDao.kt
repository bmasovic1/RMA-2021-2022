package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDao {

    @Query("DELETE FROM istrazivanje")
    suspend fun obrisiDb()

    @Insert
    suspend fun insertOne(istrazivanje : Istrazivanje)

    @Query("SELECT * FROM istrazivanje")
    suspend fun dajSvaIstrazivanjaDb(): List<Istrazivanje>

    @Query("SELECT * FROM istrazivanje where naziv=:naziv")
    suspend fun dajIstrazivanjaZaNaziv(naziv: String): Istrazivanje

    @Query("SELECT * FROM istrazivanje where godina=:godina")
    suspend fun dajIstrazivanjaZaGodinu(godina: String): List<Istrazivanje>

}