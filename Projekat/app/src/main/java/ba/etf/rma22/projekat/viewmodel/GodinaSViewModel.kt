package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.repositories.GodinaSRepository

class GodinaSViewModel {

    fun dajGodinu() : Int{
        return  GodinaSRepository.getGod()
    }

    fun postaviGodinu(g: Int){
        GodinaSRepository.setGod(g)
    }

}