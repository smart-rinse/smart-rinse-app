package labs.nusantara.smartrinse.services.response

import com.google.gson.annotations.SerializedName

data class LaundryDetailResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("laundry")
	val laundry: Laundry,

	@field:SerializedName("services")
	val services: List<ServicesItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class Laundry(

	@field:SerializedName("jam_tutup")
	val jamTutup: String,

	@field:SerializedName("count_reviews")
	val countReviews: Int,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("average_rating")
	val averageRating: Int,

	@field:SerializedName("tanggal_berdiri")
	val tanggalBerdiri: String,

	@field:SerializedName("nama_laundry")
	val namaLaundry: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("jam_buka")
	val jamBuka: String,

	@field:SerializedName("reviews")
	val reviews: List<ReviewsItem>,

	@field:SerializedName("rekening")
	val rekening: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("longitude")
	val longitude: String
)

data class ServicesItem(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("jenis_service")
	val jenisService: String
)

data class ReviewsItem(

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("content")
	val content: String
)
