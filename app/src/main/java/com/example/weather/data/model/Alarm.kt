package com.example.weather.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "tbl_alarm")
data class Alarm(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String = "",

    @ColumnInfo(name = "clock")
    @SerializedName("clock")
    var clock: String = "",

    ): Parcelable {


}
