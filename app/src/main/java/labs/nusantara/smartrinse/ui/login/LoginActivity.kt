package labs.nusantara.smartrinse.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ActivityLoginBinding
import labs.nusantara.smartrinse.ui.home.HomeActivity
import labs.nusantara.smartrinse.ui.register.RegisterActivity
import labs.nusantara.smartrinse.utils.SessionModel
import labs.nusantara.smartrinse.utils.ViewModelFactory

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.labelRegister.setOnClickListener(this)

        factory = ViewModelFactory.getInstance(this)

        loginAction()
    }

    private fun loginAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (edtEmail.length() == 0 || !binding.edtEmail.error.isNullOrEmpty()) {
                    edtEmail.error = getString(R.string.aturan_email)
                }else if(edtPassword.length() == 0 || !binding.edtPassword.error.isNullOrEmpty()) {
                    edtPassword.error = getString(R.string.aturan_password)
                }else {
                    prosesLogin()
                }
            }
        }
    }

    private fun prosesLogin() {
        // Show Loading Bar
        loginViewModel.isLoading.observe(this@LoginActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        // Send Data to Model
        binding.apply {
            loginViewModel.postLogin(
                edtEmail.text.toString(),
                edtPassword.text.toString()
            )
        }

        // Get Notification Login
        loginViewModel.toastText.observe(this@LoginActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@LoginActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Get Session Token
        loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
            saveSession(
                SessionModel(
                    response.data.email,
                    response.data.name,
                    AUTH_KEY +(response.data.accessToken),
                    true
                )
            )
        }

        loginViewModel.login()
        loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
            if (response.success) {
                val token = response.data.accessToken
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.putExtra("extra_token", token)
                startActivity(intent)
                finish()
            }
        }
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

    private fun saveSession(session: SessionModel){
        loginViewModel.saveSession(session)
    }

    companion object {
        private const val AUTH_KEY = "Bearer "
    }
}