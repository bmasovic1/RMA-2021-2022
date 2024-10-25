package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.static.svaIstrazivanja
import ba.etf.rma22.projekat.data.static.upisanUIstrazivanja

class IstrazivanjeRepository {

    companion object {

        private var upisanaIstrazivanja:MutableList<Istrazivanje> = mutableListOf();

        init {

            upisanaIstrazivanja.addAll(upisanUIstrazivanja());

        }

        fun getIstrazivanjeByGodina(god: Int):List<Istrazivanje>{
            return upisanaIstrazivanja
        }

        fun getAll(): List<Istrazivanje> {
            return svaIstrazivanja();
        }

        fun getUpisani(): List<Istrazivanje> {
            return upisanaIstrazivanja;
        }

        fun upisiIstrazivanje(istrazivanje: Istrazivanje){
            upisanaIstrazivanja.add(istrazivanje);
        }

    }

}