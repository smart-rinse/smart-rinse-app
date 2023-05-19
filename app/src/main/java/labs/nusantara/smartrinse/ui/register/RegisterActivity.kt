package labs.nusantara.smartrinse.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ActivityRegisterBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.labelLogin.setOnClickListener(this)

        factory = ViewModelFactory.getInstance(this)

        registerAction()
    }

    private fun registerAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                if(edtPassword.text.toString() == edtConfpassword.text.toString()) {
                    if(edtName.length() == 0){
                        edtName.error = getString(R.string.aturan_nama)
                    }else if(edtEmail.length() == 0 || !binding.edtEmail.error.isNullOrEmpty()){
                        edtEmail.error = getString(R.string.aturan_email)
                    }else if(edtPassword.length() == 0 || !binding.edtPassword.error.isNullOrEmpty()){
                        edtPassword.error = getString(R.string.aturan_password)
                    }else{
                        prosesRegister()
                    }
                }else {
                    edtConfpassword.error = getString(R.string.aturan_confpassword)
                }

            }
        }
    }

    private fun prosesRegister() {
        // Show Loading Bar
        registerViewModel.isLoading.observe(this@RegisterActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        // Send Data to Model
        binding.apply {
            registerViewModel.postRegister(
                edtName.text.toString(),
                edtEmail.text.toString(),
                edtPassword.text.toString(),
                edtConfpassword.text.toString()
            )
        }

        // Get Notification Register
        registerViewModel.toastText.observe(this@RegisterActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@RegisterActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Action Register Done
        registerViewModel.registerResponse.observe(this@RegisterActivity) { response ->
            if (response.success) {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
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