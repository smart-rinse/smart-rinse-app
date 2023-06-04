package labs.nusantara.smartrinse.ui.setting

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import labs.nusantara.smartrinse.BuildConfig
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.FragmentSettingBinding
import labs.nusantara.smartrinse.databinding.PopupPasswordBinding
import labs.nusantara.smartrinse.ui.faq.FaqActivity
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.ui.setting.user.UserActivity
import labs.nusantara.smartrinse.ui.setting.user.UserActivity.Companion.USER_ID
import labs.nusantara.smartrinse.utils.ViewModelFactory

class SettingFragment : Fragment(), AdapterView.OnItemClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val settingViewModel: SettingViewModel by viewModels { factory }
    private var token: String? = null
    private var userId: String? = null
    private var gender: String? = null
    private var city: String? = null
    private var name: String? = null
    private var photo: String? = null
    private var telephone: String? = null
    private var email: String? = null
    private var isLaundry: Boolean = false


    private lateinit var cardBusiness: CardView
    private lateinit var imgEditProfile: ImageView

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

        loadDataUser()

        cardBusiness = binding.cardVIewBusiness
        cardBusiness.setOnClickListener {
            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_LONG).show()
        }

        imgEditProfile = binding.imageEditProfile
        imgEditProfile.setOnClickListener {
            startActivity(
                Intent(requireContext(), UserActivity::class.java)
                    .putExtra(USER_ID, userId)
                    .putExtra("USER_GENDER", gender)
                    .putExtra("USER_CITY", city)
                    .putExtra("USER_NAME", name)
                    .putExtra("USER_PHOTO", photo)
                    .putExtra("USER_TELP", telephone)
                    .putExtra("USER_EMAIL", email)
                    .putExtra("USER_ISLAUNDRY", isLaundry)
                    .putExtra("USER_TOKEN", token)
            )
        }
    }

    private fun loadDataUser() {
        settingViewModel.getSession().observe(viewLifecycleOwner) { session ->
            token = session.token
            userId = session.userId
            val tokenAuth = session.token
            val userID = session.userId
            if (!session.isLogin) {
                Log.d("Error: ", "Error fetching data")
            } else {
                settingViewModel.getDataUser(tokenAuth, userID)
            }
        }
        settingViewModel.isLoading.observe(viewLifecycleOwner) { load ->
            showLoading(load)
        }
        settingViewModel.listDataUser.observe(viewLifecycleOwner) { listData ->
            val userData = listData.firstOrNull()
            if (userData != null) {
                binding.txtAccountemail.text = userData.email
                binding.txtAccountname.text = userData.name
                gender = userData.gender
                city = userData.city
                name = userData.name
                photo = userData.photo
                telephone = userData.telephone
                email = userData.email
                isLaundry = userData.isLaundry

                if (userData.photo.isNotEmpty()){
                    Glide.with(this@SettingFragment)
                        .load(userData.photo)
                        .circleCrop()
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(binding.imgProfilePicture)
                }
            }
        }
    }

    private fun showListSetting() {
        val items = arrayOf("Change Password", "FAQ", "Dark Mode", "About Application", "Logout")
        val adapter = SettingAdapter(requireContext(), items)
        binding.listView.adapter = adapter
        binding.listView.onItemClickListener = this
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
        when (position) {
            0 -> {
                changePassword()
            }
            1 -> {
                startActivity(Intent(requireContext(), FaqActivity::class.java))
            }
            2 -> {
                Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_SHORT).show()
            }
            3 -> {
                infoApplication()
            }
            4 -> {
                logoutConfirmation()
            }
        }
    }

    private fun changePassword() {
        val bindingPopup = PopupPasswordBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(bindingPopup.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.attributes?.windowAnimations = R.style.PopupAnimation

        val buttonSave = bindingPopup.buttonSave

        buttonSave.setOnClickListener {
            val cekToken = token.toString()
            val oldPassword = bindingPopup.edtPasswordLama.text.toString()
            val newPassword = bindingPopup.edtPasswordBaru.text.toString()
            val confirmPassword = bindingPopup.edtPasswordBaruKonfirmasi.text.toString()

            // Show Loading Bar
            settingViewModel.isLoading.observe(this@SettingFragment) {
                bindingPopup.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }

            // Send Data to Model
            bindingPopup.apply {
                userId?.let { userId ->
                    settingViewModel.putUser(
                        cekToken,
                        userId,
                        oldPassword,
                        newPassword,
                        confirmPassword
                    )
                }
            }

            // Get Notification Change Password
            settingViewModel.toastText.observe(this@SettingFragment) {
                it.getContentIfNotHandled()?.let { toastText ->
                    Toast.makeText(
                        requireContext(), toastText, Toast.LENGTH_LONG
                    ).show()
                }
            }

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun infoApplication() {
        val versionName: String = BuildConfig.VERSION_NAME
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_version))
            .setMessage(getString(R.string.dialog_version, versionName))
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun logoutConfirmation() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_confirm_logout))
            .setMessage(getString(R.string.desc_confirm_logout))
            .setPositiveButton("Yes") { dialog, _ ->
                settingViewModel.logout()
                dialog.dismiss()
                redirectToLoginActivity()
            }
            .setNegativeButton("No") { dialog, _ ->
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

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
