package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

class testGrupaRepository {

    @Test
    fun test1Za_getGroupsByIstrazivanje(){

        val grupe: List<Grupa>
        val grupe2: List<Grupa>

        grupe=GrupaRepository.getGroupsByIstrazivanje("Istrazivanje 4")

        Assert.assertEquals(3, grupe.size)
        Assert.assertEquals("I4-G1", grupe[0].naziv)
        Assert.assertEquals("Istrazivanje 4", grupe[0].nazivIstrazivanja)
        Assert.assertEquals("I4-G2", grupe[1].naziv)
        Assert.assertEquals("Istrazivanje 4", grupe[1].nazivIstrazivanja)
        Assert.assertEquals("I4-G3", grupe[2].naziv)
        Assert.assertEquals("Istrazivanje 4", grupe[2].nazivIstrazivanja)

        grupe2=GrupaRepository.getGroupsByIstrazivanje("Istrazivanje 9")

        Assert.assertEquals(2, grupe2.size)
        Assert.assertEquals("I9-G1", grupe2[0].naziv)
        Assert.assertEquals("Istrazivanje 9", grupe2[0].nazivIstrazivanja)
        Assert.assertEquals("I9-G2", grupe2[1].naziv)
        Assert.assertEquals("Istrazivanje 9", grupe2[1].nazivIstrazivanja)

    }

    @Test
    fun test2Za_getMyGroups(){

        val upisaneGrupe: List<Grupa>

        upisaneGrupe=GrupaRepository.getMyGroups();

        Assert.assertEquals(5, upisaneGrupe.size)
        Assert.assertEquals("I1-G1", upisaneGrupe[0].naziv)
        Assert.assertEquals("Istrazivanje 1", upisaneGrupe[0].nazivIstrazivanja)

        Assert.assertEquals("I3-G1", upisaneGrupe[1].naziv)
        Assert.assertEquals("Istrazivanje 3", upisaneGrupe[1].nazivIstrazivanja)

        Assert.assertEquals("I6-G2", upisaneGrupe[2].naziv)
        Assert.assertEquals("Istrazivanje 6", upisaneGrupe[2].nazivIstrazivanja)

        Assert.assertEquals("I10-G3", upisaneGrupe[3].naziv)
        Assert.assertEquals("Istrazivanje 10", upisaneGrupe[3].nazivIstrazivanja)

        Assert.assertEquals("I12-G2", upisaneGrupe[4].naziv)
        Assert.assertEquals("Istrazivanje 12", upisaneGrupe[4].nazivIstrazivanja)

    }

    @Test
    fun test3Za_upisiGrupu(){

        Assert.assertEquals(5, GrupaRepository.getMyGroups().size)
        GrupaRepository.upisiGrupu(Grupa("I8-G2", "Istrazivanje 8"))

        val grupe: List<Grupa>

        grupe=GrupaRepository.getMyGroups();

        Assert.assertEquals(6, GrupaRepository.getMyGroups().size)
        Assert.assertEquals("I8-G2", grupe[5].naziv)
        Assert.assertEquals("Istrazivanje 8", grupe[5].nazivIstrazivanja)

    }


}