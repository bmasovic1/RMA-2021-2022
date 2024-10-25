package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.static.sveAnkete

class AnketaViewModel {

    private var ankete:MutableList<Anketa> = mutableListOf();
    init {
        ankete.addAll(sveAnkete())
    }

    fun dajAnketeZaKorisnika(): List<Anketa> {
        return AnketaRepository.getMyAnkete()
    }

    fun dajSveAnkete(): List<Anketa> {
        return AnketaRepository.getAll()
    }

    fun dajUrađeneAnkete(): List<Anketa> {
        return AnketaRepository.getDone()
    }

    fun dajBuduceAnkete(): List<Anketa> {
        //kada se koristi ova zakomentarisana verzija prikazuju se i ankete unutar korisnikovih grupa koje su buduće i koje su aktivne a nisu još urađene
        //no tada jedan od testova ne prolati PocetniTest -> filtriranjeTest
        //return AnketaRepository.getMyAnkete().filter({ a:Anketa -> (a.datumPocetka.before(Date()) && a.datumKraj.after(Date()) && a.datumRada==null) || (a.datumRada==null && a.datumPocetka.after(Date()))})
        return AnketaRepository.getFuture()

    }

    fun dajProsleAnkete(): List<Anketa> {
        return AnketaRepository.getNotTaken()
    }


}