package labs.nusantara.smartrinse.services.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class LaundryResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("laundry")
	val laundry: List<LaundryItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

@Parcelize
data class LaundryItem(

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
): Parcelable
