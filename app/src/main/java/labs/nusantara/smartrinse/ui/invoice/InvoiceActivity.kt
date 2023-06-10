package labs.nusantara.smartrinse.ui.invoice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import labs.nusantara.smartrinse.databinding.ActivityInvoiceBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class InvoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvoiceBinding
    private lateinit var factory: ViewModelFactory
    private val invoiceViewModel: InvoiceViewModel by viewModels { factory }
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        val idTransaction = intent.getStringExtra(EXTRA_ID)

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
                binding.tvRekeningNumber.text = it.rekening?.toString() ?: "N/A"
                binding.tvTotalPrice.text = "Rp. ${it.totalCost}"
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