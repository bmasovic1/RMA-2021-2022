package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Grupa(
    @PrimaryKey @SerializedName("id") var id: Int,
    @ColumnInfo (name = "naziv") @SerializedName("naziv") var naziv: String,
    @ColumnInfo (name = "IstrazivanjeId") @SerializedName("IstrazivanjeId") var IstrazivanjeId: Int,
    @ColumnInfo (name = "upisan") @SerializedName("upisan") var upisan: Int,
)