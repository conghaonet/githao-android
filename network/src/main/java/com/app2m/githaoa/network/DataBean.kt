package com.app2m.githaoa.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBean(val message: String, val status: Int): Parcelable