package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class UserTransactionDetailResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("transaction")
	val transaction: UserTransactionDetail
)

data class UserTransactionDetailServicesItem(

	@field:SerializedName("quantity")
	val quantity: Int,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("serviceName")
	val serviceName: String
)

data class UserTransactionDetail(

	@field:SerializedName("owner")
	val owner: String,

	@field:SerializedName("rekening")
	val rekening: String,

	@field:SerializedName("idlaundry")
	val idlaundry: String,

	@field:SerializedName("isReviewed")
	val isReviewed: Boolean,

	@field:SerializedName("transactionNumber")
	val transactionNumber: String,

	@field:SerializedName("pembeli")
	val pembeli: String,

	@field:SerializedName("services")
	val services: List<UserTransactionDetailServicesItem>,

	@field:SerializedName("transactionDate")
	val transactionDate: String,

	@field:SerializedName("nama_laundry")
	val namaLaundry: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("totalCost")
	val totalCost: Int
)
