package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class IstrazivanjeIGrupaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getNEUpisanaIstrazivanja(godina: String, onSuccess: (istrazivanja: List<Istrazivanje>) -> Unit, onError: () -> Unit) {

        scope.launch {

            val result = IstrazivanjeIGrupaRepository.getNEUpisanaIstrazivanja2(godina)
            when (result) {
                is List<Istrazivanje> ->  onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getGrupeZaIstrazivanje(istrazivanje: String, onSuccess: (grupe: List<Grupa>) -> Unit, onError: () -> Unit) {

        scope.launch {

            val result = IstrazivanjeIGrupaRepository.dajGrupeZaIstrazivanje(istrazivanje)
            when (result) {
                is List<Grupa> -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }


    fun upisiStudenta(nazivGrupe: String, onSuccess: (uspjesno: Boolean) -> Unit, onError: () -> Unit) {

        scope.launch {

            val result = IstrazivanjeIGrupaRepository.upisiStudentaUGrupu(nazivGrupe)
            when (result) {
                is Boolean -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }





}