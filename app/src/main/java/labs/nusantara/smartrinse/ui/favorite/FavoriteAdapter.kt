package labs.nusantara.smartrinse.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import labs.nusantara.smartrinse.databinding.ItemFavoriteBinding
import labs.nusantara.smartrinse.services.response.FavoriteLaundryItem
import labs.nusantara.smartrinse.ui.laundry.LaundryDetailActivity

class FavoriteAdapter (private val listDataFavorite: List<FavoriteLaundryItem>) :
    RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(data: FavoriteLaundryItem) {
            userBinding.apply {
                tvFavoriteName.text = data.namaLaundry
                tvFavoriteAddress.text = data.alamat
                tvFavoriteOpen.text = data.jamBuka
                tvFavoriteClose.text = data.jamTutup
                if (data.photo.isNotEmpty()){
                    Glide.with(itemView.context)
                        .load(data.photo)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(ivFavorite)
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, LaundryDetailActivity::class.java)
                    intent.putExtra(LaundryDetailActivity.EXTRA_LAUNDRYDETAIL, data.id)
                    intent.putExtra(LaundryDetailActivity.EXTRA_LAUNDRYNAME, data.namaLaundry)
                    itemView.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val favBinding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(favBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDataFavorite[position])
    }

    override fun getItemCount(): Int = listDataFavorite.size

}
