package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.static.dajPitanjaAnketa
import ba.etf.rma22.projekat.data.static.dajSvaPitanja

class PitanjeAnketaRepository {


    companion object{

        var listaPitanjaAnketa = mutableListOf<PitanjeAnketa>()

        init {
            listaPitanjaAnketa.addAll(dajPitanjaAnketa())
        }

        fun getPitanjeAnketa(anketa :String, istrazivanje: String):HashSet<String>{

            return dajPitanjaAnketa().filter { p: PitanjeAnketa -> p.anketa==anketa && p.nazivIstrazivanja==istrazivanje }.map { p -> p.naziv }.toHashSet()

        }

        fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje> {

            val pitanja = getPitanjeAnketa(nazivAnkete,nazivIstrazivanja)
            return PitanjeRepository.getPitanja().filter { p: Pitanje ->  pitanja.contains(p.naziv)}

        }

        fun getOdgovor(anketa:String, istrazivanje: String, pitanje : String): Int {
            var list: List<PitanjeAnketa> =listaPitanjaAnketa.filter({ pa: PitanjeAnketa -> pa.anketa==anketa && pa.nazivIstrazivanja==istrazivanje && pa.naziv==pitanje });
            return list[0].odgovor
        }

        fun setOdgovor(anketa:String, istrazivanje: String, pitanje: String, odgovor : Int){
            var list: List<PitanjeAnketa> =listaPitanjaAnketa.filter({ pa: PitanjeAnketa -> pa.anketa==anketa && pa.nazivIstrazivanja==istrazivanje && pa.naziv==pitanje });
            list[0].odgovor=odgovor
        }

    }

}