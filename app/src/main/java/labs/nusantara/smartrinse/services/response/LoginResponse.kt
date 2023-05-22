package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class Data(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("isLaundry")
	val isLaundry: Boolean
)
