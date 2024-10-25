package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import kotlinx.coroutines.*

class AccountViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun postaviHash(context : Context, acHash : String, onSuccess: (uspjesno : Boolean) -> Unit, onError: () -> Unit){

        scope.launch{
            AccountRepository.setContext(context)
            val result = AccountRepository.postaviHash(acHash)
            when (result) {
                is Boolean -> onSuccess.invoke(result)
                else-> onError.invoke()
            }
        }
    }
}