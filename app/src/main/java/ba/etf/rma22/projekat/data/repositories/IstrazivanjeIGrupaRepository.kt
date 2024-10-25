package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.database.sqlite.SQLiteException
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IstrazivanjeIGrupaRepository {

    companion object{

        private lateinit var context: Context

        fun setContext(conte: Context){
            context = conte
        }

        suspend fun svaIstrazivanja():List<Istrazivanje>{
            return withContext(Dispatchers.IO){
                val rezultat = mutableListOf<Istrazivanje>()
                for (i in 1..10) {
                    val pom = ApiAdapter.retrofit.dajIstrazivanja2(i)
                    if(pom.size>=1) {
                        for (anketa in pom) {
                            rezultat.add(anketa)
                        }
                    }
                    else{
                        break
                    }
                }
                return@withContext rezultat
            }
        }

        suspend fun getIstrazivanja():List<Istrazivanje> {

            return withContext(Dispatchers.IO) {

                try {
                    var response = svaIstrazivanja()
                    val responseBody = response
                    for(istra in responseBody)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.istrazivanjeDao().insertOne(istra)
                        } catch (e: SQLiteException) {}
                    return@withContext response
                }
                catch (e: Exception){

                    try {
                        val db = AppDatabase.getInstance(context)
                        val anke = db.istrazivanjeDao().dajSvaIstrazivanjaDb()
                        return@withContext anke
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }

            }

        }

        suspend fun getIstrazivanja(offset:Int):List<Istrazivanje> {

            return withContext(Dispatchers.IO) {

                try {
                    var response = ApiAdapter.retrofit.dajIstrazivanja(offset)
                    val responseBody = response.body()
                    for(istra in responseBody!!)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.istrazivanjeDao().insertOne(istra)
                        } catch (e: SQLiteException) {}
                    return@withContext responseBody
                }
                catch (e: Exception){

                    try {
                        val db = AppDatabase.getInstance(context)
                        val istra = db.istrazivanjeDao().dajSvaIstrazivanjaDb()
                        return@withContext istra
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }

            }
        }

        suspend fun getGrupe():List<Grupa> {

            return withContext(Dispatchers.IO) {

                try {
                    var response = ApiAdapter.retrofit.dajGrupe()
                    val responseBody = response.body()
                    for (grupa in responseBody!!)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.grupaDao().insertOne(grupa)
                        } catch (e: SQLiteException) {}
                    return@withContext responseBody
                } catch (e: Exception) {

                    try {
                        val db = AppDatabase.getInstance(context)
                        val grupe = db.grupaDao().dajSveGrupeDb()
                        return@withContext grupe
                    } catch (e: Exception) {
                        return@withContext null!!
                    }

                }

            }


        }

        suspend fun getGrupeZaIstrazivanje(idIstrazivanja:Int):List<Grupa>{

            return withContext(Dispatchers.IO) {

                try {
                    val rezultat = mutableListOf<Grupa>()
                    val pom1 = ApiAdapter.retrofit.dajGrupe2()

                    for(istra in pom1){
                        if(istra.IstrazivanjeId.equals(idIstrazivanja)) {
                            rezultat.add(istra)
                        }
                    }
                    for (grupa in rezultat!!)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.grupaDao().insertOne(grupa)
                        } catch (e: SQLiteException) {}
                    return@withContext rezultat
                } catch (e: Exception) {

                    try {
                        val db = AppDatabase.getInstance(context)
                        val grupe = db.grupaDao().dajGrupeZaIstrazivanje(idIstrazivanja)
                        return@withContext grupe
                    } catch (e: Exception) {
                        return@withContext null!!
                    }

                }

            }

        }

        suspend fun upisiUGrupu(idGrupa:Int):Boolean{

            return withContext(Dispatchers.IO) {
                try {
                    val odgovor = ApiAdapter.retrofit.upisiStudentaUGrupu(idGrupa,
                        AccountRepository.getHash())
                    var rezultat = true
                    if (odgovor.message.contains("Grupa not found.") ||
                        odgovor.message.contains("Ne postoji account gdje je")
                    )
                        rezultat = false

                    try {
                        val db = AppDatabase.getInstance(context)
                        var gru = db.grupaDao().dajGrupeZaID(idGrupa)
                        gru.upisan=1
                        db.grupaDao().updateGrupu(gru)
                    } catch (e: SQLiteException) {}

                    return@withContext rezultat
                }
                catch (e: Exception) {}
                return@withContext false
            }

        }

        suspend fun getUpisaneGrupe():List<Grupa> {

            return withContext(Dispatchers.IO) {
                try {
                    return@withContext ApiAdapter.retrofit.dajStudentoveGrupe(AccountRepository.getHash())
                }
                catch (e: Exception) {
                    val db = AppDatabase.getInstance(context)
                    var upisane = db.grupaDao().dajUpisaneGrupe()
                    return@withContext upisane
                }
            }
        }



        // DODATNE


        suspend fun getNEUpisanaIstrazivanja2(godina:String):List<Istrazivanje> {

            return withContext(Dispatchers.IO) {

                try{
                    val upisaneGrupe =
                        ApiAdapter.retrofit.dajStudentoveGrupe(AccountRepository.getHash())

                    var svaIstra = mutableListOf<Istrazivanje>()
                    val response = svaIstrazivanja()
                    svaIstra = response.toMutableList()

                    var rezultat2 = mutableListOf<Istrazivanje>()

                    val sveGrupe = ApiAdapter.retrofit.dajGrupe2()

                    for (svaIs in svaIstra) {

                        var br1 = 0;
                        var br2 = 0;


                        for (sveG in sveGrupe) {
                            if (sveG.IstrazivanjeId == svaIs.id)
                                br1++;
                        }


                        for (upiG in upisaneGrupe) {
                            if (upiG.IstrazivanjeId == svaIs.id)
                                br2++;
                        }

                        if (br2 == 0) {
                            rezultat2.add(svaIs)
                        }

                    }

                    val rezultat3 = mutableListOf<Istrazivanje>()
                    for (anketa in rezultat2) {
                        if (anketa.godina == godina) {
                            rezultat3.add(anketa)
                        }
                    }

                    for(istra in rezultat3)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.istrazivanjeDao().insertOne(istra)
                        } catch (e: SQLiteException) {}

                    return@withContext rezultat3
                }
                catch (e: Exception){

                    val db = AppDatabase.getInstance(context)
                    val rezGodine = db.istrazivanjeDao().dajIstrazivanjaZaGodinu(godina)
                    val rezz = mutableListOf<Istrazivanje>()
                    var test=0
                    for(istra in rezGodine) {
                        test=0
                        val grupe = db.grupaDao().dajGrupeZaIstrazivanje(istra.id)
                        for(gru in grupe) {
                            if(gru.upisan==1){
                                test=1
                            }
                        }
                        if(test==0){
                            rezz.add(istra)
                        }
                    }
                    return@withContext rezz

                }
                return@withContext null!!

            }
        }

        suspend fun dajGrupeZaIstrazivanje(godina:String):List<Grupa>{

            return withContext(Dispatchers.IO) {

                try {
                    val rezultat = mutableListOf<Grupa>()
                    val pom1 = ApiAdapter.retrofit.dajGrupe2()

                    for (anketa in pom1) {
                        val istr = ApiAdapter.retrofit.dajIstrazivanjeId(anketa.IstrazivanjeId)
                        if (istr.naziv.equals(godina)) {
                            rezultat.add(anketa)
                        }
                    }

                    val upisaneGrupe = ApiAdapter.retrofit.dajStudentoveGrupe(AccountRepository.getHash())

                    var i = 0;
                    while (i < rezultat.size) {
                        for (anketa in upisaneGrupe) {
                            if (rezultat[i].id == anketa.id) {
                                rezultat.remove(anketa);
                                i = 0;
                                break
                            }
                        }
                        i++;
                    }

                    for(gruu in rezultat)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.grupaDao().insertOne(gruu)
                        } catch (e: SQLiteException) {}

                    return@withContext rezultat
                }
                catch (e:Exception){
                    try {

                        val db = AppDatabase.getInstance(context)
                        val istra = db.istrazivanjeDao().dajIstrazivanjaZaNaziv(godina)

                        val grupe = db.grupaDao().dajGrupeZaIstrazivanje(istra.id)

                        return@withContext grupe
                    } catch (e: Exception) {
                        return@withContext null!!
                    }
                }

            }

        }

        suspend fun upisiStudentaUGrupu(nazivGrupe: String):Boolean{

            return withContext(Dispatchers.IO) {

                try {

                    val sveGrupe = ApiAdapter.retrofit.dajGrupe2()
                    var idGrupa = 0
                    for (sveG in sveGrupe) {
                        if (sveG.naziv == nazivGrupe)
                            idGrupa = sveG.id
                    }
                    val odgovor = ApiAdapter.retrofit.upisiStudentaUGrupu(idGrupa, AccountRepository.getHash())
                    var rezultat = true
                    if (odgovor.message.contains("Grupa not found.") ||
                        odgovor.message.contains("Ne postoji account gdje je")
                    )
                        rezultat = false

                    try {
                        val db = AppDatabase.getInstance(context)
                        var gru = db.grupaDao().dajGrupeZaID(idGrupa)
                        gru.upisan=1
                        db.grupaDao().updateGrupu(gru)
                    } catch (e: SQLiteException) {}

                    return@withContext rezultat
                }
                catch(err :Exception){}
                return@withContext false
            }

        }

    }

}