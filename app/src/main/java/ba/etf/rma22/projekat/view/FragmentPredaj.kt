package ba.etf.rma22.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*

class FragmentPredaj(private var anketa : Anketa, private var velicina:Int) : Fragment() {

    lateinit var tekst:TextView
    private lateinit var pagerAdapter: ViewPagerAdapter
    lateinit var predaj: Button;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_predaj, container, false)

        tekst = view.findViewById(R.id.progresTekst)
        predaj = view.findViewById(R.id.dugmePredaj)

        provjera()

        tekst.text = anketa.progres?.let { zaokruzi(it).toString() }+"%"

        if(anketa.progres==null)
            tekst.text="0%"

        predaj.setOnClickListener {

            anketa.datumRada=Calendar.getInstance().time

            if (velicina == 1) {

                pagerAdapter.zamijeni(0, FragmentAnkete.newInstance(pagerAdapter))
                pagerAdapter.notifyDataSetChanged();

                pagerAdapter.remove(1)

                pagerAdapter.add(1, FragmentPoruka.newInstance
                    ("Završili ste anketu ${anketa.naziv} u okviru istraživanja ${anketa.nazivIstrazivanja}"))
                pagerAdapter.notifyDataSetChanged();

            }
            else if (velicina >= 2) {

                pagerAdapter.zamijeni(0, FragmentAnkete.newInstance(pagerAdapter))
                pagerAdapter.notifyDataSetChanged();

                pagerAdapter.zamijeni(1, FragmentPoruka.newInstance
                    ("Završili ste anketu ${anketa.naziv} u okviru istraživanja ${anketa.nazivIstrazivanja}"))
                pagerAdapter.notifyDataSetChanged();


                for (i in velicina  downTo 2) {
                    pagerAdapter.remove(i.toInt());
                    pagerAdapter.notifyDataSetChanged();

                }

            }

        }

        return view

    }

    companion object {
        fun newInstance(anketa : Anketa, velicina:Int, adapter: ViewPagerAdapter) =
            FragmentPredaj(anketa, velicina).apply {
                this.pagerAdapter = adapter
            }
    }

    fun zaokruzi(value1: Float): Int {
        var value = value1*100
        var mult = 20
        if (value < 0) {
            mult = -20
            value = -value
        }
        return ((mult * ((value.toInt() + 10) / 20)))
    }

    fun provjera(){
        if (anketa.datumRada!=null) {
            predaj.isVisible = false
        }
        if(anketa.datumKraj.before(Date())){
            predaj.isVisible = false
        }
    }

}