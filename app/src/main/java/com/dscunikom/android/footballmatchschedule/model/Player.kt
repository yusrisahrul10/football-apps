package com.dscunikom.android.footballmatchschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    @SerializedName("strCutout") var strCutout: String?,
    @SerializedName("strPlayer") var strPlayer: String?,
    @SerializedName("strPosition") var strPosition: String?,
    @SerializedName("strWeight") var strWeight: String?,
    @SerializedName("strHeight") var strHeight: String?,
    @SerializedName("strDescriptionEN") var strDescriptionEN: String?,
    @SerializedName("strFanart1") var strFanart1: String?
) : Parcelable