package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

class testItrazivanjeRepository {

    @Test
    fun test1Za_getIstrazivanjeByGodina(){
        Assert.assertEquals(3, IstrazivanjeRepository.getIstrazivanjeByGodina(1).size)
        Assert.assertEquals(2, IstrazivanjeRepository.getIstrazivanjeByGodina(4).size)
    }

    @Test
    fun test3Za_getAll(){

        val listaSvih : List<Istrazivanje>
        listaSvih=IstrazivanjeRepository.getAll();

        Assert.assertEquals("Istrazivanje 1", listaSvih[0].naziv)
        Assert.assertEquals(1, listaSvih[0].godina)

        Assert.assertEquals("Istrazivanje 2", listaSvih[1].naziv)
        Assert.assertEquals(2, listaSvih[1].godina)

        Assert.assertEquals("Istrazivanje 6", listaSvih[5].naziv)
        Assert.assertEquals(3, listaSvih[5].godina)

        Assert.assertEquals("Istrazivanje 7", listaSvih[6].naziv)
        Assert.assertEquals(4, listaSvih[6].godina)

        Assert.assertEquals("Istrazivanje 10", listaSvih[9].naziv)
        Assert.assertEquals(5, listaSvih[9].godina)


    }

    @Test
    fun test3Za_getUpisani(){

        val listaUpisanih : List<Istrazivanje>

        listaUpisanih=IstrazivanjeRepository.getUpisani();
        Assert.assertEquals(5, listaUpisanih.size)

        Assert.assertEquals("Istrazivanje 1", listaUpisanih[0].naziv)
        Assert.assertEquals(1, listaUpisanih[0].godina)

        Assert.assertEquals("Istrazivanje 3", listaUpisanih[1].naziv)
        Assert.assertEquals(4, listaUpisanih[1].godina)

        Assert.assertEquals("Istrazivanje 6", listaUpisanih[2].naziv)
        Assert.assertEquals(3, listaUpisanih[2].godina)

        Assert.assertEquals("Istrazivanje 10", listaUpisanih[3].naziv)
        Assert.assertEquals(5, listaUpisanih[3].godina)

        Assert.assertEquals("Istrazivanje 12", listaUpisanih[4].naziv)
        Assert.assertEquals(2, listaUpisanih[4].godina)

    }

    @Test
    fun test4Za_upisiIstrazivanje(){

        Assert.assertEquals(5, IstrazivanjeRepository.getUpisani().size)
        IstrazivanjeRepository.upisiIstrazivanje(Istrazivanje("Istrazivnje 12", 5))

        val istrazivanja: List<Istrazivanje>

        istrazivanja= IstrazivanjeRepository.getUpisani();

        Assert.assertEquals(6, IstrazivanjeRepository.getUpisani().size)
        Assert.assertEquals("Istrazivnje 12", istrazivanja[5].naziv)
        Assert.assertEquals(5, istrazivanja[5].godina)


    }


}