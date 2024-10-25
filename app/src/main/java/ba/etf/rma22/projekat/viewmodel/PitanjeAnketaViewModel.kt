package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository

class PitanjeAnketaViewModel {

    fun dajPitanjaAnkete(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje>{
        return PitanjeAnketaRepository.getPitanja(nazivAnkete, nazivIstrazivanja)
    }

    fun dajOdgovor(anketa:String, istrazivanje: String, pitanje : String):Int{
        return PitanjeAnketaRepository.getOdgovor(anketa, istrazivanje, pitanje)
    }

    fun dodajOdgovor(anketa:String, istrazivanje: String, pitanje: String, odgovor : Int){
        return PitanjeAnketaRepository.setOdgovor(anketa, istrazivanje, pitanje, odgovor)
    }
}