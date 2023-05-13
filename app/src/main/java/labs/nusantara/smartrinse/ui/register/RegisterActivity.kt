package labs.nusantara.smartrinse.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import labs.nusantara.smartrinse.databinding.ActivityRegisterBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.labelLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.labelLogin.id -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}