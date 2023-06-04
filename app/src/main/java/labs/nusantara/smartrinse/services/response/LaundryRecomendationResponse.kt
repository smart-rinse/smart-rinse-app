package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class LaundryRecomendationResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("laundry")
	val laundry: List<LaundryRecomendationItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class LaundryRecomendationItem(

	@field:SerializedName("jam_operasional")
	val jamOperasional: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("nama_laundry")
	val namaLaundry: String,

	@field:SerializedName("alamat")
	val alamat: String
)
