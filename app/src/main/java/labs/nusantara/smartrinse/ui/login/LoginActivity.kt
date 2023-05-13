package labs.nusantara.smartrinse.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import labs.nusantara.smartrinse.databinding.ActivityLoginBinding
import labs.nusantara.smartrinse.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.labelRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.labelRegister.id -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}