package labs.nusantara.smartrinse.ui.invoice

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.databinding.ItemInvoiceBinding
import labs.nusantara.smartrinse.services.response.UserTransactionDetailServicesItem

class InvoiceAdapter (private val listInvoiceService: List<UserTransactionDetailServicesItem>) :
    RecyclerView.Adapter<InvoiceAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemInvoiceBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: UserTransactionDetailServicesItem) {
            userBinding.apply {
                tvItemName.text = data.serviceName
                tvItemPrice.text = "Total item (${data.quantity} kg) x ${data.price}"
                val totalPrice = (data.price * data.quantity)
                tvItemPriceTotal.text = "Rp. $totalPrice"
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val invoiceBinding =
            ItemInvoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(invoiceBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listInvoiceService[position])
    }

    override fun getItemCount(): Int = listInvoiceService.size

}