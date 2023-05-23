package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class User(

	@field:SerializedName("gender")
	val gender: Any,

	@field:SerializedName("city")
	val city: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("telephone")
	val telephone: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("isLaundry")
	val isLaundry: Boolean
)
