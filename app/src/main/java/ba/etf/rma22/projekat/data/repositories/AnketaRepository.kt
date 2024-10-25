package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.static.sveAnkete
import java.util.*

class AnketaRepository {

    companion object {

        private var ankete:MutableList<Anketa> = mutableListOf();

        init {
            ankete.addAll(sveAnkete())
        }

        fun getMyAnkete(): List<Anketa> {

            val mojaIstrazivanja = IstrazivanjeRepository.getUpisani().map({ i:Istrazivanje -> i.toString()});
            val mojeGrupe = GrupaRepository.getMyGroups().map { g: Grupa? -> g?.toString() }

            return ankete.filter({ a:Anketa -> mojaIstrazivanja.contains(a.nazivIstrazivanja)}).filter{ a: Anketa ->  mojeGrupe.contains(a.nazivGrupe)}

        }

        fun getAll(): List<Anketa> {
            return ankete;
        }

        fun getDone(): List<Anketa> {
            return getMyAnkete().filter({a:Anketa -> a.datumRada!=null});
        }

        fun getFuture(): List<Anketa> {
            return getMyAnkete().filter({a:Anketa -> a.datumRada==null && a.datumPocetka.after(Date())});
        }

        fun getNotTaken(): List<Anketa> {
            return getMyAnkete().filter({a: Anketa -> a.datumRada==null && a.datumKraj.before(Date())});
        }

    }
}