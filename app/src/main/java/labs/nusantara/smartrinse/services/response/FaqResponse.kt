package labs.nusantara.smartrinse.services.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FaqResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("faq")
	val faq: List<FaqItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

@Parcelize
data class FaqItem(

	@field:SerializedName("question")
	val question: String,

	@field:SerializedName("answer")
	val answer: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("isExpanded")
	var isExpanded: Boolean = false
): Parcelable
