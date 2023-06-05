package labs.nusantara.smartrinse.ui.laundry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.databinding.ItemLaundryServicesBinding
import labs.nusantara.smartrinse.services.response.ServicesItem

class LaundryAdapter (private val listDataService: List<ServicesItem>) :
    RecyclerView.Adapter<LaundryAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemLaundryServicesBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(data: ServicesItem) {
            userBinding.apply {
                tvServiceName.text = data.jenisService
                tvServicePrice.text = data.price.toString()
            }

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