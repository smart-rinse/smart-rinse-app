package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class SearchLaundryResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("laundry")
	val laundry: List<SearchLaundryItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("keyword")
	val keyword: String
)

data class SearchLaundryItem(

	@field:SerializedName("jam_buka")
	val jamBuka: String,

	@field:SerializedName("jam_tutup")
	val jamTutup: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("nama_laundry")
	val namaLaundry: String,

	@field:SerializedName("alamat")
	val alamat: String
)
