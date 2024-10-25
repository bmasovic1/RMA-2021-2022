package ba.etf.rma22.projekat.data.static

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa

fun dajSvaPitanja():List<Pitanje>{

    return mutableListOf<Pitanje>(

        Pitanje("p0","Koji NBA tim vam je najdraži.", listOf("LA Lakers","Miami Heat","Chicago Bulls","Boston Celtics")),
        Pitanje("p1","Da li pratite tenis?", listOf("Da","Ne","Povremeno")),
        Pitanje("p2","Dnevno koliko sati provodite prateći sportske aktivnosti?", listOf("1h-2h","3h-5h",">5h","Ne pratim sportske aktivnosti")),
        Pitanje("p3","Koji fudbalski tim vam je najdraži.", listOf("Real Madrid CF","FC Barcelona","Juventus F.C.","Paris Saint-Germain F.C.", "FC Bayern Munich", "Manchester United F.C.")),
        Pitanje("p4","Ko je trenutno najbolji fudbaler na svijetu?", listOf("Lionel Messi","Cristiano Ronaldo","Karim Benzema","Kylian Mbappé", "Robert Lewandowski", "Drugi"))

    )
}

fun dajPitanjaAnketa():List<PitanjeAnketa>{

    return listOf(

        PitanjeAnketa("p0","Anketa 1","Istrazivanje 1",0),
        PitanjeAnketa("p1","Anketa 1","Istrazivanje 1",0),
        PitanjeAnketa("p2","Anketa 1","Istrazivanje 1",0),
        PitanjeAnketa("p3","Anketa 1","Istrazivanje 1",0),
        PitanjeAnketa("p4","Anketa 1","Istrazivanje 1",0),

        PitanjeAnketa("p3","Anketa 2","Istrazivanje 1",0),
        PitanjeAnketa("p1","Anketa 2","Istrazivanje 1",0),

        PitanjeAnketa("p3","Anketa 3","Istrazivanje 2",0),
        PitanjeAnketa("p4","Anketa 3","Istrazivanje 2",0),

        PitanjeAnketa("p4","Anketa 4","Istrazivanje 2",0),
        PitanjeAnketa("p1","Anketa 4","Istrazivanje 2",0),
        PitanjeAnketa("p2","Anketa 4","Istrazivanje 2",0),

        PitanjeAnketa("p0","Anketa 5","Istrazivanje 3",1),
        PitanjeAnketa("p1","Anketa 5","Istrazivanje 3",3),
        PitanjeAnketa("p2","Anketa 5","Istrazivanje 3",0),
        PitanjeAnketa("p3","Anketa 5","Istrazivanje 3",2),

        PitanjeAnketa("p0","Anketa 6","Istrazivanje 3",1),
        PitanjeAnketa("p1","Anketa 6","Istrazivanje 3",0),
        PitanjeAnketa("p2","Anketa 6","Istrazivanje 3",2),
        PitanjeAnketa("p3","Anketa 6","Istrazivanje 3",0),

        PitanjeAnketa("p0","Anketa 7","Istrazivanje 4",0),
        PitanjeAnketa("p1","Anketa 7","Istrazivanje 4",0),
        PitanjeAnketa("p2","Anketa 7","Istrazivanje 4",3),
        PitanjeAnketa("p3","Anketa 7","Istrazivanje 4",0),

        PitanjeAnketa("p3","Anketa 8","Istrazivanje 4",0),
        PitanjeAnketa("p4","Anketa 8","Istrazivanje 4",0),

        PitanjeAnketa("p3","Anketa 9","Istrazivanje 4",0),

        PitanjeAnketa("p3","Anketa 10","Istrazivanje 5",0),
        PitanjeAnketa("p1","Anketa 10","Istrazivanje 5",3),

        PitanjeAnketa("p4","Anketa 11","Istrazivanje 5",0),

        PitanjeAnketa("p4","Anketa 12","Istrazivanje 6",0),
        PitanjeAnketa("p1","Anketa 12","Istrazivanje 6",0),

        PitanjeAnketa("p0","Anketa 13","Istrazivanje 6",0),
        PitanjeAnketa("p1","Anketa 13","Istrazivanje 6",0),
        PitanjeAnketa("p2","Anketa 13","Istrazivanje 6",0),
        PitanjeAnketa("p3","Anketa 13","Istrazivanje 6",0),
        PitanjeAnketa("p4","Anketa 13","Istrazivanje 6",0),


        PitanjeAnketa("p2","Anketa 14","Istrazivanje 7",0),
        PitanjeAnketa("p3","Anketa 14","Istrazivanje 7",0),

        PitanjeAnketa("p4","Anketa 15","Istrazivanje 7",0),
        PitanjeAnketa("p1","Anketa 15","Istrazivanje 7",0),
        PitanjeAnketa("p0","Anketa 15","Istrazivanje 7",0),

        PitanjeAnketa("p4","Anketa 16","Istrazivanje 8",1),
        PitanjeAnketa("p1","Anketa 16","Istrazivanje 8",3),
        PitanjeAnketa("p0","Anketa 16","Istrazivanje 8",4),

        PitanjeAnketa("p3","Anketa 17","Istrazivanje 8",0),
        PitanjeAnketa("p1","Anketa 17","Istrazivanje 8",0),

        PitanjeAnketa("p4","Anketa 18","Istrazivanje 9",0),
        PitanjeAnketa("p1","Anketa 18","Istrazivanje 9",0),
        PitanjeAnketa("p2","Anketa 18","Istrazivanje 9",0),

        PitanjeAnketa("p1","Anketa 19","Istrazivanje 9",0),
        PitanjeAnketa("p2","Anketa 19","Istrazivanje 9",0),
        PitanjeAnketa("p0","Anketa 19","Istrazivanje 9",0),

        PitanjeAnketa("p4","Anketa 20","Istrazivanje 10",0),
        PitanjeAnketa("p3","Anketa 20","Istrazivanje 10",0),
        PitanjeAnketa("p2","Anketa 20","Istrazivanje 10",0),
        PitanjeAnketa("p0","Anketa 20","Istrazivanje 10",0),

        PitanjeAnketa("p1","Anketa 21","Istrazivanje 10",0),
        PitanjeAnketa("p3","Anketa 21","Istrazivanje 10",0),

        PitanjeAnketa("p4","Anketa 22","Istrazivanje 10",0),

        PitanjeAnketa("p3","Anketa 23","Istrazivanje 11",0),
        PitanjeAnketa("p1","Anketa 23","Istrazivanje 11",0),

        PitanjeAnketa("p2","Anketa 24","Istrazivanje 11",0),
        PitanjeAnketa("p1","Anketa 24","Istrazivanje 11",3),
        PitanjeAnketa("p3","Anketa 24","Istrazivanje 11",0),

        PitanjeAnketa("p2","Anketa 25","Istrazivanje 12",0),
        PitanjeAnketa("p3","Anketa 25","Istrazivanje 12",0),
        PitanjeAnketa("p4","Anketa 25","Istrazivanje 12",0),

        PitanjeAnketa("p1","Anketa 26","Istrazivanje 12",0),
        PitanjeAnketa("p0","Anketa 26","Istrazivanje 12",0)

        )
}