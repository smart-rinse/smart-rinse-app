package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class UserAllTransactionResponse(

	@field:SerializedName("userTransaction")
	val userTransaction: List<UserTransactionItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class UserTransactionItem(

	@field:SerializedName("dateTransaction")
	val dateTransaction: String,

	@field:SerializedName("totalCost")
	val totalCost: Int,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("idTransaction")
	val idTransaction: String
)
