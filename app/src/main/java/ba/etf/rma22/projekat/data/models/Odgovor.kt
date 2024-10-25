package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

class Odgovor(
    @SerializedName("id") val id: Int,
    @SerializedName("odgovoreno") val odgovoreno: Int,
    @SerializedName("PitanjeId") val pitanjeId: Int
)