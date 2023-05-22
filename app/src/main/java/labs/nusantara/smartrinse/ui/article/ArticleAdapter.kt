package labs.nusantara.smartrinse.ui.article

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.databinding.ItemArticleBinding
import labs.nusantara.smartrinse.services.response.ArticleItem
import labs.nusantara.smartrinse.ui.article.detail.ArticleDetailActivity
import labs.nusantara.smartrinse.ui.article.detail.ArticleDetailActivity.Companion.EXTRA_DETAIL
import labs.nusantara.smartrinse.ui.article.detail.ArticleDetailActivity.Companion.EXTRA_TITLE

class ArticleAdapter (private val listDataStory: List<ArticleItem>) :
    RecyclerView.Adapter<ArticleAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: ItemArticleBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(data: ArticleItem) {
            userBinding.apply {
                tvTitleArticle.text = data.title
                tvAuthArticle.text = data.author
                tvDateArticle.text = data.date
                tvDescArticle.text = data.sinopsis

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ArticleDetailActivity::class.java)
                    intent.putExtra(EXTRA_DETAIL, data.url)
                    intent.putExtra(EXTRA_TITLE, data.title)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(tvTitleArticle, "title"),
                            Pair(tvDescArticle, "sinopsis")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val userBinding =
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDataStory[position])
    }

    override fun getItemCount(): Int = listDataStory.size

}
