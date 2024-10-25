package ba.etf.rma22.projekat

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.GodinaSViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import java.util.*


class FragmentPitanje(private var pitanja:Pitanje, private var velicina:Int, private var anketa:Anketa) : Fragment() {

    lateinit var pitanje:TextView
    lateinit var listView: ListView
    lateinit var zaustavi: Button;
    private lateinit var pagerAdapter: ViewPagerAdapter
    val plava = Color.parseColor("#0000FF")
    var test: Int = 0
    var test2: Int = 0
    var godinaSViewModel = GodinaSViewModel()

    private var pitanjeAnketaViewModel = PitanjeAnketaViewModel();


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_pitanje, container, false)

        pitanje = view.findViewById(R.id.tekstPitanja)
        pitanje.text = pitanja.tekst

        listView = view.findViewById(R.id.odgovoriLista)
        zaustavi = view.findViewById(R.id.dugmeZaustavi)

        provjera()

        val listItems = arrayOfNulls<String>(pitanja.opcije.size)

        for (i in 0 until pitanja.opcije.size) {

            val recipe = pitanja.opcije[i]
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


        var odgovor = pitanjeAnketaViewModel.dajOdgovor(anketa.naziv, anketa.nazivIstrazivanja, pitanja.naziv)
        if (odgovor!=0 && odgovor!=null){

            listView.performContextClick()
            listView.callOnClick()


            if(listView.getChildAt(odgovor)!=null)
                (listView.getChildAt(odgovor) as TextView).setTextColor(plava)

            listView.performContextClick()
            listView.callOnClick()

        }

        return view

    }

    var br:Int = 0

    val itemClickedListener = object : AdapterView.OnItemClickListener{


        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            var vr:Float

            var odgovor = pitanjeAnketaViewModel.dajOdgovor(anketa.naziv, anketa.nazivIstrazivanja, pitanja.naziv)

            if(br==0 && odgovor==0 && test==0) {

                (parent!!.getChildAt(position) as TextView).setTextColor(plava)

                vr=(1.toDouble()/(velicina-1)).toFloat()

                if(anketa.progres==null){
                    anketa.progres = vr
                }
                else {
                    anketa.progres = anketa.progres?.plus(vr)
                }

                pitanjeAnketaViewModel.dodajOdgovor(anketa.naziv, anketa.nazivIstrazivanja, pitanja.naziv, position+1)

            }

            pagerAdapter.zamijeni(velicina-1, FragmentPredaj.newInstance(anketa,velicina-1,pagerAdapter))

            br++

        }

    }


    fun provjera(){
        if (anketa.datumRada!=null) {
            test=1
            test2=1
        }
        else {
            test=0
        }

        if(anketa.datumKraj.before(Date())){
            test=1
        }

    }

    override fun onResume() {
        super.onResume()

        listView.performClick()

        var odgovor = pitanjeAnketaViewModel.dajOdgovor(anketa.naziv, anketa.nazivIstrazivanja, pitanja.naziv)

        if (odgovor!=0 &&odgovor!=null){

            if(listView.getChildAt(odgovor-1)!=null)
                (listView.getChildAt(odgovor-1) as TextView).setTextColor(plava)
        }

    }


    companion object {
        fun newInstance(poruka: Pitanje,velicina:Int, anketa:Anketa, adapter: ViewPagerAdapter) =
            FragmentPitanje(poruka,velicina, anketa).apply {
                this.pagerAdapter = adapter
            }
    }
}