package labs.nusantara.smartrinse.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import labs.nusantara.smartrinse.databinding.ItemLaundryVerticalBinding
import labs.nusantara.smartrinse.services.response.LaundryItem

class HomeAdapter (private val listDataLaundry: List<LaundryItem>) :
    RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemLaundryVerticalBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: LaundryItem) {
            userBinding.apply {
                tvLaundryName.text = data.namaLaundry
                tvLaundryCity.text = "Kota : ${data.kota}"
                tvLaundryTime.text = "Hours : ${data.jamOperasional}"
                if (data.photo.isNotEmpty()){
                    Glide.with(itemView.context)
                        .load(data.photo)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(ivLaundry)
                }

//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, ArticleDetailActivity::class.java)
//                    intent.putExtra(ArticleDetailActivity.EXTRA_DETAIL, data.url)
//                    intent.putExtra(ArticleDetailActivity.EXTRA_TITLE, data.title)
//
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(tvLaundryName, "name"),
//                            Pair(tvLaundryCity, "city")
//                        )
//                    itemView.context.startActivity(intent, optionsCompat.toBundle())
//                }
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
