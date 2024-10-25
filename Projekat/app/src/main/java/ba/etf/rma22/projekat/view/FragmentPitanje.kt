package ba.etf.rma22.projekat

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma22.projekat.viewmodel.TakeAnketaViewModel


class FragmentPitanje(private var pocetaA:AnketaTaken, private var pitanja:Pitanje, private var velicina:Int, private var anketa:Anketa) : Fragment() {

    lateinit var pitanje:TextView
    lateinit var listView: ListView
    lateinit var zaustavi: Button;
    private lateinit var pagerAdapter: ViewPagerAdapter
    val plava = Color.parseColor("#0000FF")
    var odgovorViewModel =  OdgovorViewModel()
    private var takeAnketaViewModel = TakeAnketaViewModel()

    private var testInternet: Boolean = true;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pitanje, container, false)

        takeAnketaViewModel.tetInternet(requireContext(), onSuccess ={

            if(it!=true)
                testInternet=false

        }, onError = {})

        pitanje = view.findViewById(R.id.tekstPitanja)
        //pitanje.text = pitanja.tekstPitanja

        listView = view.findViewById(R.id.odgovoriLista)
        zaustavi = view.findViewById(R.id.dugmeZaustavi)

        val listItems = arrayOfNulls<String>(pitanja.opcije!!.size)

        for (i in 0 until pitanja.opcije!!.size) {

            val recipe = pitanja.opcije!![i]
            listItems[i] = recipe
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter


        zaustavi.setOnClickListener {

            if (velicina == 1) {

                pagerAdapter.zamijeni(0, FragmentAnkete.newInstance(pagerAdapter))
                pagerAdapter.notifyDataSetChanged();

                pagerAdapter.add(1, FragmentIstrazivanje.newInstance(pagerAdapter))
                pagerAdapter.notifyDataSetChanged();

            }

            else if (velicina >= 2) {

                pagerAdapter.zamijeni(0, FragmentAnkete.newInstance(pagerAdapter))
                pagerAdapter.notifyDataSetChanged();

                pagerAdapter.zamijeni(1, FragmentIstrazivanje.newInstance(pagerAdapter))
                pagerAdapter.notifyDataSetChanged();

                for (i in velicina - 1 downTo 2) {
                    pagerAdapter.remove(i.toInt());
                    pagerAdapter.notifyDataSetChanged();

                }

            }

        }

        listView.onItemClickListener = itemClickedListener

        listView.performContextClick()
        listView.callOnClick()

        listView.performClick()


        odgovorViewModel.getOdgovori(requireContext(),anketa.id, onSuccess = {
            val odgovori = it
            var odgovor = 0
            for(od in odgovori) {
                if( od.pitanjeId==pitanja.id ){
                    odgovor=od.odgovoreno
                }
            }
            if (odgovor!=0 && odgovor!=null){

                listView.performContextClick()
                listView.callOnClick()


                if(listView.getChildAt(odgovor)!=null)
                //(listView.getChildAt(odgovor) as TextView).setTextColor(plava)

                    listView.performContextClick()
                listView.callOnClick()

            }
        },onError = ::onError)
        return view

    }

    fun onError() {
    }

    var br:Int = 0

    val itemClickedListener = object : AdapterView.OnItemClickListener{


        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            var vr:Float

            odgovorViewModel.getOdgovori(context!!, anketa.id, onSuccess = {
                val odgovori = it
                var odgovor = 0
                for(od in odgovori) {
                    if( od.pitanjeId==pitanja.id  ){
                        odgovor=od.odgovoreno
                    }
                }
                if(br==0 && odgovor==0 && testInternet ) {

                    (parent!!.getChildAt(position) as TextView).setTextColor(plava)

                    vr=(1.toDouble()/(velicina-1)).toFloat()


                    odgovorViewModel.setOdgovor(context!!,pocetaA.id,pitanja.id,position+1, onSuccess = {

                        anketa.progres = it.toFloat()/100

                    },onError = ::onError)

                }

                pagerAdapter.zamijeni(velicina-1, FragmentPredaj.newInstance(anketa,velicina-1,pagerAdapter))

                br++
            },onError = ::onError)
        }

    }


    override fun onResume() {
        super.onResume()

        listView.performClick()

        odgovorViewModel.getOdgovori(requireContext(), anketa.id, onSuccess = {
            val odgovori = it
            var odgovor = 0
            for(od in odgovori) {

                if( od.pitanjeId== pitanja.id ){
                    odgovor=od.odgovoreno
                }
            }

            if (odgovor!=0 && odgovor!=null){

                if(listView.getChildAt(odgovor-1)!=null)
                    (listView.getChildAt(odgovor-1) as TextView).setTextColor(plava)
            }
        },onError = ::onError)

    }


    companion object {
        fun newInstance(pocetaA:AnketaTaken, poruka: Pitanje,velicina:Int, anketa:Anketa, adapter: ViewPagerAdapter) =
            FragmentPitanje(pocetaA, poruka,velicina, anketa).apply {
                this.pagerAdapter = adapter
            }
    }
}