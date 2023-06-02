package labs.nusantara.smartrinse.ui.laundry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import labs.nusantara.smartrinse.databinding.ActivityLaundryBinding
import labs.nusantara.smartrinse.utils.ViewModelFactory

class LaundryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaundryBinding
    private lateinit var factory: ViewModelFactory
    private val laundryViewModel: LaundryDetailViewModel by viewModels { factory }
    private var token: String? = null

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

        loadItem()
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

}