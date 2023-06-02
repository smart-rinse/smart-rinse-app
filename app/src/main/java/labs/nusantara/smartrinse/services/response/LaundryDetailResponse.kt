package labs.nusantara.smartrinse.services.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class LaundryDetailResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("laundry")
	val laundry: Laundry,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

@Parcelize
data class Laundry(

	@field:SerializedName("reviews")
	val reviews: List<ReviewsItem>,

	@field:SerializedName("jam_operasional")
	val jamOperasional: String,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("average_rating")
	val averageRating: Float,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("tanggal_berdiri")
	val tanggalBerdiri: String,

	@field:SerializedName("nama_laundry")
	val namaLaundry: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("longitude")
	val longitude: String
): Parcelable

@Parcelize
data class ReviewsItem(

	@field:SerializedName("rating")
	val rating: Float,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("content")
	val content: String
): Parcelable
