package com.app2m.githaoa.network.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@SuppressLint("ParcelCreator")
data class UserData(
    @SerializedName("gists_url")
    var gistsUrl: String,
    @SerializedName("repos_url")
    var reposUrl: String,
    @SerializedName("following_url")
    var followingUrl: String,
    var bio: @RawValue Any,
    @SerializedName("created_at")
    var createdAt: String,
    var login: String,
    var type: String,
    var blog: String,
    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("site_admin")
    var siteAdmin: Boolean,
    var company: String,
    var id: Int,
    @SerializedName("public_repos")
    var publicRepos: Int,
    @SerializedName("gravatar_id")
    var gravatarId: String,
    var email: String,
    @SerializedName("organizations_url")
    var organizationsUrl: String,
    var hireable: @RawValue Any,
    @SerializedName("starred_url")
    var starredUrl: String,
    @SerializedName("followers_url")
    var followersUrl: String,
    var publicGists: Int,
    var url: String,
    @SerializedName("received_events_url")
    var receivedEventsUrl: String,
    var followers: Int,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("events_url")
    var eventsUrl: String,
    @SerializedName("html_url")
    var htmlUrl: String,
    var following: Int,
    var name: String,
    var location: @RawValue Any,
    @SerializedName("node_id")
    var nodeId: String,
/// 已验证用户返回该字段
    @SerializedName("private_gists")
    var privateGists: Int,
/// 已验证用户返回该字段
    @SerializedName("total_private_repos")
    var totalPrivateRepos: Int,
/// 已验证用户返回该字段
    @SerializedName("owned_private_repos")
    var ownedPrivateRepos: Int,
/// 已验证用户返回该字段
    @SerializedName("disk_usage")
    var diskUsage: Int,
/// 已验证用户返回该字段
    var collaborators: Int,
/// 已验证用户返回该字段
    @SerializedName("two_factor_authentication")
    var twoFactorAuthentication: Boolean,
/// 已验证用户返回该字段
    var plan: UserPlanData
) : Parcelable {
    @Parcelize
    @SuppressLint("ParcelCreator")
    data class UserPlanData(
        var privateRepos: Int,
        var name: String,
        var collaborators: Int,
        var space: Int
    ) : Parcelable
}