package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository {
    companion object {

        var acHash: String = "09307588-af76-4192-94b8-1569e760967d"
        private lateinit var context: Context

        fun setContext(conte: Context) {
            context = conte
        }

        suspend fun postaviHash(accHash: String): Boolean {
            return withContext(Dispatchers.IO) {

                acHash = accHash

                try {

                    val db = AppDatabase.getInstance(context)
                    db.accountDao().izbrisiSve()

                    val acc = Account(0, "student", accHash)
                    db.accountDao().insertAcc(acc)

                    db.anketaDao().obrisiDb()
                    db.anketaTakenDao().obrisiDb()
                    db.grupaDao().obrisiDb()
                    db.istrazivanjeDao().obrisiDb()
                    db.odgovorDao().obrisiDb()
                    db.pitanjeDao().obrisiDb()

                    return@withContext true

                } catch (e: Exception) {
                    return@withContext false
                }

            }
        }

        fun getHash(): String {
            return acHash
        }

    }

}