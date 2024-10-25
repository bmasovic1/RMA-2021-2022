package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Account
import ba.etf.rma22.projekat.data.models.Pitanje

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    suspend fun getAcc(): Account

    @Query("DELETE FROM Account")
    suspend fun izbrisiSve()

    @Insert
    suspend fun insertAcc(account : Account)

    @Query("SELECT acHash from Account")
    suspend fun getHash() : String

}