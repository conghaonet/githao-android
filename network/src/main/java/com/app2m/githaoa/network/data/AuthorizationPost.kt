package com.app2m.githaoa.network.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

private const val CLIENT_ID  = "c868cf1dc9c48103bb55"
private const val CLIENT_SECRET  = "b341fe1eb154f1d78b4f8f58288106d95bce3bf0"
private const val APPLICATION_NAME = "HaoGitHub"
private const val CALLBACK_URL = "https://github.com/conghaonet/HaoGitHub/callback"
private val SCOPES: List<String> = listOf("user", "repo", "gist", "notifications", "write:discussion", "user:follow")

@Parcelize
data class AuthorizationPost(
    @SerializedName("client_id")
    var clientId: String = CLIENT_ID,
    @SerializedName("client_secret")
    var clientSecret: String = CLIENT_SECRET,
    var appName: String = APPLICATION_NAME,
    var callback: String = CALLBACK_URL,
    var scopes: List<String> = SCOPES
): Parcelable