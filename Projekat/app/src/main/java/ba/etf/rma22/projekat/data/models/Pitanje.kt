package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
class Pitanje(
    @PrimaryKey(autoGenerate = true) val ids: Long,
    @ColumnInfo (name = "id")@SerializedName("id") var id: Int,
    @ColumnInfo (name = "naziv")@SerializedName("naziv") var naziv: String,
    @ColumnInfo (name = "tekstPitanja")@SerializedName("tekstPitanja") var tekstPitanja: String,
    @TypeConverters(Converter::class)
    @ColumnInfo (name = "opcije") @SerializedName("opcije") val opcije: List<String>?,
    @ColumnInfo (name = "AnketaId") @SerializedName("AnketaId") var AnketaId: Int,
)
