package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OdgovorViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getOdgovori(context: Context, anketaId: Int,onSuccess: (List<Odgovor>) -> Unit, onError: () -> Unit) {
        scope.launch {
            OdgovorRepository.setContext(context)
            val result = OdgovorRepository.getOdgovoriAnketa(anketaId)
            when (result) {
                is List<Odgovor> ->  onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }

    }

    fun setOdgovor(context: Context, idAnketaTaken:Int,idPitanje:Int,odgovor:Int,onSuccess: (Int) -> Unit, onError: () -> Unit) {
        scope.launch {
            OdgovorRepository.setContext(context)
            val result = OdgovorRepository.postaviOdgovorAnketa(idAnketaTaken,idPitanje,odgovor)
            when (result) {
                is Int -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }

    }

}