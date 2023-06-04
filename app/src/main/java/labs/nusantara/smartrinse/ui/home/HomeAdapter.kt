package labs.nusantara.smartrinse.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import labs.nusantara.smartrinse.databinding.ItemLaundryVerticalBinding
import labs.nusantara.smartrinse.services.response.LaundryItem
import labs.nusantara.smartrinse.ui.laundry.LaundryDetailActivity
import labs.nusantara.smartrinse.ui.laundry.LaundryDetailActivity.Companion.EXTRA_LAUNDRYDETAIL
import labs.nusantara.smartrinse.ui.laundry.LaundryDetailActivity.Companion.EXTRA_LAUNDRYNAME

class HomeAdapter (private val listDataLaundry: List<LaundryItem>) :
    RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemLaundryVerticalBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: LaundryItem) {
            userBinding.apply {
                tvLaundryName.text = data.namaLaundry
                tvLaundryCity.text = "Kota : ${data.alamat}"
                tvLaundryTime.text = "Hours : ${data.jamBuka} - ${data.jamTutup}"
                if (data.photo.isNotEmpty()){
                    Glide.with(itemView.context)
                        .load(data.photo)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(ivLaundry)
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, LaundryDetailActivity::class.java)
                    intent.putExtra(EXTRA_LAUNDRYDETAIL, data.id)
                    intent.putExtra(EXTRA_LAUNDRYNAME, data.namaLaundry)
                    itemView.context.startActivity(intent)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val userBinding =
            ItemLaundryVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDataLaundry[position])
    }

    override fun getItemCount(): Int = listDataLaundry.size

}
