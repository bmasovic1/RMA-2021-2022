package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OdgovorViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getOdgovori(anketaId: Int,onSuccess: (List<Odgovor>) -> Unit, onError: () -> Unit) {
        scope.launch {

            val result = OdgovorRepository.getOdgovoriAnketa(anketaId)
            when (result) {
                is List<Odgovor> ->  onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }

    }

    fun setOdgovor(idAnketaTaken:Int,idPitanje:Int,odgovor:Int,onSuccess: (Int) -> Unit, onError: () -> Unit) {
        scope.launch {

            val result = OdgovorRepository.postaviOdgovorAnketa(idAnketaTaken,idPitanje,odgovor)
            when (result) {
                is Int -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }

    }

}