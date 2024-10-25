package ba.etf.rma22.projekat.data.repositories

class GodinaSRepository {

    companion object {
        private var godina: Int;

        init {
            godina=1
        }

        fun setGod(g: Int){
            godina=g
        }

        fun getGod(): Int{
            return godina
        }

    }
}