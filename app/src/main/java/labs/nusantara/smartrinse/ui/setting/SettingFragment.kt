package labs.nusantara.smartrinse.ui.setting

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.FragmentSettingBinding
import labs.nusantara.smartrinse.databinding.PopupPasswordBinding
import labs.nusantara.smartrinse.ui.home.HomeActivity
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class SettingFragment : Fragment(), AdapterView.OnItemClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var bindingPopup: PopupPasswordBinding
    private lateinit var factory: ViewModelFactory
    private val settingViewModel: SettingViewModel by viewModels { factory }
//    private lateinit var sessionPreferences: SessionPreferences by preferencesDataStore()
//    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.apply {
            elevation = 0f
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        factory = ViewModelFactory.getInstance(requireContext())

        showListSetting()

//        sessionPreferences = SessionPreferences.getInstance()
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            sessionPreferences.getSession().collect { sessionModel ->
//                val idKey = sessionModel.userId
//                Log.d("ID_KEY:", idKey)
//            }
//        }

    }

    private fun showListSetting() {
        val items = arrayOf("Ganti password", "FAQ", "Tentang Aplikasi", "Logout")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_setting, R.id.text_item, items)
        binding.listView.adapter = adapter
        binding.listView.onItemClickListener = this
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
        when (position) {
            0 -> {
                changePassword()
            }
            1 -> {
                startActivity(
                    Intent(requireContext(), HomeActivity::class.java).putExtra(
                        "item",
                        "FAQ"
                    )
                )
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

        val dialog = Dialog(requireContext())
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
            Toast.makeText(
                requireContext(),
                "Password berubah dari $editTextOldPassword menjadi $editTextNewPassword.",
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun infoApplication() {
        val versionName: String =
            requireContext().packageManager.getPackageInfo(
                requireContext().packageName,
                0
            ).versionName
        val builder = AlertDialog.Builder(requireContext())
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
        val builder = AlertDialog.Builder(requireContext())
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
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
