package com.app2m.githaoa.network.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class AuthorizationData(
    var app: AuthorizationApp,
    @SerializedName("hashed_token")
    var hashedToken: String,
    var note: @RawValue Any,
    @SerializedName("note_url")
    var noteUrl: String,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("token_last_eight")
    var tokenLastEight: String,
    var fingerprint: @RawValue Any,
    @SerializedName("created_at")
    var createdAt: String,
    var id: Int,
    var scopes: List<String>,
    var url: String,
    var token: String
): Parcelable {
    @Parcelize
    data class AuthorizationApp(
        var name: String,
        var url: String,
        @SerializedName("client_id")
        var clientId: String
    ): Parcelable
}