package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TakeAnketaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun zapociAnketu(context : Context, idAnkete:Int, onSuccess: (pitanja: AnketaTaken) -> Unit, onError: () -> Unit){

        scope.launch{
            TakeAnketaRepository.setContext(context)
            val pitanja = TakeAnketaRepository.zapocniAnketu(idAnkete)
            when(pitanja){
                is AnketaTaken -> onSuccess?.invoke(pitanja)
                else -> onError?.invoke()
            }
        }
    }

    fun zapoceteAnkete(context : Context,onSuccess: (ankete: List<AnketaTaken>) -> Unit, onError: () -> Unit){

        scope.launch{
            TakeAnketaRepository.setContext(context)
            val result = TakeAnketaRepository.getPoceteAnkete()
            when (result) {
                is List<AnketaTaken> -> onSuccess?.invoke(result!!)
                else-> onError?.invoke()
            }
        }
    }

    fun tetInternet(context : Context,onSuccess: (test: Boolean) -> Unit, onError: () -> Unit){

        scope.launch{
            TakeAnketaRepository.setContext(context)
            val result = TakeAnketaRepository.tetstInternet()
            when (result) {
                is Boolean -> onSuccess?.invoke(result!!)
                else-> onError?.invoke()
            }
        }
    }

}