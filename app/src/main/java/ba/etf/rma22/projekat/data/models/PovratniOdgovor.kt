package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

class PovratniOdgovor(
    @SerializedName("odgovoreno") var odgovoreno: Int?,
    @SerializedName("AnketaTakenId") var AnketaTakenId: Int?,
    @SerializedName("PitanjeId") var PitanjeId: Int?
)