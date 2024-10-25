package ba.etf.rma22.projekat

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.view.ListaAnketaAdapter
import ba.etf.rma22.projekat.viewmodel.*


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
    private var takeAnketaViewModel =TakeAnketaViewModel()
    private var testInternet: Boolean = true;

    private var anketeUpisane = listOf<Anketa>();


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_ankete, container, false)

        filter = view.findViewById(R.id.filterAnketa)

        ankete = view.findViewById(R.id.listaAnketa)
        ankete.layoutManager = GridLayoutManager(view.context, 2);

        anketaAdapter = ListaAnketaAdapter(arrayListOf()) { anket -> pocniAnketu(anket) }
        ankete.adapter = anketaAdapter

        takeAnketaViewModel.tetInternet(requireContext(), onSuccess ={

            if(it!=true)
                testInternet=false

        }, onError = {})

        filter.adapter = ArrayAdapter<String>(

            requireActivity(),
            android.R.layout.simple_list_item_1,
            listOf("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete"));

        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (position) {

                    0 -> anketaViewModel.getMyAnkete(context!!,onSuccess = ::onSuccess,onError = ::onError)
                    1 -> anketaViewModel.getAll(context!!,onSuccess = ::onSuccess,onError = ::onError)

                }

                ankete.adapter?.notifyDataSetChanged()

            }

            //i ako nista ne radi mora stajati jer inače izbacuje errore
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        return view
    }

    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onSuccess(anke: List<Anketa>) {
        anketaAdapter.ankete = anke.sortedBy { it.naziv }
        ankete.adapter?.notifyDataSetChanged()
        anketeUpisane=anke
    }

    private fun pocniAnketu(ank: Anketa){


        if(filter.selectedItem.toString()=="Sve moje ankete") {

            takeAnketaViewModel.zapoceteAnkete(requireContext(), onSuccess ={

                val zapoceteAn = it
                var test=0

                for(zap in zapoceteAn){
                    if(zap.AnketumId==ank.id){
                        test=1

                        pitanjeAnketaViewModel.dajPitanjaAnkete(requireContext(), ank.id, onSuccess = {

                            var lista: List<Pitanje>
                            lista = it

                            var br: Int
                            br = 0

                            if (lista.count() != 1 && lista.count() != 0) {

                                for (item in lista) {

                                    if (br == 0) {
                                        pagerAdapter.zamijeni(0, FragmentPitanje.newInstance(zap, item, lista.count() + 1, ank, pagerAdapter))
                                    } else if (br == 1) {
                                        pagerAdapter.zamijeni(1, FragmentPitanje.newInstance(zap, item, lista.count() + 1, ank, pagerAdapter))
                                    } else {
                                        pagerAdapter.add(br, FragmentPitanje.newInstance(zap, item, lista.count() + 1, ank, pagerAdapter))
                                    }

                                    br++

                                }

                                pagerAdapter.add(
                                    br,
                                    FragmentPredaj.newInstance(ank, lista.count(), pagerAdapter)
                                )

                            } else if (lista.count() == 1) {

                                pagerAdapter.zamijeni(0, FragmentPitanje.newInstance(zap, lista[0], lista.count() + 1, ank, pagerAdapter))
                                pagerAdapter.zamijeni(1, FragmentPredaj.newInstance(ank, lista.count(), pagerAdapter))

                            }


                        }, onError = ::onError)

                    }
                }

                if(test==0 && testInternet){
                    takeAnketaViewModel.zapociAnketu(requireContext(),ank.id, onSuccess ={

                        var pocetaA=it

                        var lista: List<Pitanje>

                        pitanjeAnketaViewModel.dajPitanjaAnkete(requireContext(),ank.id, onSuccess = {

                            lista = it

                            var br: Int
                            br = 0

                            if (lista.count() != 1 && lista.count() != 0) {

                                for (item in lista) {

                                    if (br == 0) {
                                        pagerAdapter.zamijeni(0, FragmentPitanje.newInstance(pocetaA, item, lista.count() + 1, ank, pagerAdapter))
                                    } else if (br == 1) {
                                        pagerAdapter.zamijeni(1, FragmentPitanje.newInstance(pocetaA, item, lista.count() + 1, ank, pagerAdapter))
                                    } else {
                                        pagerAdapter.add(br, FragmentPitanje.newInstance(pocetaA, item, lista.count() + 1, ank, pagerAdapter))
                                    }

                                    br++

                                }

                                pagerAdapter.add(
                                    br,
                                    FragmentPredaj.newInstance(ank, lista.count(), pagerAdapter)
                                )

                            } else if (lista.count() == 1) {

                                pagerAdapter.zamijeni(0, FragmentPitanje.newInstance(pocetaA, lista[0], lista.count() + 1, ank, pagerAdapter))
                                pagerAdapter.zamijeni(1, FragmentPredaj.newInstance(ank, lista.count(), pagerAdapter))

                            }


                        }, onError = ::onError)

                    },onError = ::onError)

                }
                else {



                }

            }, onError = {
                takeAnketaViewModel.zapociAnketu(requireContext(),ank.id, onSuccess ={

                    var pocetaA=it

                    var lista: List<Pitanje>

                    pitanjeAnketaViewModel.dajPitanjaAnkete(requireContext(),ank.id, onSuccess = {

                        lista = it

                        var br: Int
                        br = 0

                        if (lista.count() != 1 && lista.count() != 0) {

                            for (item in lista) {

                                if (br == 0) {
                                    pagerAdapter.zamijeni(0, FragmentPitanje.newInstance(pocetaA, item, lista.count() + 1, ank, pagerAdapter))
                                } else if (br == 1) {
                                    pagerAdapter.zamijeni(1, FragmentPitanje.newInstance(pocetaA, item, lista.count() + 1, ank, pagerAdapter))
                                } else {
                                    pagerAdapter.add(br, FragmentPitanje.newInstance(pocetaA, item, lista.count() + 1, ank, pagerAdapter))
                                }

                                br++

                            }

                            pagerAdapter.add(
                                br,
                                FragmentPredaj.newInstance(ank, lista.count(), pagerAdapter)
                            )

                        } else if (lista.count() == 1) {

                            pagerAdapter.zamijeni(0, FragmentPitanje.newInstance(pocetaA, lista[0], lista.count() + 1, ank, pagerAdapter))
                            pagerAdapter.zamijeni(1, FragmentPredaj.newInstance(ank, lista.count(), pagerAdapter))

                        }


                    }, onError = ::onError)

                },onError = ::onError)
            } )


        }

    }

}

