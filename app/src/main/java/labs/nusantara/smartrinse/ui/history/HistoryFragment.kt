package labs.nusantara.smartrinse.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import labs.nusantara.smartrinse.databinding.FragmentHistoryBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var factory: ViewModelFactory
    private val historyViewModel: HistoryViewModel by viewModels { factory }
    private var token: String? = null
    private var isViewCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factory = ViewModelFactory.getInstance(requireContext())
        requireActivity().actionBar?.hide()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        loadData()
    }

    private fun loadData() {
        historyViewModel.getSession().observe(viewLifecycleOwner) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                historyViewModel.getHistory(tokenAuth)

                if (isViewCreated) {
                    binding.rvHistory.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                    }

                    historyViewModel.listDataHistory.observe(viewLifecycleOwner) { listData ->
                        if (listData.isNotEmpty()) {
                            binding.imageNotFound.visibility = View.GONE
                            binding.tvNotFound.visibility = View.GONE
                            binding.rvHistory.adapter = HistoryAdapter(listData)
                        } else {
                            binding.imageNotFound.visibility = View.VISIBLE
                            binding.tvNotFound.visibility = View.VISIBLE
                            binding.rvHistory.adapter = null
                        }
                    }
                    historyViewModel.isLoading.observe(viewLifecycleOwner) { load ->
                        showLoading(load)
                    }
                }
            }
        }
    }

    private fun backLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
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
        isViewCreated = false
    }
}
