package ba.etf.rma22.projekat.data.repositories

class AccountRepository {

    companion object {
        var acHash: String = "09307588-af76-4192-94b8-1569e760967d"
    }

    fun postaviHash(accHash:String):Boolean{
        try {
            acHash = accHash
            return true
        }catch (e: Exception){
            return false
        }
    }

    fun getHash():String{
        return acHash
    }
}
