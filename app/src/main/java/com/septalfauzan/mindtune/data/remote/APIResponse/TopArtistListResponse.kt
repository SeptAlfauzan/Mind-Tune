package com.septalfauzan.mindtune.data.remote.APIResponse

import com.google.gson.annotations.SerializedName

data class TopArtistListResponse(

	@field:SerializedName("next")
	val next: Any? = null,

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
	val items: List<Artist?>? = null
)

data class Artist(

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("followers")
	val followers: Followers? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("popularity")
	val popularity: Int? = null,

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
//
//data class Followers(
//
//	@field:SerializedName("total")
//	val total: Int? = null,
//
//	@field:SerializedName("href")
//	val href: Any? = null
//)
//
//data class ImagesItem(
//
//	@field:SerializedName("width")
//	val width: Int? = null,
//
//	@field:SerializedName("url")
//	val url: String? = null,
//
//	@field:SerializedName("height")
//	val height: Int? = null
//)
