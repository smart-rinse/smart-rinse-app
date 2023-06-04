package labs.nusantara.smartrinse.services.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class Laundry(

	@field:SerializedName("jam_tutup")
	val jamTutup: String,

	@field:SerializedName("count_reviews")
	val countReviews: String,

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
): Parcelable

@Parcelize
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
): Parcelable

@Parcelize
data class ServicesItem(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("jenis_service")
	val jenisService: String
): Parcelable
