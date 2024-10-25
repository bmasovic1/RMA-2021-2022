package ba.etf.rma22.projekat

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.view.ListaAnketaAdapter
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import java.util.*

class FragmentAnkete : Fragment() {

    companion object {
        fun newInstance(adapter: ViewPagerAdapter) =
            FragmentAnkete().apply {
                this.pagerAdapter = adapter
            }
    }

    private lateinit var pagerAdapter: ViewPagerAdapter

    private lateinit var ankete: RecyclerView;
    private lateinit var anketaAdapter: ListaAnketaAdapter;
    private lateinit var filter: Spinner;
    //poc god
    private var godina: String = "1";

    private var anketaViewModel = AnketaViewModel()
    private var istrazivanjeViewModel = IstrazivanjeViewModel()
    private var grupaViewModel = GrupaViewModel();
    private var pitanjeAnketaViewModel = PitanjeAnketaViewModel();


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_ankete, container, false)

        filter = view.findViewById(R.id.filterAnketa)

        ankete = view.findViewById(R.id.listaAnketa)
        ankete.layoutManager = GridLayoutManager(view.context, 2);

        anketaAdapter = ListaAnketaAdapter(arrayListOf()) { anket -> pocniAnketu(anket) }
        ankete.adapter = anketaAdapter


        filter.adapter = ArrayAdapter<String>(

            requireActivity(),
            android.R.layout.simple_list_item_1,
            listOf("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete"));

        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (position) {

                    0 -> anketaAdapter.ankete = (anketaViewModel.dajAnketeZaKorisnika()).sortedBy { it.datumPocetka }
                    1 -> anketaAdapter.ankete = (anketaViewModel.dajSveAnkete()).sortedBy { it.datumPocetka }
                    2 -> anketaAdapter.ankete = (anketaViewModel.dajUrađeneAnkete()).sortedBy { it.datumPocetka }
                    3 -> anketaAdapter.ankete = (anketaViewModel.dajBuduceAnkete()).sortedBy { it.datumPocetka }
                    4 -> anketaAdapter.ankete = (anketaViewModel.dajProsleAnkete()).sortedBy { it.datumPocetka }

                }

                ankete.adapter?.notifyDataSetChanged()

            }

            //i ako nista ne radi mora stajati jer inače izbacuje errore
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        return view
    }


    private fun pocniAnketu(ank: Anketa){

        if((ank.datumPocetka.after(Date()) && ank.progres==null) || !anketaViewModel.dajAnketeZaKorisnika().contains(ank)) {

        }
        else{
            var lista : List<Pitanje>
            lista = pitanjeAnketaViewModel.dajPitanjaAnkete(ank.naziv, ank.nazivIstrazivanja)


            var br:Int
            br=0

            if(lista.count()!=1 && lista.count()!=0) {

                for (item in lista) {

                    if (br == 0) {
                        pagerAdapter.zamijeni(
                            0,
                            FragmentPitanje.newInstance(item, lista.count() + 1, ank, pagerAdapter)
                        )
                    } else if (br == 1) {
                        pagerAdapter.zamijeni(
                            1,
                            FragmentPitanje.newInstance(item, lista.count() + 1, ank, pagerAdapter)
                        )
                    } else {
                        pagerAdapter.add(
                            br,
                            FragmentPitanje.newInstance(item, lista.count() + 1, ank, pagerAdapter)
                        )
                    }

                    br++

                }

                pagerAdapter.add(br, FragmentPredaj.newInstance(ank, lista.count() , pagerAdapter))

            }
            else if(lista.count()==1){

                pagerAdapter.zamijeni(0, FragmentPitanje.newInstance(lista[0], lista.count() + 1, ank, pagerAdapter))
                pagerAdapter.zamijeni(1, FragmentPredaj.newInstance(ank, lista.count() , pagerAdapter))

            }

        }
    }

}

