package labs.nusantara.smartrinse.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ActivitySettingBinding
import labs.nusantara.smartrinse.ui.home.HomeActivity

class SettingActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0f
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

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
                val selectedText = adapterView?.getItemAtPosition(position)
                Toast.makeText(this, "Pilihanmu: $selectedText", Toast.LENGTH_LONG).show()
            }
            1 -> {
                startActivity(Intent(this, HomeActivity::class.java).putExtra("item", "FAQ"))
            }
            2 -> {
                infoApplication()
            }
            3 -> {
                logoutConfirmation()
            }
        }
    }

    private fun infoApplication() {
        val versionName: String = packageManager.getPackageInfo(packageName, 0).versionName
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
                Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}