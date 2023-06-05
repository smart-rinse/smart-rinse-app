package labs.nusantara.smartrinse.ui.laundry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.databinding.ItemLaundryReviewsBinding
import labs.nusantara.smartrinse.services.response.ReviewsItem

class LaundryReviewsAdapter(private val listReviews: List<ReviewsItem>) :
    RecyclerView.Adapter<LaundryReviewsAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemLaundryReviewsBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(data: ReviewsItem) {
            userBinding.apply {
                tvRevName.text = data.name
                tvRevContent.text = data.content
                tvRevRating.text = data.rating.toString()

                val ratingBar = ratingReview
                val rating = data.rating
                ratingBar.rating = rating.toFloat()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val reviewsBinding =
            ItemLaundryReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(reviewsBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listReviews[position])
    }

    override fun getItemCount(): Int = listReviews.size

}