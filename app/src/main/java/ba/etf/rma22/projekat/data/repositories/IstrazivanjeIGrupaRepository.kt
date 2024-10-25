package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IstrazivanjeIGrupaRepository {

    companion object{

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
                var response = svaIstrazivanja()
                val responseBody = response
                return@withContext responseBody!!
            }

        }

        suspend fun getIstrazivanja(offset:Int):List<Istrazivanje> {

            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.dajIstrazivanja(offset)
                val responseBody = response.body()
                return@withContext responseBody!!
            }

        }

        suspend fun getGrupe():List<Grupa> {

            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.dajGrupe()
                val responseBody = response.body()
                return@withContext responseBody!!
            }

        }

        suspend fun getGrupeZaIstrazivanje(idIstrazivanja:Int):List<Grupa>{

            return withContext(Dispatchers.IO) {

                val rezultat = mutableListOf<Grupa>()
                val pom1 = ApiAdapter.retrofit.dajGrupe2()

                for(istra in pom1){
                    if(istra.IstrazivanjeId.equals(idIstrazivanja)) {

                        rezultat.add(istra)
                    }
                }

                return@withContext rezultat
            }

        }

        suspend fun upisiUGrupu(idGrupa:Int):Boolean{

            return withContext(Dispatchers.IO) {
                val acc = AccountRepository()
                val odgovor = ApiAdapter.retrofit.upisiStudentaUGrupu(idGrupa, acc.getHash())
                var rezultat = true
                if(odgovor.message.contains("Grupa not found.") ||
                    odgovor.message.contains("Ne postoji account gdje je"))
                    rezultat = false
                return@withContext rezultat
            }

        }

        suspend fun getUpisaneGrupe():List<Grupa> {

            return withContext(Dispatchers.IO) {
                val acc = AccountRepository()
                return@withContext ApiAdapter.retrofit.dajStudentoveGrupe(acc.getHash())
            }
        }



        // DODATNE

        suspend fun getNEUpisanaIstrazivanja(godina:String):List<Istrazivanje> {

            return withContext(Dispatchers.IO) {

                val acc = AccountRepository()
                val rezultat = mutableListOf<Istrazivanje>()
                val pom1 = ApiAdapter.retrofit.dajStudentoveGrupe(acc.getHash())
                for(anketa in pom1){
                    val istr = ApiAdapter.retrofit.dajIstrazivanjeId(anketa.IstrazivanjeId)
                    rezultat.add(istr)
                }
                var rezultat2 = mutableListOf<Istrazivanje>()
                val response = svaIstrazivanja()
                rezultat2 = response.toMutableList()

                var i=0;
                while(i < rezultat2.size) {
                    for(anketa in rezultat) {
                        if(rezultat2[i].id==anketa.id){
                            rezultat2.remove(anketa);
                            i=0;
                            break
                        }
                    }
                    i++;
                }
                val rezultat3 = mutableListOf<Istrazivanje>()
                for(anketa in rezultat2){
                    if(anketa.godina==godina) {
                        rezultat3.add(anketa)
                    }
                }

                return@withContext rezultat3
            }
        }

        suspend fun getNEUpisanaIstrazivanja2(godina:String):List<Istrazivanje> {

            return withContext(Dispatchers.IO) {

                val acc = AccountRepository()
                val upisaneGrupe = ApiAdapter.retrofit.dajStudentoveGrupe(acc.getHash())

                var svaIstra = mutableListOf<Istrazivanje>()
                val response = svaIstrazivanja()
                svaIstra = response.toMutableList()

                var rezultat2 = mutableListOf<Istrazivanje>()

                val sveGrupe = ApiAdapter.retrofit.dajGrupe2()

                for(svaIs in svaIstra){

                    var br1=0;
                    var br2=0;


                    for(sveG in sveGrupe){
                        if(sveG.IstrazivanjeId==svaIs.id)
                            br1++;
                    }


                    for(upiG in upisaneGrupe){
                        if(upiG.IstrazivanjeId==svaIs.id)
                            br2++;
                    }

                    if(br2==0) {
                        rezultat2.add(svaIs)
                    }

                }

                val rezultat3 = mutableListOf<Istrazivanje>()
                for(anketa in rezultat2){
                    if(anketa.godina==godina) {
                        rezultat3.add(anketa)
                    }
                }

                return@withContext rezultat3
            }
        }

        suspend fun dajGrupeZaIstrazivanje(godina:String):List<Grupa>{

            return withContext(Dispatchers.IO) {

                val rezultat = mutableListOf<Grupa>()
                val pom1 = ApiAdapter.retrofit.dajGrupe2()

                for(anketa in pom1){
                    val istr = ApiAdapter.retrofit.dajIstrazivanjeId(anketa.IstrazivanjeId)
                    if(istr.naziv.equals(godina)) {
                        rezultat.add(anketa)
                    }
                }

                val acc = AccountRepository()
                val upisaneGrupe = ApiAdapter.retrofit.dajStudentoveGrupe(acc.getHash())

                var i=0;
                while(i < rezultat.size) {
                    for(anketa in upisaneGrupe) {
                        if(rezultat[i].id==anketa.id){
                            rezultat.remove(anketa);
                            i=0;
                            break
                        }
                    }
                    i++;
                }

                return@withContext rezultat
            }

        }

        suspend fun upisiStudentaUGrupu(nazivGrupe: String):Boolean{

            return withContext(Dispatchers.IO) {

                val sveGrupe = ApiAdapter.retrofit.dajGrupe2()

                var idGrupa=0

                for(sveG in sveGrupe){
                    if(sveG.naziv==nazivGrupe)
                        idGrupa=sveG.id
                }

                val acc = AccountRepository()
                val odgovor = ApiAdapter.retrofit.upisiStudentaUGrupu(idGrupa, acc.getHash())
                var rezultat = true
                if(odgovor.message.contains("Grupa not found.") ||
                    odgovor.message.contains("Ne postoji account gdje je"))
                    rezultat = false
                return@withContext rezultat
            }

        }

    }

}