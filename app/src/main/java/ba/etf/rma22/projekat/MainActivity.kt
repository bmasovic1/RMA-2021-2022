package ba.etf.rma22.projekat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import ba.etf.rma22.projekat.view.ListaAnketaAdapter
import ba.etf.rma22.projekat.viewmodel.AnketaViewModel
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {


    private lateinit var ankete: RecyclerView;
    private lateinit var anketaAdapter: ListaAnketaAdapter;
    private lateinit var filter: Spinner;
    private lateinit var upisIstrazivanja: FloatingActionButton;

    //poc god
    private var godina: String = "1";

    private var anketaViewModel = AnketaViewModel()
    private var istrazivanjeViewModel = IstrazivanjeViewModel()
    private var grupaViewModel = GrupaViewModel();

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filter = findViewById(R.id.filterAnketa)
        upisIstrazivanja = findViewById(R.id.upisDugme)
        ankete = findViewById(R.id.listaAnketa)
        ankete.layoutManager = GridLayoutManager(this,2);

        anketaAdapter = ListaAnketaAdapter(anketaViewModel.dajAnketeZaKorisnika())
        ankete.adapter = anketaAdapter

        upisIstrazivanja.setOnClickListener {
            upisIstrazivanje();
        }

        filter.adapter = ArrayAdapter<String>(

            this,
            android.R.layout.simple_list_item_1,
            listOf("Sve moje ankete", "Sve ankete", "Urađene ankete", "Buduće ankete", "Prošle ankete"));

        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (position) {

                    0 -> anketaAdapter.ankete = (anketaViewModel.dajAnketeZaKorisnika())
                    1 -> anketaAdapter.ankete = (anketaViewModel.dajSveAnkete())
                    2 -> anketaAdapter.ankete = (anketaViewModel.dajUrađeneAnkete())
                    3 -> anketaAdapter.ankete = (anketaViewModel.dajBuduceAnkete())
                    4 -> anketaAdapter.ankete = (anketaViewModel.dajProsleAnkete())

                }

                ankete.adapter?.notifyDataSetChanged()

            }

            //i ako nista ne radi mora stajati jer inače izbacuje errore
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }

    private fun upisIstrazivanje() {

        val intent = Intent(this, UpisIstrazivanje::class.java)
        intent.putExtra("godina", godina)
        startActivity(intent)

    }

    override fun onResume() {

        super.onResume()

        if(intent.getBundleExtra("upisIstrazivanja")!=null){

            val bundle = intent.getBundleExtra("upisIstrazivanja")

            godina = bundle?.getString("godina").toString()

            val istrazivanje = bundle?.getString("istrazivanje")
            val grupa = bundle?.getString("grupa")

            istrazivanjeViewModel.upisiIstrazivanje(istrazivanje!!,godina.toInt())

            grupaViewModel.upisiUGrupu(grupa!!,istrazivanje)

            ankete.adapter?.notifyDataSetChanged()

            ankete.adapter?.notifyDataSetChanged()

            intent.removeExtra("upisIstrazivanja")

        }

    }

}

