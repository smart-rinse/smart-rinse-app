package labs.nusantara.smartrinse.ui.laundry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.databinding.ActivityLaundryBinding
import labs.nusantara.smartrinse.databinding.ItemLaundryServicesBinding
import labs.nusantara.smartrinse.services.response.ServicesItem

class LaundryAdapter(
    private val listDataService: List<ServicesItem>,
    private val binding: ActivityLaundryBinding
) : RecyclerView.Adapter<LaundryAdapter.ListViewHolder>() {

    private var subtotal: Int = 0

    inner class ListViewHolder(private val userBinding: ItemLaundryServicesBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(data: ServicesItem) {
            userBinding.apply {
                // Bind data to views
                tvServiceName.text = data.jenisService
                tvServicePrice.text = "Rp ${data.price}  /kg"
                txtValue.text = data.itemCount.toString()

                imgIncrement.setOnClickListener {
                    data.itemCount += 1
                    txtValue.text = data.itemCount.toString()
                    updateSubtotal()
                }

                imgDecrement.setOnClickListener {
                    if (data.itemCount > 0) {
                        data.itemCount -= 1
                        txtValue.text = data.itemCount.toString()
                        updateSubtotal()
                    }
                }

                updateSubtotal()
            }
        }

        private fun updateSubtotal() {
            subtotal = listDataService.sumBy { it.price * it.itemCount }
            val buttonText = when {
                subtotal > 0 -> "Process (Rp. $subtotal)"
                else -> "Process"
            }

            binding.btnProcess.text = buttonText

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val serviceBinding =
            ItemLaundryServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(serviceBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDataService[position])
    }

    override fun getItemCount(): Int = listDataService.size
}