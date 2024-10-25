package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository

class IstrazivanjeViewModel {

    fun upisiIstrazivanje(istr:String, god:Int){
        IstrazivanjeRepository.upisiIstrazivanje(IstrazivanjeRepository.getIstrazivanjeByGodina(god).filter { i: Istrazivanje -> i.naziv==istr }.first())
    }

    fun dajNeUpisanaIstrazivanja(god : Int):List<Istrazivanje>{
        return IstrazivanjeRepository.getIstrazivanjeByGodina(god).filter{ i:Istrazivanje -> !IstrazivanjeRepository.getUpisani().contains(i)}
    }

}