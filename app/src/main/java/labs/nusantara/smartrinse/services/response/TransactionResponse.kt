package labs.nusantara.smartrinse.services.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TransactionResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("transaction")
	val transaction: Transaction
)

@Parcelize
data class Transaction(

	@field:SerializedName("transactionNumber")
	val transactionNumber: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("transactionDate")
	val transactionDate: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("laundryId")
	val laundryId: String
): Parcelable
