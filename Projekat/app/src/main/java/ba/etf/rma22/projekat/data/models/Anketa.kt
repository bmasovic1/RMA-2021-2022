package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Anketa(
    @PrimaryKey @SerializedName("id") var id: Int,
    @ColumnInfo (name = "naziv") @SerializedName("naziv") var naziv: String,
    @ColumnInfo (name = "datumPocetak") @SerializedName("datumPocetak") var datumPocetak: String,
    @ColumnInfo (name = "datumKraj") @SerializedName("datumKraj") var datumKraj: String?,
    @ColumnInfo (name = "trajanje") @SerializedName("trajanje") var trajanje: Int,
    @ColumnInfo (name = "nazivGrupe") var nazivGrupe: String?,
    @ColumnInfo (name = "nazivIstrazivanja") var nazivIstrazivanja: String?,
    @ColumnInfo (name = "datumRada") var datumRada: String?,
    @ColumnInfo (name = "progres") var progres: Float?,
    @ColumnInfo (name = "upisan") var upisan: Int,
)