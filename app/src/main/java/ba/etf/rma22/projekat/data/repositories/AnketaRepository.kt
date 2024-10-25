package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnketaRepository {


    companion object {


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
                var response = sveAnkete()
                return@withContext response
            }
        }

        suspend fun getAll(offset:Int):List<Anketa>  {
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.dajSveAnkete(offset)
                return@withContext response
            }
        }

        suspend fun getById(id:Int):Anketa{
            return withContext(Dispatchers.IO){
                try {
                    var response = ApiAdapter.retrofit.dajeAnketu(id)
                    val responseBody = response.body()
                    return@withContext responseBody!!

                }catch (e: Exception){
                    return@withContext null!!
                }
            }
        }

        suspend fun getUpisane():List<Anketa>{
            return withContext(Dispatchers.IO) {
                val acc = AccountRepository()
                var korisnikoveAnkete = arrayListOf<Anketa>()
                var anketee = mutableListOf<Anketa>()
                var grupe = ApiAdapter.retrofit.dajStudentoveGrupe(acc.getHash())!!
                if (grupe != null) {
                    for (grupaa in grupe) {
                        var ankete = ApiAdapter.retrofit.dajUpisaneAnkete(grupaa.id)!!
                        if(ankete!=null){
                            anketee.addAll(ankete)
                        }
                    }
                    korisnikoveAnkete.addAll(anketee)
                }
                return@withContext anketee
            }
        }

        //DODATNE

        suspend fun getAllPrikaz():List<Anketa>  {
            return withContext(Dispatchers.IO){
                val pom = sveAnkete()
                val rezultat = mutableListOf<Anketa>()
                for(ank in pom){
                    val pom1 = ApiAdapter.retrofit.dajGrupeZaAnkete(ank.id)
                    for(ank2 in pom1){
                        var ubaci = Anketa(ank.id,ank.naziv,ank.datumPocetak,ank.datumKraj,ank.trajanje,ank.nazivGrupe,ank.nazivIstrazivanja,ank.datumRada,ank.progres)
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
                return@withContext rezultat.distinct().toList()
            }
        }


        suspend fun getUpisanePrikaz():List<Anketa>{
            return withContext(Dispatchers.IO){
                val acc = AccountRepository()
                var grupe = ApiAdapter.retrofit.dajStudentoveGrupe(acc.getHash())
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
                return@withContext rezultat.distinct().toList()
            }
        }

    }
}