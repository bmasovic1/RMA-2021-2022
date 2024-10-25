package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.GrupaRepository

class GrupaViewModel {

    fun dajGrupeZaIstrazivanja(istrazivanje: Istrazivanje):List<Grupa>{
        return GrupaRepository.getGroupsByIstrazivanje(istrazivanje.naziv)
    }

    //Ako je potrebo samo grupe u koje nije upisan
    fun dajNeUpisaneGrupe(ist: String): List<Grupa> {
        return GrupaRepository.getGroupsByIstrazivanje(ist).filter{ g:Grupa -> !GrupaRepository.getMyGroups().contains(g)}
    }

    fun upisiUGrupu(grup:String, istr:String){
        GrupaRepository.upisiGrupu(GrupaRepository.getGroupsByIstrazivanje(istr).first { grupa: Grupa -> grupa.naziv == grup })
    }

    fun dajGrupu(grup: String,istrazivanje: String):Grupa{
        return GrupaRepository.getGroupsByIstrazivanje(istrazivanje).first{ grupa: Grupa -> grupa.naziv == grup }
    }

}