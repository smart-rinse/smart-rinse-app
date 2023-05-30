package labs.nusantara.smartrinse.ui.setting

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import labs.nusantara.smartrinse.BuildConfig
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ActivitySettingBinding
import labs.nusantara.smartrinse.databinding.PopupPasswordBinding
import labs.nusantara.smartrinse.MainActivity
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class SettingActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var bindingPopup: PopupPasswordBinding
    private lateinit var factory: ViewModelFactory
    private val settingViewModel: SettingViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0f
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        factory = ViewModelFactory.getInstance(this)

        showListSetting()
    }

    private fun showListSetting() {
        val listView = findViewById<ListView>(R.id.list_view)
        val items = arrayOf("Ganti password", "FAQ", "Tentang Aplikasi", "Logout")

        val adapter = ArrayAdapter(this, R.layout.item_setting, R.id.text_item, items)
        listView.adapter = adapter

        listView.onItemClickListener = this
    }


    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
        when (position) {
            0 -> {
                changePassword()
            }
            1 -> {
                startActivity(Intent(this, MainActivity::class.java).putExtra("item", "FAQ"))
            }
            2 -> {
                infoApplication()
            }
            3 -> {
                logoutConfirmation()
            }
        }
    }

    private fun changePassword() {
        bindingPopup = PopupPasswordBinding.inflate(layoutInflater)

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_password)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.attributes?.windowAnimations = R.style.PopupAnimation

        val buttonSave = bindingPopup.buttonSave
        val editTextOldPassword = bindingPopup.edtPasswordLama
        val editTextNewPassword = bindingPopup.edtPasswordBaru

        buttonSave.setOnClickListener {
            Toast.makeText(this, "Password berubah daro ${editTextOldPassword}, menjadi ${editTextNewPassword}.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun infoApplication() {
        val versionName: String = BuildConfig.VERSION_NAME
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informasi Aplikasi")
            .setMessage(
                "Versi Aplikasi: $versionName\n\n" +
                        "Aplikasi Smart Rinse dirancang untuk memudahkan Anda dalam menentukan tempat laundry terbaik dengan teknologi machine learning.\n\n" +
                        "Terima kasih telah menggunakan Aplikasi ini.\n"
            )
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }


    private fun logoutConfirmation() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin logout?")
            .setPositiveButton("Ya") { dialog, _ ->
                settingViewModel.logout()
                dialog.dismiss()
                redirectToLoginActivity()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun redirectToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}