package ba.etf.rma22.projekat.data.static

import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*


fun postaviDatum(g:Int,m:Int,d:Int): Date {

    var cal: Calendar = Calendar.getInstance()
    cal.set(g,m-1,d);
    return cal.time
}


fun sveAnkete():List<Anketa>{

    return listOf(
/*
        Anketa("Anketa 1","Istrazivanje 1",postaviDatum(2022,1,1),postaviDatum(2023,10,14),null,73,"I1-G1",null),
        Anketa("Anketa 2","Istrazivanje 1",postaviDatum(2023,1,13),postaviDatum(2023,2,1),null,5,"I1-G2",null),

        Anketa("Anketa 3","Istrazivanje 2",postaviDatum(2022,5,11),postaviDatum(2022,10,25),null,67,"I2-G1",null),
        Anketa("Anketa 4","Istrazivanje 2",postaviDatum(2022,2,10),postaviDatum(2024,5,15),null,1,"I2-G2",null),

        Anketa("Anketa 5","Istrazivanje 3",postaviDatum(2022,2,20),postaviDatum(2023,4,21),postaviDatum(2022,4,11),7,"I3-G1",0.75f),
        Anketa("Anketa 6","Istrazivanje 3",postaviDatum(2022,4,10),postaviDatum(2023,4,15),postaviDatum(2021,5,12),34,"I3-G2",0.4f),

        Anketa("Anketa 7","Istrazivanje 4", postaviDatum(2022,1,18),postaviDatum(2022,7,13),Calendar.getInstance().time,34,"I4-G1",0.25F),
        Anketa("Anketa 8","Istrazivanje 4",postaviDatum(2025,1,1),postaviDatum(2025,2,1),null,22,"I4-G2",null),
        Anketa("Anketa 9","Istrazivanje 4",postaviDatum(2022,5,1),postaviDatum(2022,4,23),null,12,"I4-G3",null),

        Anketa("Anketa 10","Istrazivanje 5",postaviDatum(2021,12,10),postaviDatum(2022,10,15),postaviDatum(2022,1,11),37,"I5-G1",0.50f),
        Anketa("Anketa 11","Istrazivanje 5",postaviDatum(2021,4,20),postaviDatum(2021,12,21),null,45,"I5-G2",null),

        Anketa("Anketa 12","Istrazivanje 6",postaviDatum(2022,4,20),postaviDatum(2022,1,21),null,65,"I6-G1",null),
        Anketa("Anketa 13","Istrazivanje 6",postaviDatum(2022,1,1),postaviDatum(2023,10,14),null,55,"I6-G2",null),

        Anketa("Anketa 14","Istrazivanje 7",postaviDatum(2022,1,13),postaviDatum(2022,1,30),null,31,"I7-G1",null),
        Anketa("Anketa 15","Istrazivanje 7",postaviDatum(2022,3,27),postaviDatum(2022,11,23),null,23,"I7-G2",null),

        Anketa("Anketa 16","Istrazivanje 8",postaviDatum(2022,3,12),postaviDatum(2022,9,28),postaviDatum(2021,3,19),47,"I8-G1",0.91f),
        Anketa("Anketa 17","Istrazivanje 8",postaviDatum(2022,7,1),postaviDatum(2022,4,30),null,21,"I8-G2",null),

        Anketa("Anketa 18","Istrazivanje 9",postaviDatum(2021,3,12),postaviDatum(2021,7,17),null,29,"I9-G1",null),
        Anketa("Anketa 19","Istrazivanje 9",postaviDatum(2022,1,15),postaviDatum(2022,2,9),null,47,"I9-G2",null),

        Anketa("Anketa 20","Istrazivanje 10",postaviDatum(2022,2,28),postaviDatum(2022,8,3),null,56,"I10-G1",null),
        Anketa("Anketa 21","Istrazivanje 10",postaviDatum(2022,7,18),postaviDatum(2022,11,19),null,60,"I10-G2",null),
        Anketa("Anketa 22","Istrazivanje 10",postaviDatum(2022,1,1),postaviDatum(2021,1,3),null,52,"I10-G3",null),

        Anketa("Anketa 23","Istrazivanje 11",postaviDatum(2021,10,6),postaviDatum(2021,10,7),null,56,"I11-G1",null),
        Anketa("Anketa 24","Istrazivanje 11",postaviDatum(2022,1,1),postaviDatum(2022,7,13),postaviDatum(2021,1,12),54,"I11-G2",0.33f),

        Anketa("Anketa 25","Istrazivanje 12",postaviDatum(2022,4,30),postaviDatum(2022,9,27),null,61,"I12-G1",null),
        Anketa("Anketa 26","Istrazivanje 12",postaviDatum(2022,5,11),postaviDatum(2022,6,4),null,27,"I12-G2",null)
*/
    );
}

/*
    val naziv: String,
    val nazivIstraživanja: String,
    val datumPocetka: Date,
    val datumKraj: Date,
    val datumRada: Date?,
    val trajanje: Int,
    val nazivGrupe: String,
    val progres: Float?
 */