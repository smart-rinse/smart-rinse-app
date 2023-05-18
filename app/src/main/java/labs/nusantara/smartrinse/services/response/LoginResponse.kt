package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("email")
	val email: String
)
