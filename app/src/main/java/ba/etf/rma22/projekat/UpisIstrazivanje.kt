package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.viewmodel.GrupaViewModel
import ba.etf.rma22.projekat.viewmodel.IstrazivanjeViewModel
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.util.Log


class UpisIstrazivanje : AppCompatActivity() {

    lateinit var godineSpinner:Spinner;
    lateinit var istrazivanjaSpinner:Spinner;
    lateinit var grupeSpinner:Spinner;

    lateinit var dodaj: Button;

    lateinit var istrazivanjeAdapter: ArrayAdapter<String>
    lateinit var grupeAdapter: ArrayAdapter<String>
    var istrazivanjeViewModel = IstrazivanjeViewModel()
    var grupaViewModel = GrupaViewModel()

    var godina:Int=1;
    var br:Int=0;
    var br2:Int=0;
    var br3:Int=0;

    private fun dajGodinu():Int {
        if(intent.getStringExtra("godina")!=null){
            return intent.getStringExtra("godina")!!.toInt()
        }else return 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_istrazivanje)

        godineSpinner = findViewById(R.id.odabirGodina);
        istrazivanjaSpinner = findViewById(R.id.odabirIstrazivanja);
        grupeSpinner = findViewById(R.id.odabirGrupa);

        dodaj = findViewById(R.id.dodajIstrazivanjeDugme)

        //onemogućen button za dodavanje
        dodaj.isVisible = false;

        godineSpinner.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, listOf("1","2","3","4","5"));

        istrazivanjeAdapter = ArrayAdapter(this@UpisIstrazivanje, android.R.layout.simple_list_item_1, mutableListOf<String>())
        grupeAdapter= ArrayAdapter(this@UpisIstrazivanje, android.R.layout.simple_list_item_1, mutableListOf<String>())

        var prazno: ArrayAdapter<String>
        prazno= ArrayAdapter(this@UpisIstrazivanje, android.R.layout.simple_list_item_1, listOf(" "))

        istrazivanjaSpinner.adapter = istrazivanjeAdapter
        grupeSpinner.adapter = grupeAdapter

        godina = dajGodinu()

        godineSpinner.setSelection(dajGodinu()-1)

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

                istrazivanjeAdapter.clear()

                istrazivanjaSpinner.adapter= ArrayAdapter(this@UpisIstrazivanje, android.R.layout.simple_list_item_1, istrazivanjeViewModel.dajNeUpisanaIstrazivanja(godina).map { p: Istrazivanje -> p.toString() })


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


                //U slučaju da se pokazuju samo grupe u koje korisnik nije upisan pošto nije specificirano u postavci spirale
                //grupeSpinner.adapter = ArrayAdapter(this@UpisIstrazivanje,android.R.layout.simple_list_item_1, grupaViewModel.dajNeUpisaneGrupe(istrazivanjaSpinner.getSelectedItem().toString()).map { g:Grupa->g.toString() })

                //U slučaju da se pokazuju sve grupe za dato istraživanje
                //grupeSpinner.adapter = ArrayAdapter(this@UpisIstrazivanje,android.R.layout.simple_list_item_1, grupaViewModel.dajGrupeZaIstrazivanja(istrazivanjeViewModel.dajNeUpisanaIstrazivanja(godina)[position]).map { g:Grupa->g.toString() })


                  grupeSpinner.adapter = ArrayAdapter(this@UpisIstrazivanje,android.R.layout.simple_list_item_1, grupaViewModel.dajGrupeZaIstrazivanja(istrazivanjeViewModel.dajNeUpisanaIstrazivanja(godina)[position]).map { g:Grupa->g.toString() })


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



        dodaj.setOnClickListener {

            val otvoriPocetnuAktivnost = Intent(this,MainActivity::class.java).apply {

                var bundle = Bundle()
                bundle.putString("godina",godineSpinner.selectedItem.toString())
                bundle.putString("istrazivanje",istrazivanjaSpinner.selectedItem.toString())
                bundle.putString("grupa",grupeSpinner.selectedItem.toString())
                putExtra("upisIstrazivanja",bundle)

            }

            startActivity(otvoriPocetnuAktivnost)

        }

    }

}