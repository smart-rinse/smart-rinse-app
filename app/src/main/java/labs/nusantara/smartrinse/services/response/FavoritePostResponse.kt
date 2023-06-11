package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class FavoritePostResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)
