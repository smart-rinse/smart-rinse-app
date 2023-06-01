package labs.nusantara.smartrinse.ui.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ItemFaqBinding
import labs.nusantara.smartrinse.services.response.FaqItem

class FaqAdapter (private val listDataFaq: List<FaqItem>) :
    RecyclerView.Adapter<FaqAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemFaqBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(data: FaqItem) {
            userBinding.apply {
                textItem.text = data.question

                // Set the visibility of the answer
                textAnswer.visibility = if (data.isExpanded) View.VISIBLE else View.GONE
                textAnswer.text = data.answer

                val imageResId = if (data.isExpanded) R.drawable.ic_minimize else R.drawable.ic_add
                imgArrowitem.setImageResource(imageResId)

                itemView.setOnClickListener {
                    data.isExpanded = !data.isExpanded
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val userBinding =
            ItemFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDataFaq[position])
    }

    override fun getItemCount(): Int = listDataFaq.size

}
