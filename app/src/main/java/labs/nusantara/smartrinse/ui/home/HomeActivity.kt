package labs.nusantara.smartrinse.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ActivityHomeBinding
import labs.nusantara.smartrinse.ui.article.ArticleActivity
import labs.nusantara.smartrinse.ui.setting.SettingActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupSelectedNavigation()
        setupBottomNavigation()
    }

    private fun setupSelectedNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navHome
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navHome -> {
                    true
                }
                R.id.navArticle -> {
                    val intent = Intent(this, ArticleActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navCamera -> {
                    Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show()
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