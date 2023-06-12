package labs.nusantara.smartrinse.ui.history

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ItemHistoryBinding
import labs.nusantara.smartrinse.services.response.UserTransactionItem
import labs.nusantara.smartrinse.ui.invoice.InvoiceActivity
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter (private val listHistoryData: List<UserTransactionItem>) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    private val sortedListHistoryData = listHistoryData.sortedByDescending { it.dateTransaction }

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
                if(data.status == "In Progress"){
                    val backgroundDrawable = ContextCompat.getDrawable(itemView.context, R.drawable.background_rounded_yellow)
                    tvHistoryStatus.background = backgroundDrawable
                }else {
                    val backgroundDrawable = ContextCompat.getDrawable(itemView.context, R.drawable.background_rounded_green)
                    tvHistoryStatus.background = backgroundDrawable
                }

                val textColor = ContextCompat.getColor(itemView.context, R.color.white)
                tvHistoryStatus.setTextColor(textColor)
                tvHistoryStatus.text = data.status
                tvHistoryTotal.text = "Cost : Rp.${data.totalCost}"

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
        holder.bind(sortedListHistoryData[position])
    }

    override fun getItemCount(): Int = sortedListHistoryData.size

}