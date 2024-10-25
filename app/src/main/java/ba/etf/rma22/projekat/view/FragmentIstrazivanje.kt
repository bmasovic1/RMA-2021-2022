package ba.etf.rma22.projekat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.view.isVisible
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.viewmodel.GodinaSViewModel
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel


class FragmentIstrazivanje (): Fragment() {

    companion object {
        fun newInstance(adapter: ViewPagerAdapter) =
            FragmentIstrazivanje().apply {
                this.pagerAdapter = adapter
            }
    }

    private lateinit var pagerAdapter: ViewPagerAdapter

    private lateinit var godineSpinner: Spinner;
    private lateinit var istrazivanjaSpinner: Spinner;
    private lateinit var grupeSpinner: Spinner;

    private lateinit var dodaj: Button;

    private lateinit var istrazivanjeAdapter: ArrayAdapter<String>
    private lateinit var grupeAdapter: ArrayAdapter<String>
    var istrazivanjeViewModel = IstrazivanjeViewModel()
    var grupaViewModel = GrupaViewModel()
    var godinaSViewModel = GodinaSViewModel()

    var godina:Int=0;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_istrazivanje, container, false)

        godineSpinner = view.findViewById(R.id.odabirGodina);
        istrazivanjaSpinner = view.findViewById(R.id.odabirIstrazivanja);
        grupeSpinner = view.findViewById(R.id.odabirGrupa);

        dodaj = view.findViewById(R.id.dodajIstrazivanjeDugme)

        //onemogućen button za dodavanje
        dodaj.isVisible = false;

        godineSpinner.adapter = ArrayAdapter(requireActivity(),android.R.layout.simple_list_item_1, listOf("1","2","3","4","5"));

        istrazivanjeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mutableListOf<String>())
        grupeAdapter= ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mutableListOf<String>())


        istrazivanjaSpinner.adapter = istrazivanjeAdapter
        grupeSpinner.adapter = grupeAdapter

        godina = godinaSViewModel.dajGodinu()
        godineSpinner.setSelection(godina-1)


        grupeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if(grupeSpinner.count!=0){
                    dodaj.isVisible=true
                }

            }

            // Iako ništa ne radi mora stajati jer inače izbacuje error
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        godineSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                godina= position+1
                godinaSViewModel.postaviGodinu(position+1)

                istrazivanjeAdapter.clear()

                istrazivanjaSpinner.adapter= ArrayAdapter(context!!, android.R.layout.simple_list_item_1, istrazivanjeViewModel.dajNeUpisanaIstrazivanja(godina).map { p: Istrazivanje -> p.toString() })


                if(istrazivanjaSpinner.adapter.isEmpty) {
                    dodaj.isVisible=false
                }


                if(istrazivanjaSpinner.adapter.isEmpty) {
                    grupeAdapter.clear()
                    grupeSpinner.adapter = grupeAdapter
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                dodaj.isVisible = false;
            }

        }

        istrazivanjaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                grupeSpinner.adapter = ArrayAdapter(context!!,android.R.layout.simple_list_item_1, grupaViewModel.dajGrupeZaIstrazivanja(istrazivanjeViewModel.dajNeUpisanaIstrazivanja(godina)[position]).map { g: Grupa ->g.toString() })


                if(grupeSpinner.adapter.isEmpty) {
                    dodaj.isVisible = false
                }

                if(istrazivanjaSpinner.adapter.isEmpty) {
                    grupeAdapter.clear()
                    grupeSpinner.adapter = grupeAdapter
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                dodaj.isVisible = false;
            }
        }

        dodaj.setOnClickListener{

            istrazivanjeViewModel.upisiIstrazivanje(istrazivanjaSpinner.selectedItem.toString(),godineSpinner.selectedItem.toString().toInt())

            grupaViewModel.upisiUGrupu(grupeSpinner.selectedItem.toString(),istrazivanjaSpinner.selectedItem.toString())

            val grupa = grupaViewModel.dajGrupu(grupeSpinner.selectedItem.toString(),istrazivanjaSpinner.selectedItem.toString())

            pagerAdapter.zamijeni(1, FragmentPoruka.newInstance("Uspješno ste upisani u grupu ${grupa.naziv} istraživanja ${grupa.nazivIstrazivanja}!"))
            pagerAdapter.zamijeni(0, FragmentAnkete.newInstance(pagerAdapter))

        }

        return view
    }

}