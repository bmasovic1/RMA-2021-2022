package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.database.sqlite.SQLiteException
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnketaRepository {


    companion object {

        private lateinit var context: Context

        fun setContext(conte: Context){
            context = conte
        }

        suspend fun sveAnkete():List<Anketa>{
            return withContext(Dispatchers.IO){
                val rezultat = mutableListOf<Anketa>()
                for (i in 1..10) {
                    val pom = ApiAdapter.retrofit.dajSveAnkete(i)
                    if(pom.size>=1) {
                        for (ank in pom) {
                            rezultat.add(ank)
                        }
                    }
                    else{
                        break
                    }
                }
                return@withContext rezultat
            }
        }

        suspend fun getAll():List<Anketa>  {
            return withContext(Dispatchers.IO) {

                try {
                    val response = sveAnkete()
                    for(anketa in response)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.anketaDao().insertOne(anketa)
                        } catch (e: SQLiteException) {}
                    return@withContext response
                }
                catch (e: Exception){

                    try {
                        val db = AppDatabase.getInstance(context)
                        val anke = db.anketaDao().dajSveAnketeDb()
                        return@withContext anke
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }

            }
        }

        suspend fun getAll(offset:Int):List<Anketa>  {
            return withContext(Dispatchers.IO) {

                try {
                    var response = ApiAdapter.retrofit.dajSveAnkete(offset)
                    for(anketa in response)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.anketaDao().insertOne(anketa)
                        } catch (e: SQLiteException) {}
                    return@withContext response
                }
                catch (e: Exception){

                    try {
                        val db = AppDatabase.getInstance(context)
                        val anke = db.anketaDao().dajSveAnketeDb()
                        return@withContext anke
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }
            }
        }

        suspend fun getById(id:Int):Anketa?{
            return withContext(Dispatchers.IO){
                try {
                    var response = ApiAdapter.retrofit.dajeAnketu(id)
                    val responseBody = response.body()
                    if(responseBody!!.id==0){
                        return@withContext null
                    }
                    return@withContext responseBody!!
                }catch (e: Exception){
                    try {
                        var db = AppDatabase.getInstance(context)
                        var anke = db.anketaDao().dajByIdDb(id)
                        if(anke.id==null)
                            return@withContext null

                        return@withContext anke
                    }catch (e: Exception){
                        return@withContext null
                    }


                }
            }
        }

        suspend fun getUpisane():List<Anketa>{
            return withContext(Dispatchers.IO) {

                try {

                    val korisnikoveAnkete = arrayListOf<Anketa>()
                    val anketee = mutableListOf<Anketa>()
                    val grupe = ApiAdapter.retrofit.dajStudentoveGrupe(AccountRepository.getHash())!!
                    if (grupe != null) {
                        for (grupaa in grupe) {
                            var ankete = ApiAdapter.retrofit.dajUpisaneAnkete(grupaa.id)!!
                            if(ankete!=null){
                                anketee.addAll(ankete)
                            }
                        }
                        korisnikoveAnkete.addAll(anketee)
                    }

                    for(ankett in anketee)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.anketaDao().insertOne(ankett)
                        } catch (e: SQLiteException) {}

                    return@withContext anketee

                } catch (e: Exception) {

                    try {
                        val db = AppDatabase.getInstance(context)
                        val anke = db.anketaDao().dajSveAnketeDb()
                        var rez = anke.toMutableList()

                        return@withContext rez
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }
                return@withContext null!!
            }
        }

        //DODATNE

        suspend fun getAllPrikaz():List<Anketa>  {
            return withContext(Dispatchers.IO){
                try{
                    val pom = sveAnkete()
                    val rezultat = mutableListOf<Anketa>()
                    for(ank in pom){
                        val pom1 = ApiAdapter.retrofit.dajGrupeZaAnkete(ank.id)
                        for(ank2 in pom1){
                            var ubaci = Anketa(ank.id,ank.naziv,ank.datumPocetak,ank.datumKraj,ank.trajanje,ank.nazivGrupe,ank.nazivIstrazivanja,ank.datumRada,ank.progres,ank.upisan)
                            val naziv = ApiAdapter.retrofit.dajIstrazivanjeId(ank2.IstrazivanjeId).naziv
                            ubaci.nazivIstrazivanja = naziv
                            rezultat.add(ubaci)
                        }

                    }
                    val datRad = TakeAnketaRepository.getPoceteAnkete()
                    if (datRad != null) {
                        for(anketa in datRad){
                            rezultat.stream().forEach { ank ->
                                if(ank.id == anketa.AnketumId)
                                    ank.datumRada = anketa.datumRada
                            }
                        }
                    }
                    val pocetaAnketa = TakeAnketaRepository.getPoceteAnkete()
                    for(ank in rezultat){
                        if (pocetaAnketa != null) {
                            for(anketaa in pocetaAnketa){
                                if(anketaa.AnketumId == ank.id){
                                    if(ank.progres==null){
                                        ank.progres = anketaa.progres.toFloat()/100
                                    }
                                    else {
                                        if(ank.progres!!<anketaa.progres){
                                            ank.progres = anketaa.progres.toFloat()/100
                                        }
                                    }
                                }
                            }
                        }
                    }

                    var db = AppDatabase.getInstance(context)
                    var rezzz = rezultat.distinct().toList()
                    for(ankett in rezzz)
                        try {
                            val db = AppDatabase.getInstance(context)
                            db.anketaDao().insertOne(ankett)
                        } catch (e: SQLiteException) {}

                    return@withContext rezultat.distinct().toList()

                }catch (e: Exception){

                    try {
                        val db = AppDatabase.getInstance(context)
                        val anke = db.anketaDao().dajSveAnketeDb()
                        return@withContext anke
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }

            }
        }


        suspend fun getUpisanePrikaz():List<Anketa>{
            return withContext(Dispatchers.IO){

                try{

                    var grupe = ApiAdapter.retrofit.dajStudentoveGrupe(AccountRepository.getHash())
                    var rezultat = mutableListOf<Anketa>()
                    for(grupa in grupe){
                        var pom = ApiAdapter.retrofit.dajUpisaneAnkete(grupa.id)
                        val nazivIstrazivanja = ApiAdapter.retrofit.dajIstrazivanjeId(grupa.IstrazivanjeId).naziv
                        for(ank in pom){
                            if(ank.nazivIstrazivanja == null)
                                ank.nazivIstrazivanja = nazivIstrazivanja
                            else ank.nazivIstrazivanja+= nazivIstrazivanja

                        }
                        rezultat.addAll(pom)
                    }
                    var izbacuj = mutableListOf<Int>()
                    var i = 0
                    var j = 0
                    while(i < rezultat.size){
                        j = i + 1
                        while(j < rezultat.size){
                            if(rezultat[i].id == rezultat[j].id){
                                izbacuj.add(j)
                            }
                            j++
                        }
                        i++
                    }

                    val datRad = TakeAnketaRepository.getPoceteAnkete()
                    if (datRad != null) {
                        for(anketa in datRad){
                            rezultat.stream().forEach { ank ->
                                if(ank.id == anketa.AnketumId)
                                    ank.datumRada = anketa.datumRada
                            }
                        }
                    }
                    val pocetaAnketa = TakeAnketaRepository.getPoceteAnkete()
                    for(ank in rezultat){
                        if (pocetaAnketa != null) {
                            for(anketaa in pocetaAnketa){
                                if(anketaa.AnketumId == ank.id){
                                    if(ank.progres==null){
                                        ank.progres = anketaa.progres.toFloat()/100
                                    }
                                    else {
                                        if(ank.progres!!<anketaa.progres){
                                            ank.progres = anketaa.progres.toFloat()/100
                                        }
                                    }
                                }
                            }
                        }
                    }

                    var db = AppDatabase.getInstance(context)
                    var rezzz = rezultat.distinct().toList()
                    try {
                        db.anketaDao().insertAll(rezzz)
                    } catch (e: SQLiteException) {}

                    for(anke in rezzz)
                        try {
                            anke.upisan=1
                            db.anketaDao().updateAnketu(anke)
                        } catch (e: SQLiteException) {}

                    return@withContext rezultat.distinct().toList()


                }catch (e: Exception){

                    try {
                        val db = AppDatabase.getInstance(context)
                        val anke = db.anketaDao().dajUpisaneAnketeDb()
                        return@withContext anke
                    }catch (e: Exception){
                        return@withContext null!!
                    }

                }
            }
        }

    }
}