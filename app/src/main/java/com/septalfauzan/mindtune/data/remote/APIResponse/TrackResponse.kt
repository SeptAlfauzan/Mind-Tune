package com.septalfauzan.mindtune.data.remote.APIResponse

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @field:SerializedName("disc_number")
    val discNumber: Int? = null,

    @field:SerializedName("album")
    val album: Album? = null,

    @field:SerializedName("available_markets")
    val availableMarkets: List<String?>? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("external_ids")
    val externalIds: ExternalIds? = null,

    @field:SerializedName("uri")
    val uri: String? = null,

    @field:SerializedName("duration_ms")
    val durationMs: Int? = null,

    @field:SerializedName("explicit")
    val explicit: Boolean? = null,

    @field:SerializedName("artists")
    val artists: List<ArtistsItem?>? = null,

    @field:SerializedName("preview_url")
    val previewUrl: String? = null,

    @field:SerializedName("popularity")
    val popularity: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("track_number")
    val trackNumber: Int? = null,

    @field:SerializedName("href")
    val href: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("is_local")
    val isLocal: Boolean? = null,

    @field:SerializedName("external_urls")
    val externalUrls: ExternalUrls? = null
)