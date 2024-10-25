package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import kotlinx.coroutines.*

class AnketaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getAll(onSuccess: (ankete: List<Anketa>) -> Unit, onError: () -> Unit){

        scope.launch{
            val result = AnketaRepository.getAllPrikaz()
            when (result) {
                is List<Anketa> -> onSuccess?.invoke(result!!)
                else-> onError?.invoke()
            }
        }
    }

    fun getMyAnkete( onSuccess: (ankete: List<Anketa>) -> Unit, onError: () -> Unit){

        scope.launch{

            val result = AnketaRepository.getUpisanePrikaz()
            when (result) {
                is List<Anketa> -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

}