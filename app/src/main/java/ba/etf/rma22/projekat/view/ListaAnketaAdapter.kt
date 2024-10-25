package ba.etf.rma22.projekat.view

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*


class ListaAnketaAdapter(var ankete: List<Anketa>, private val onItemClicked: (anketa:Anketa) -> Unit): RecyclerView.Adapter<ListaAnketaAdapter.AnketaViewHolder>() {

    inner class AnketaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val stanjeAnkete:ImageView = itemView.findViewById(R.id.stanjeAnkete);
        val nazivAnkete: TextView = itemView.findViewById(R.id.nazivAnkete);

        val nazivIstrazivanja: TextView = itemView.findViewById(R.id.nazivIstrazivanja);
        val progresZavrsetka: ProgressBar = itemView.findViewById(R.id.progresZavrsetka);

        val datum: TextView = itemView.findViewById(R.id.datumAnkete);

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnketaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_anketa, parent, false)
        return AnketaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ankete.size;
    }

    fun updateAnkete(anket: List<Anketa>) {
        this.ankete = anket
        notifyDataSetChanged()
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

    fun ispisDatuma(trazeniDatum:Date):String {

        var cal: Calendar = Calendar.getInstance()
        cal.time = trazeniDatum
        return String.format("%02d.%02d.%d.", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))

    }

    override fun onBindViewHolder(holder: AnketaViewHolder, position: Int) {

        holder.nazivAnkete.text = ankete[position].naziv;
        holder.nazivIstrazivanja.text = ankete[position].nazivIstrazivanja;

        holder.itemView.setOnClickListener {
            onItemClicked(ankete[position])
        }

        if( ankete[position].datumKraj!=null && ankete[position].datumKraj!!.before(Date())){

            holder.stanjeAnkete.setImageResource(R.drawable.crvena);
            holder.progresZavrsetka.setProgress(0);

            //holder.datum.text = "Anketa zatvorena: "+(ankete[position].datumKraj)+"."+ (ankete[position].datumKraj?.month!!)+"."+(ankete[position].datumKraj?.year!!);

            holder.datum.text = "Anketa zatvorena: " + ispisDatuma(ankete[position].datumKraj!!)

        }else if(ankete[position].datumRada!=null && ankete[position].progres==1f){

            holder.stanjeAnkete.setImageResource(R.drawable.plava);

            if(ankete[position].progres!=null)
                holder.progresZavrsetka.setProgress(zaokruzi(ankete[position].progres!!))

            //ankete[position].progres?.let { holder.progresZavrsetka.setProgress(it.toInt()) };
            //holder.datum.text = "Anketa urađena: " + (ankete[position].datumRada?.date!!)+"."+ (ankete[position].datumRada?.month!!)+"."+(ankete[position].datumRada?.year!!);

            holder.datum.text = "Anketa urađena: " + ispisDatuma(ankete[position].datumRada!!)

        }else if(ankete[position].datumPocetak.after(Date())){

            holder.stanjeAnkete.setImageResource(R.drawable.zuta);
            holder.progresZavrsetka.setProgress(0);

            //holder.datum.text = "Vrijeme aktiviranja: " + (ankete[position].datumPocetka.date)+"."+ (ankete[position].datumPocetka?.month!!)+"."+(ankete[position].datumPocetka?.year!!);

            holder.datum.text = "Vrijeme aktiviranja: " + ispisDatuma(ankete[position].datumPocetak)

        }else{

            holder.stanjeAnkete.setImageResource(R.drawable.zelena);
            holder.progresZavrsetka.setProgress(0);
            if(ankete[position].progres!=null)
                holder.progresZavrsetka.setProgress(zaokruzi(ankete[position].progres!!))

            //holder.datum.text = "Vrijeme zatvaranja: " + (ankete[position].datumKraj)+"."+ (ankete[position].datumKraj?.month!!)+"."+(ankete[position].datumKraj?.year!!);

            if(ankete[position].datumKraj!=null)
                holder.datum.text = "Vrijeme zatvaranja: " +  ispisDatuma(ankete[position].datumKraj!!)
            else
                holder.datum.text = "Vrijeme zatvaranja: null"

        }

    }
}