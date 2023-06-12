package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class ReviewPostResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("review")
	val review: Review,

	@field:SerializedName("message")
	val message: String
)

data class Review(

	@field:SerializedName("sentiment")
	val sentiment: Any,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("userName")
	val userName: String,

	@field:SerializedName("laundryId")
	val laundryId: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("content")
	val content: String
)
