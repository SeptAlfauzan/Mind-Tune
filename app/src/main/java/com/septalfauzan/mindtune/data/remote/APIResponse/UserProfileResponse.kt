package com.septalfauzan.mindtune.data.remote.APIResponse

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("product")
	val product: String? = null,

	@field:SerializedName("followers")
	val followers: Followers? = null,

	@field:SerializedName("explicit_content")
	val explicitContent: ExplicitContent? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null,

	@field:SerializedName("uri")
	val uri: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Followers(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("href")
	val href: Any? = null
)

data class ExplicitContent(

	@field:SerializedName("filter_locked")
	val filterLocked: Boolean? = null,

	@field:SerializedName("filter_enabled")
	val filterEnabled: Boolean? = null
)
