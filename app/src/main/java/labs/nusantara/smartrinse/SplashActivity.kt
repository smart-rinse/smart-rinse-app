package labs.nusantara.smartrinse

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import labs.nusantara.smartrinse.databinding.ActivitySplashBinding
import labs.nusantara.smartrinse.ui.home.HomeActivity
import labs.nusantara.smartrinse.ui.home.HomeViewModel
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    private val timeOut = 3000L

    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        factory = ViewModelFactory.getInstance(this)

        Handler(Looper.getMainLooper()).postDelayed({
            homeViewModel.getSession().observe(this@SplashActivity){
                token = it.token
                if(!it.isLogin){
                    backLogin()
                }else {
                    gotoHome()
                }
            }
        }, timeOut)
    }

    private fun backLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun gotoHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}