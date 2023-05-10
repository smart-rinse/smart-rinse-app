package labs.nusantara.smartrinse.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import labs.nusantara.smartrinse.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}