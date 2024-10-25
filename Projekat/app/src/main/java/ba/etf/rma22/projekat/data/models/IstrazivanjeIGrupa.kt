package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

class IstrazivanjeIGrupa (
    @SerializedName("naziv") val nazivGrupe: String,
    @SerializedName("IstrazivanjeId") val IstrazivanjeId: Int
)