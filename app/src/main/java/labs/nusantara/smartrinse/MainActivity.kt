package labs.nusantara.smartrinse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import labs.nusantara.smartrinse.databinding.ActivityHomeBinding
import labs.nusantara.smartrinse.ui.article.ArticleFragment
import labs.nusantara.smartrinse.ui.home.HomeFragment
import labs.nusantara.smartrinse.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navHome -> replaceFragment(HomeFragment())
                R.id.navArticle -> replaceFragment(ArticleFragment())
                R.id.navHistory -> {
                    Toast.makeText(this, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                }
                R.id.navFavorite -> {
                    Toast.makeText(this, "Feature Coming Soon", Toast.LENGTH_SHORT).show()
                }
                R.id.navSetting -> {
                    replaceFragment(SettingFragment())
                }
                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}