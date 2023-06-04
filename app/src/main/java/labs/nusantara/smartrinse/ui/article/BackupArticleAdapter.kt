package labs.nusantara.smartrinse.ui.article

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import labs.nusantara.smartrinse.databinding.ItemArticleBinding
import labs.nusantara.smartrinse.databinding.ItemTitleBinding
import labs.nusantara.smartrinse.services.response.ArticleItem
import labs.nusantara.smartrinse.ui.article.detail.ArticleDetailActivity
import labs.nusantara.smartrinse.ui.article.detail.ArticleDetailActivity.Companion.EXTRA_DETAIL
import labs.nusantara.smartrinse.ui.article.detail.ArticleDetailActivity.Companion.EXTRA_TITLE

class BackupArticleAdapter(private val listDataStory: List<ArticleItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_TITLE = 0
    private val VIEW_TYPE_ITEM = 1

    inner class TitleViewHolder(private val titleBinding: ItemTitleBinding) :
        RecyclerView.ViewHolder(titleBinding.root) {

        fun bind() {
            titleBinding.tvTitle.text = "Your Title Here"
        }
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TITLE -> {
                val titleBinding =
                    ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(titleBinding)
            }
            VIEW_TYPE_ITEM -> {
                val userBinding =
                    ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListViewHolder(userBinding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind()
            is ListViewHolder -> holder.bind(listDataStory[position - 1])
        }
    }

    override fun getItemCount(): Int = listDataStory.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_TITLE
        } else {
            VIEW_TYPE_ITEM
        }
    }
}

