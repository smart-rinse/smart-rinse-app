package labs.nusantara.smartrinse.ui.setting.user

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import labs.nusantara.smartrinse.databinding.ActivityUserBinding
import labs.nusantara.smartrinse.ui.setting.SettingViewModel
import labs.nusantara.smartrinse.utils.ViewModelFactory
import labs.nusantara.smartrinse.utils.reduceFileImage
import labs.nusantara.smartrinse.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var factory: ViewModelFactory
    private val settingViewModel: SettingViewModel by viewModels { factory }
    private var valueUserId: String = ""
    private var valueToken: String = ""
    private var getFile: File? = null

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
        binding.imgUpload.setOnClickListener { startGallery() }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val file = uriToFile(uri, this@UserActivity)
                getFile = file
                 Glide.with(this@UserActivity)
                     .load(selectedImg)
                     .circleCrop()
                     .into(binding.imageUser)
            }
        }
    }


    private fun updateProfile() {
        try {
            val userCity = binding.edtCity.text.toString().toRequestBody("text/plain".toMediaType())
            val userTelp =
                binding.edtPhone.text.toString().toRequestBody("text/plain".toMediaType())
            val selectedButton = binding.radioGroup.checkedRadioButtonId
            var userGender: RequestBody? = null

            if (selectedButton != -1) {
                val valueGender = when (selectedButton) {
                    binding.radMale.id -> "Male"
                    binding.radFemale.id -> "Female"
                    else -> ""
                }
                userGender = valueGender.toRequestBody("text/plain".toMediaTypeOrNull())
                Log.d("GenderValue : ", "$userGender")
            }

            when {
                getFile != null -> {
                    val file = reduceFileImage(getFile as File)
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "photo",
                        file.name,
                        requestImageFile
                    )

                    if (userGender != null) {
                        Log.d(
                            "Data Masuk : ",
                            "${userTelp} - ${userCity} - ${userGender} - ${imageMultipart}"
                        )
                        editProcess(userTelp, userCity, userGender, imageMultipart)
                    }
                }
            }

        } catch (e: Exception) {
            Log.d("Error Message ", e.toString())
        }
    }

    private fun editProcess(
        userTelp: RequestBody,
        userCity: RequestBody,
        userGender: RequestBody,
        imageMultipart: MultipartBody.Part
    ) {
        // Show Loading Bar
        settingViewModel.isLoading.observe(this@UserActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        // Send Data to Model
        binding.apply {
            settingViewModel.putProfileUser(
                valueToken,
                valueUserId,
                userTelp,
                userCity,
                userGender,
                imageMultipart
            )
        }
    }

    private fun getDetailUser() {
        val userId = intent.getStringExtra(USER_ID)
        val userToken = intent.getStringExtra("USER_TOKEN")
        if (userId != null && userToken != null) {
            val userGender = intent.getStringExtra("USER_GENDER")
            val userCity = intent.getStringExtra("USER_CITY")
            val userName = intent.getStringExtra("USER_NAME")
            val userPhoto = intent.getStringExtra("USER_PHOTO")
            val userTelp = intent.getStringExtra("USER_TELP")
            val userEmail = intent.getStringExtra("USER_EMAIL")
            Log.d("TOKENKU : ", "$userToken")

            valueUserId = userId
            valueToken = userToken

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
        private const val GALLERY_REQUEST_CODE = 100
    }
}