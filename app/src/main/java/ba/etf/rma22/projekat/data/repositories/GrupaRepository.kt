package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.static.sveGrupe
import ba.etf.rma22.projekat.data.static.upisanUGrupe

class GrupaRepository {

    companion object {

        private var sveGrupe: MutableList<Grupa> = mutableListOf()
        private var upisaneGrupe: MutableList<Grupa> = mutableListOf()

        init {

            sveGrupe.addAll(sveGrupe())
            upisaneGrupe.addAll(upisanUGrupe())

        }

        fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa> {
            return sveGrupe.filter { g: Grupa? -> g?.nazivIstrazivanja == nazivIstrazivanja }
        }

        fun getMyGroups():List<Grupa>{
            return upisaneGrupe
        }

        fun upisiGrupu(grupa: Grupa){
            upisaneGrupe.add(grupa)
        }

    }

}