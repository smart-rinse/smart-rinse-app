package labs.nusantara.smartrinse.ui.faq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import labs.nusantara.smartrinse.databinding.ActivityFaqBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class FaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaqBinding
    private lateinit var factory: ViewModelFactory
    private val faqViewModel: FaqViewModel by viewModels { factory }
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "F.A.Q"
            setDisplayHomeAsUpEnabled(true)
        }

        factory = ViewModelFactory.getInstance(this)

        loadData()
    }

    private fun loadData() {
        faqViewModel.getSession().observe(this@FaqActivity) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                faqViewModel.getDataFaq(tokenAuth)
                binding.rvFaq.apply {
                    layoutManager = LinearLayoutManager(this@FaqActivity)
                    setHasFixedSize(true)
                }
                faqViewModel.listDataArticle.observe(this@FaqActivity) { listData ->
                    binding.rvFaq.adapter = FaqAdapter(listData)
                }
                faqViewModel.isLoading.observe(this@FaqActivity) { load ->
                    showLoading(load)
                }
            }
        }
    }

    private fun backLogin() {
        startActivity(Intent(this@FaqActivity, LoginActivity::class.java))
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


}