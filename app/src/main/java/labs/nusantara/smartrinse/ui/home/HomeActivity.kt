package labs.nusantara.smartrinse.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ActivityHomeBinding
import labs.nusantara.smartrinse.ui.setting.SettingActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        processClickNavigation()
    }

    private fun processClickNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navHome -> {
                    Toast.makeText(this, "Homepage", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.navArticle -> {
                    Toast.makeText(this, "Article", Toast.LENGTH_SHORT).show()
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
                R.id.navCamera -> {
                    Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

    }
}