package labs.nusantara.smartrinse.ui.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ActivityArticleBinding
import labs.nusantara.smartrinse.ui.home.HomeActivity
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.ui.setting.SettingActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private lateinit var factory: ViewModelFactory
    private val articleViewModel: ArticleViewModel by viewModels { factory }
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        supportActionBar?.hide()

        loadData()
        setupSelectedNavigation()
        setupBottomNavigation()
    }

    private fun loadData() {
        articleViewModel.getSession().observe(this@ArticleActivity) {
            token = it.token
            val tokenAuth = it.token
            if(!it.isLogin){
                backLogin()
            }else{
                articleViewModel.getDataStory(tokenAuth)
                binding.rvArticle.apply {
                    layoutManager = LinearLayoutManager(this@ArticleActivity)
                    setHasFixedSize(true)
                }
                articleViewModel.listDataArticle.observe(this@ArticleActivity) { listData ->
                    binding.rvArticle.adapter = ArticleAdapter(listData)
                }
                articleViewModel.isLoading.observe(this) { load ->
                    showLoading(load)
                }
            }
        }
    }

    private fun backLogin() {
        startActivity(Intent(this@ArticleActivity, LoginActivity::class.java))
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupSelectedNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navArticle
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navHome -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navArticle -> {
                    true
                }
                R.id.navCamera -> {
                true
            }
                R.id.navChat -> {
                    Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navSetting -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }

}