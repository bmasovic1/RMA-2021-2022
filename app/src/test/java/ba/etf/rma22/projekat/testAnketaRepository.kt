package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)


class testAnketaRepository {

    @Test
    fun test1Za_getMyAnkete(){

        val ankete: List<Anketa>

        ankete=AnketaRepository.getMyAnkete();

        Assert.assertEquals(5, ankete.size)

        Assert.assertEquals("Anketa 1", ankete[0].naziv)
        Assert.assertEquals("Istrazivanje 1", ankete[0].nazivIstrazivanja)
        Assert.assertEquals("I1-G1", ankete[0].nazivGrupe)

        Assert.assertEquals("Anketa 5", ankete[1].naziv)
        Assert.assertEquals("Istrazivanje 3", ankete[1].nazivIstrazivanja)
        Assert.assertEquals("I3-G1", ankete[1].nazivGrupe)

        Assert.assertEquals("Anketa 13", ankete[2].naziv)
        Assert.assertEquals("Istrazivanje 6", ankete[2].nazivIstrazivanja)
        Assert.assertEquals("I6-G2", ankete[2].nazivGrupe)

        Assert.assertEquals("Anketa 22", ankete[3].naziv)
        Assert.assertEquals("Istrazivanje 10", ankete[3].nazivIstrazivanja)
        Assert.assertEquals("I10-G3", ankete[3].nazivGrupe)

        Assert.assertEquals("Anketa 26", ankete[4].naziv)
        Assert.assertEquals("Istrazivanje 12", ankete[4].nazivIstrazivanja)
        Assert.assertEquals("I12-G2", ankete[4].nazivGrupe)

    }

    @Test
    fun test2Za_geAll(){

        val ankete: List<Anketa>

        ankete=AnketaRepository.getAll();

        Assert.assertEquals(26, ankete.size)

        Assert.assertEquals("Anketa 1", ankete[0].naziv)
        Assert.assertEquals("Istrazivanje 1", ankete[0].nazivIstrazivanja)
        Assert.assertEquals("I1-G1", ankete[0].nazivGrupe)

        Assert.assertEquals("Anketa 9", ankete[8].naziv)
        Assert.assertEquals("Istrazivanje 4", ankete[8].nazivIstrazivanja)
        Assert.assertEquals("I4-G3", ankete[8].nazivGrupe)

        Assert.assertEquals("Anketa 15", ankete[14].naziv)
        Assert.assertEquals("Istrazivanje 7", ankete[14].nazivIstrazivanja)
        Assert.assertEquals("I7-G2", ankete[14].nazivGrupe)

        Assert.assertEquals("Anketa 20", ankete[19].naziv)
        Assert.assertEquals("Istrazivanje 10", ankete[19].nazivIstrazivanja)
        Assert.assertEquals("I10-G1", ankete[19].nazivGrupe)

    }

    @Test
    fun test3Za_getDone(){

        val ankete: List<Anketa>

        ankete=AnketaRepository.getDone();

        Assert.assertEquals(2, ankete.size)

        Assert.assertEquals("Anketa 1", ankete[0].naziv)
        Assert.assertEquals("Istrazivanje 1", ankete[0].nazivIstrazivanja)
        Assert.assertEquals("I1-G1", ankete[0].nazivGrupe)

        Assert.assertEquals("Anketa 5", ankete[1].naziv)
        Assert.assertEquals("Istrazivanje 3", ankete[1].nazivIstrazivanja)
        Assert.assertEquals("I3-G1", ankete[1].nazivGrupe)


    }

    @Test
    fun test4Za_getFuture(){

        val ankete: List<Anketa>

        ankete=AnketaRepository.getFuture();

        Assert.assertEquals(1, ankete.size)

        Assert.assertEquals("Anketa 26", ankete[0].naziv)
        Assert.assertEquals("Istrazivanje 12", ankete[0].nazivIstrazivanja)
        Assert.assertEquals("I12-G2", ankete[0].nazivGrupe)

    }

    @Test
    fun test5Za_getNotTaken(){

        val ankete: List<Anketa>

        ankete=AnketaRepository.getNotTaken();

        Assert.assertEquals(1, ankete.size)

        Assert.assertEquals("Anketa 22", ankete[0].naziv)
        Assert.assertEquals("Istrazivanje 10", ankete[0].nazivIstrazivanja)
        Assert.assertEquals("I10-G3", ankete[0].nazivGrupe)

    }


}