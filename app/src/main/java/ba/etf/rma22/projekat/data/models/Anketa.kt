package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Anketa(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
    @SerializedName("datumPocetak") val datumPocetak: Date,
    @SerializedName("datumKraj") val datumKraj: Date?,
    @SerializedName("trajanje") val trajanje: Int,
    var nazivGrupe: String?,
    var nazivIstrazivanja: String?,
    var datumRada: Date?,
    var progres: Float?
)