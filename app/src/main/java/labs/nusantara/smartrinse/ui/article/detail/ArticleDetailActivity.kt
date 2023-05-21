package labs.nusantara.smartrinse.ui.article.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import labs.nusantara.smartrinse.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0f
            title = intent.getStringExtra(EXTRA_TITLE)
            setDisplayHomeAsUpEnabled(true)
        }

        setupWebView()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        binding.webView.webChromeClient = WebChromeClient()

        val url = intent.getStringExtra(EXTRA_DETAIL)
        url?.let {
            loadUrl(it)
        }
    }

    private fun loadUrl(url: String) {
        binding.webView.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_TITLE = "extra_title"
    }
}