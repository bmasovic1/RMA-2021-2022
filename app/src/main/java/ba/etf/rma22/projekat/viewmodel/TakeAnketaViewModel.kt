package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TakeAnketaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun zapociAnketu(idAnkete:Int, onSuccess: (pitanja: AnketaTaken) -> Unit, onError: () -> Unit){

        scope.launch{
            val pitanja = TakeAnketaRepository.zapocniAnketu(idAnkete)
            when(pitanja){
                is AnketaTaken -> onSuccess?.invoke(pitanja)
                else -> onError?.invoke()
            }
        }
    }

    fun zapoceteAnkete(onSuccess: (ankete: List<AnketaTaken>) -> Unit, onError: () -> Unit){

        scope.launch{
            val result = TakeAnketaRepository.getPoceteAnkete()
            when (result) {
                is List<AnketaTaken> -> onSuccess?.invoke(result!!)
                else-> onError?.invoke()
            }
        }
    }

}