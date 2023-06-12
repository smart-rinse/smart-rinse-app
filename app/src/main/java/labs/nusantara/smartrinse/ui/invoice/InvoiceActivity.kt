package labs.nusantara.smartrinse.ui.invoice

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import labs.nusantara.smartrinse.R
import labs.nusantara.smartrinse.databinding.ActivityInvoiceBinding
import labs.nusantara.smartrinse.ui.laundry.LaundryDetailViewModel
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class InvoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvoiceBinding
    private lateinit var factory: ViewModelFactory
    private val invoiceViewModel: InvoiceViewModel by viewModels { factory }
    private val trxViewModel: LaundryDetailViewModel by viewModels { factory }
    private var token: String? = null
    private var laundryId: String? = null
    private var trxId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        val idTransaction = intent.getStringExtra(EXTRA_ID)
        trxId = idTransaction

        val actionBar = (this as AppCompatActivity).supportActionBar
        actionBar?.apply {
            elevation = 0f
            title = idTransaction
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        if (idTransaction != null) {
            loadTransaction(idTransaction)
            loadInvoice(idTransaction)
        }

        invoiceViewModel.listDetailTransaction.observe(this@InvoiceActivity) { listData ->
            val data = listData?.firstOrNull()
            data?.let {
                if (it.isReviewed){
                    binding.btnWriteReview.isEnabled = false
                    binding.btnWriteReview.visibility = View.GONE
                }else {
                    binding.btnWriteReview.isEnabled = true
                    binding.btnWriteReview.visibility = View.VISIBLE
                    binding.btnWriteReview.setOnClickListener { showWriteReview() }
                }
            }
        }
    }

    private fun showWriteReview() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_add_review)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawableResource(R.drawable.background_rounded)

        val etReviewInput: TextInputEditText = dialog.findViewById(R.id.et_review_input)
        val ratingInput: RatingBar = dialog.findViewById(R.id.rating_input)
        val btnSubmitReview: Button = dialog.findViewById(R.id.btn_submit_review)

        btnSubmitReview.setOnClickListener {
            val review = etReviewInput.text.toString()
            val rating = ratingInput.rating.toInt()
            submitReview(review, rating)
            dialog.dismiss()
        }

        dialog.show()
    }



    private fun submitReview(review: String, rating: Int) {
        invoiceViewModel.getSession().observe(this@InvoiceActivity) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                Log.d("Error: ", "Error fetching data")
            } else {
                laundryId?.let { it -> invoiceViewModel.postReview(tokenAuth, it, review, rating) }
                trxId?.let {
                        trx -> trxViewModel.putTransaction(tokenAuth, trx) }
            }
        }

        invoiceViewModel.toastText.observe(this@InvoiceActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@InvoiceActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun loadTransaction(idTrx: String) {
        invoiceViewModel.getSession().observe(this@InvoiceActivity) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                Log.d("Error: ", "Error fetching data")
            } else {
                invoiceViewModel.getTrxDetail(tokenAuth, idTrx)
            }
        }
        invoiceViewModel.isLoading.observe(this@InvoiceActivity) { load ->
            showLoading(load)
        }
        invoiceViewModel.listDetailTransaction.observe(this@InvoiceActivity) { listData ->
            val data = listData?.firstOrNull()
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("dd MMMM yyyy")

            data?.let {
                binding.tvOrderId.text = it.transactionNumber
                try {
                    val parsedDate: Date = inputFormat.parse(it.transactionDate) as Date
                    val outputDate: String = outputFormat.format(parsedDate)
                    binding.tvTglTrx.text = outputDate
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                binding.tvMerchantName.text = it.namaLaundry
                binding.tvRekeningNumber.text = it.rekening ?: "N/A"
                binding.tvTotalPrice.text = "Rp. ${it.totalCost}"

                laundryId = it.idlaundry
            }
        }

    }

    private fun loadInvoice(idTrx: String) {
        invoiceViewModel.getSession().observe(this@InvoiceActivity) {
            token = it.token
            val tokenAuth = it.token
            if(!it.isLogin){
                backLogin()
            }else{
                invoiceViewModel.getServiceDetail(tokenAuth, idTrx)
                binding.rvInvoice.apply {
                    layoutManager = LinearLayoutManager(this@InvoiceActivity)
                    setHasFixedSize(true)
                }
                invoiceViewModel.listDetailService.observe(this@InvoiceActivity) { listData ->
                    binding.rvInvoice.adapter = listData?.let { it1 -> InvoiceAdapter(it1) }
                }
                invoiceViewModel.isLoading.observe(this) { load ->
                    showLoading(load)
                }
            }
        }
    }

    private fun backLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
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
        const val EXTRA_ID = "extra_id"
    }
}