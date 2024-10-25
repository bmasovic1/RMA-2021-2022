package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface GrupaDao {

    @Query("DELETE FROM grupa")
    suspend fun obrisiDb()

    @Insert
    suspend fun insertOne(grupa : Grupa)

    @Query("SELECT * FROM grupa")
    suspend fun dajSveGrupeDb(): List<Grupa>

    @Query("SELECT * FROM grupa WHERE IstrazivanjeId =:IstrazivanjeId")
    suspend fun dajGrupeZaIstrazivanje(IstrazivanjeId: Int): List<Grupa>

    @Query("SELECT * FROM grupa WHERE id =:idGrupa")
    suspend fun dajGrupeZaID(idGrupa: Int): Grupa

    @Query("SELECT * FROM grupa WHERE upisan =1")
    suspend fun dajUpisaneGrupe(): List<Grupa>

    @Update
    suspend fun updateGrupu(model: Grupa): Int

}