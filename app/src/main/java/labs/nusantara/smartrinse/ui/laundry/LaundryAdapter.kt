package labs.nusantara.smartrinse.ui.laundry

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.databinding.ActivityLaundryBinding
import labs.nusantara.smartrinse.databinding.ItemLaundryServicesBinding
import labs.nusantara.smartrinse.model.CartItem
import labs.nusantara.smartrinse.services.response.ServicesItem

class LaundryAdapter(
    private val listDataService: List<ServicesItem>,
    private val binding: ActivityLaundryBinding
) : RecyclerView.Adapter<LaundryAdapter.ListViewHolder>() {

    private var subtotal: Int = 0
    private var selectedItems: MutableList<CartItem> = mutableListOf()

    inner class ListViewHolder(private val userBinding: ItemLaundryServicesBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        @RequiresApi(Build.VERSION_CODES.N)
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

                    // Update the selected item in the list
                    val cartItem = selectedItems.find { it.serviceId == data.id.toString() }
                    if (cartItem != null) {
                        cartItem.quantity = data.itemCount
                    } else {
                        val newCartItem = CartItem(data.id.toString(), data.itemCount)
                        selectedItems.add(newCartItem)
                    }

                    Log.d("Item Add : ", cartItem.toString())
                }

                imgDecrement.setOnClickListener {
                    if (data.itemCount > 0) {
                        data.itemCount -= 1
                        txtValue.text = data.itemCount.toString()
                        updateSubtotal()

                        val cartItem = selectedItems.find { it.serviceId == data.id.toString() }
                        if (cartItem != null) {
                            cartItem.quantity = data.itemCount
                            if (cartItem.quantity == 0) {
                                selectedItems.remove(cartItem)
                            }
                        }

                        Log.d("Item Min : ", cartItem.toString())
                    }
                }

                updateSubtotal()
            }
        }

        private fun updateSubtotal() {
            subtotal = listDataService.sumOf { it.price * it.itemCount }
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDataService[position])
    }

    override fun getItemCount(): Int = listDataService.size

    fun getSelectedItems(): MutableList<CartItem> {
        return selectedItems
    }
}