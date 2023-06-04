package labs.nusantara.smartrinse.ui.laundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import labs.nusantara.smartrinse.databinding.ActivityLaundryBinding
import labs.nusantara.smartrinse.utils.ViewModelFactory

class LaundryDetailActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityLaundryBinding
    private lateinit var factory: ViewModelFactory
    private val laundryViewModel: LaundryDetailViewModel by viewModels { factory }
    private var token: String? = null
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
        }

        binding.imgShare.setOnClickListener(this)
        loadItem()
    }

    private fun shareContent() {
        laundryViewModel.listDataLaundry.observe(this@LaundryDetailActivity) { listData ->
            val data = listData.firstOrNull()
            if (data != null) {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val shareBody = "I found \"${data.namaLaundry}\" laundry on the \"Smart Rinse\" app. Let's download it now at play store. "
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Smart Rinse")
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(sharingIntent, "Share via"))
            }
        }
    }

    private fun loadItem(){
        val itemList = arrayOf("Service 1", "Service 2", "Service 3", "Service 4", "Service 5", "Service 6", "Service 7", "Service 8")

        val recyclerView: RecyclerView = binding.rvLaundryService
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = LaundryAdapter(itemList.toList())
        recyclerView.adapter = adapter
    }

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
                if (data.photo.isNotEmpty()){
                    Glide.with(this@LaundryDetailActivity)
                        .load(data.photo)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(binding.imgMerchant)
                }
                binding.tvMerchantName.text = data.namaLaundry
                binding.tvMerchantAddress.text = data.alamat
                binding.tvMerchantRating.text = data.averageRating.toString()
                val ratingBar = binding.ratingMerchant
                val rating = data.averageRating
                ratingBar.rating = rating

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
        }
    }

}