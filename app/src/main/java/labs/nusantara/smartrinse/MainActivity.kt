package labs.nusantara.smartrinse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import labs.nusantara.smartrinse.databinding.ActivityHomeBinding
import labs.nusantara.smartrinse.ui.article.ArticleFragment
import labs.nusantara.smartrinse.ui.favorite.FavoriteFragment
import labs.nusantara.smartrinse.ui.history.HistoryFragment
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
                R.id.navHistory -> replaceFragment(HistoryFragment())
                R.id.navFavorite -> replaceFragment(FavoriteFragment())
                R.id.navSetting -> replaceFragment(SettingFragment())
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