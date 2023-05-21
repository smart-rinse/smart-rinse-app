package labs.nusantara.smartrinse.services.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArticleResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("article")
	val article: List<ArticleItem>,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

@Parcelize
data class ArticleItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("sinopsis")
	val sinopsis: String
): Parcelable
