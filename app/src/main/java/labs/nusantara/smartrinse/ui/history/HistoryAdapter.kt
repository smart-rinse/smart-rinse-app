package labs.nusantara.smartrinse.ui.history

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.databinding.ItemHistoryBinding
import labs.nusantara.smartrinse.services.response.UserTransactionItem
import labs.nusantara.smartrinse.ui.invoice.InvoiceActivity
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter (private val listHistoryData: List<UserTransactionItem>) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: UserTransactionItem) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("dd MMMM yyyy")


            userBinding.apply {
                tvHistoryId.text = "Transaction : ${data.idTransaction}"
                try {
                    val parsedDate: Date = inputFormat.parse(data.dateTransaction) as Date
                    val outputDate: String = outputFormat.format(parsedDate)
                    tvHistoryDate.text = outputDate
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                tvHistoryTotal.text = "Cost : Rp.${data.totalCost}"
//                if (data.thumbnail.isNotEmpty()){
//                    Glide.with(itemView.context)
//                        .load(data.thumbnail)
//                        .transition(DrawableTransitionOptions.withCrossFade(500))
//                        .into(ivArticle)
//                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, InvoiceActivity::class.java)
                    intent.putExtra(InvoiceActivity.EXTRA_ID, data.idTransaction)
                    itemView.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val userBinding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHistoryData[position])
    }

    override fun getItemCount(): Int = listHistoryData.size

}