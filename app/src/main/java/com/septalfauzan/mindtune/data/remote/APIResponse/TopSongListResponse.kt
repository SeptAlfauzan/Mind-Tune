package com.septalfauzan.mindtune.data.remote.APIResponse

import com.google.gson.annotations.SerializedName

data class TopSongListResponse(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("items")
	val items: List<SongItem?>? = null
)

data class ImagesItem(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class ExternalUrls(

	@field:SerializedName("spotify")
	val spotify: String? = null
)

data class ExternalIds(

	@field:SerializedName("isrc")
	val isrc: String? = null
)

data class Album(

    @field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

    @field:SerializedName("available_markets")
	val availableMarkets: List<String?>? = null,

    @field:SerializedName("release_date_precision")
	val releaseDatePrecision: String? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("uri")
	val uri: String? = null,

    @field:SerializedName("total_tracks")
	val totalTracks: Int? = null,

    @field:SerializedName("artists")
	val artists: List<ArtistsItem?>? = null,

    @field:SerializedName("release_date")
	val releaseDate: String? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("album_type")
	val albumType: String? = null,

    @field:SerializedName("href")
	val href: String? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null
)

data class SongItem(

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

data class ArtistsItem(

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("href")
	val href: String? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null,

    @field:SerializedName("uri")
	val uri: String? = null
)
