package labs.nusantara.smartrinse.ui.setting.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import labs.nusantara.smartrinse.databinding.ActivityUserBinding
import labs.nusantara.smartrinse.ui.setting.SettingViewModel
import labs.nusantara.smartrinse.utils.ViewModelFactory

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var factory: ViewModelFactory
    private val settingViewModel: SettingViewModel by viewModels { factory }
    private var userId: String = ""
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Profile Account"
            setDisplayHomeAsUpEnabled(true)
        }

        factory = ViewModelFactory.getInstance(this)

        getDetailUser()

        binding.btnSave.setOnClickListener { updateProfile() }
    }

    private fun updateProfile() {
        try {
            val userCity = binding.edtCity.text.toString()
            val userTelp = binding.edtPhone.text.toString()
            val selectedButton = binding.radioGroup.checkedRadioButtonId
            var userGender = ""

            if (selectedButton != -1) {
                val valueGender = when (selectedButton) {
                    binding.radMale.id -> "Male"
                    binding.radFemale.id -> "Female"
                    else -> ""
                }
                userGender = valueGender
            }

            editProcess(userTelp, userCity, userGender)
        } catch (e: Exception) {
            Log.d("Error Message ", e.toString())
        }
    }

    private fun editProcess(userTelp: String, userCity: String, userGender: String) {
        settingViewModel.getSession().observe(this@UserActivity) {
            token = it.token
            userId = it.userId
        }

        // Show Loading Bar
        settingViewModel.isLoading.observe(this@UserActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        // Send Data to Model
        binding.apply {
            settingViewModel.putProfileUser(
                token,
                userId,
                userTelp,
                userCity,
                userGender
            )
        }
    }

    private fun getDetailUser() {
        val userId = intent.getStringExtra(USER_ID)
        if (userId != null) {
            val userGender = intent.getStringExtra("USER_GENDER")
            val userCity = intent.getStringExtra("USER_CITY")
            val userName = intent.getStringExtra("USER_NAME")
            val userPhoto = intent.getStringExtra("USER_PHOTO")
            val userTelp = intent.getStringExtra("USER_TELP")
            val userEmail = intent.getStringExtra("USER_EMAIL")

            binding.edtNamalengkap.setText(userName)
            binding.edtEmail.setText(userEmail)
            binding.edtPhone.setText(userTelp)
            binding.edtCity.setText(userCity)
            if (userPhoto != null && userPhoto.isNotEmpty()) {
                Glide.with(this@UserActivity)
                    .load(userPhoto)
                    .circleCrop()
                    .into(binding.imageUser)
            }

            if (userGender != null && userGender.isNotEmpty()) {
                when (userGender) {
                    "Male" -> binding.radMale.isChecked = true
                    "Female" -> binding.radFemale.isChecked = true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val USER_ID = ""
    }
}