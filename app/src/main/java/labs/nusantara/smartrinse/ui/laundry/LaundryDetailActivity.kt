package labs.nusantara.smartrinse.ui.laundry

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import labs.nusantara.smartrinse.databinding.ActivityLaundryBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class LaundryDetailActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityLaundryBinding
    private lateinit var factory: ViewModelFactory
    private val laundryViewModel: LaundryDetailViewModel by viewModels { factory }
    private var token: String? = null
    private var valueLaundryId: String? = null
    private var noWhatsapp: String? = null
    private val animationDuration = 200L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLaundryDetail)
        supportActionBar?.apply {
            title = intent.getStringExtra(EXTRA_LAUNDRYNAME)
            setDisplayHomeAsUpEnabled(true)
        }

        factory = ViewModelFactory.getInstance(this)

        val laundryId = intent.getStringExtra(EXTRA_LAUNDRYDETAIL)

        if (laundryId != null) {
            loadData(laundryId)
            valueLaundryId = laundryId
        }

        binding.imgShare.setOnClickListener(this)
        binding.btnChat.setOnClickListener(this)
        loadService()

        binding.radNull.isClickable = false
        binding.radNull2.isClickable = false
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = binding.root.findViewById<RadioButton>(checkedId)
            val selectedMenu = selectedRadioButton.text.toString()

            when (selectedMenu) {
                "Services" -> loadService()
                "Reviews" -> loadReviews()
            }
        }
    }

    private fun shareContent() {
        laundryViewModel.listDataLaundry.observe(this@LaundryDetailActivity) { listData ->
            val data = listData.firstOrNull()
            if (data != null) {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val shareBody =
                    "I found \"${data.namaLaundry}\" laundry on the \"Smart Rinse\" app. Let's download it now at play store. "
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Smart Rinse")
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(sharingIntent, "Share via"))
            }
        }
    }

    private fun loadReviews() {
        binding.rvLaundryReviews.visibility = View.VISIBLE
        binding.layoutAddReview.visibility = View.VISIBLE
        binding.rvLaundryService.visibility = View.GONE
        laundryViewModel.getSession().observe(this@LaundryDetailActivity) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                valueLaundryId?.let { laundryViewModel.getLaundryReviews(tokenAuth, it) }
                binding.rvLaundryReviews.apply {
                    layoutManager = LinearLayoutManager(this@LaundryDetailActivity)
                    setHasFixedSize(true)
                }
                laundryViewModel.listDataLaundryReview.observe(this@LaundryDetailActivity) { listData ->
                    binding.rvLaundryReviews.adapter = listData?.let { LaundryReviewsAdapter(it) }
                }
                laundryViewModel.isLoading.observe(this@LaundryDetailActivity) { load ->
                    showLoading(load)
                }
            }
        }
    }

    private fun loadService() {
        binding.rvLaundryService.visibility = View.VISIBLE
        binding.rvLaundryReviews.visibility = View.GONE
        binding.layoutAddReview.visibility = View.GONE
        laundryViewModel.getSession().observe(this@LaundryDetailActivity) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                valueLaundryId?.let { laundryViewModel.getLaundryService(tokenAuth, it) }
                binding.rvLaundryService.apply {
                    layoutManager = LinearLayoutManager(this@LaundryDetailActivity)
                    setHasFixedSize(true)
                }
                laundryViewModel.listDataLaundryService.observe(this@LaundryDetailActivity) { listData ->
                    binding.rvLaundryService.adapter = listData?.let { LaundryAdapter(it) }
                }
                laundryViewModel.isLoading.observe(this@LaundryDetailActivity) { load ->
                    showLoading(load)
                }
            }
        }
    }

    private fun backLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun loadData(laundryId: String) {
        laundryViewModel.getSession().observe(this@LaundryDetailActivity) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                Log.d("Error: ", "Error fetching data")
            } else {
                laundryViewModel.getDataLaundryDetail(tokenAuth, laundryId)
            }
        }
        laundryViewModel.isLoading.observe(this@LaundryDetailActivity) { load ->
            showLoading(load)
        }
        laundryViewModel.listDataLaundry.observe(this@LaundryDetailActivity) { listData ->
            val data = listData.firstOrNull()
            if (data != null) {
                if (data.photo.isNotEmpty()) {
                    Glide.with(this@LaundryDetailActivity)
                        .load(data.photo)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(binding.imgMerchant)
                }
                binding.tvMerchantName.text = data.namaLaundry
                binding.tvMerchantAddress.text = data.alamat
                binding.tvMerchantRating.text = "${data.averageRating}"
                binding.tvRatingCount.text = "(${data.countReviews} Reviews)"
                val ratingBar = binding.ratingMerchant
                val rating = data.averageRating.toFloat()
                ratingBar.rating = rating

                noWhatsapp = "62xxxxxxxxxxx"
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_LAUNDRYDETAIL = "extra_laundrydetail"
        const val EXTRA_LAUNDRYNAME = "extra_laundryname"
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.imgShare.id -> {
                val clickAnimation = ScaleAnimation(
                    1f, 0.9f,
                    1f, 0.9f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                )

                clickAnimation.duration = animationDuration
                clickAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        // Animation start event
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        // Animation end event
                        shareContent()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        // Animation repeat event
                    }
                })

                binding.imgShare.startAnimation(clickAnimation)
            }

            binding.btnChat.id -> {
                val url = "https://api.whatsapp.com/send?phone=$noWhatsapp"
                try {
                    val pm = v.context.packageManager
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    v.context.startActivity(i)
                } catch (e: PackageManager.NameNotFoundException) {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    v.context.startActivity(i)
                }

            }
        }
    }

}