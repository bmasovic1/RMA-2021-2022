package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjeAnketaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)


    fun dajPitanjaAnkete(idAnkete:Int, onSuccess: (pitanja: List<Pitanje>) -> Unit, onError: () -> Unit){

        scope.launch{
            val pitanja = PitanjeAnketaRepository.getPitanja(idAnkete)
            when(pitanja){
                is List<Pitanje> -> onSuccess?.invoke(pitanja)
                else -> onError?.invoke()
            }
        }
    }

}