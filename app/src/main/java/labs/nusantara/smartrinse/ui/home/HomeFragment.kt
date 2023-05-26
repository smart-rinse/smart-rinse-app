package labs.nusantara.smartrinse.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import labs.nusantara.smartrinse.databinding.FragmentHomeBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        requireActivity().actionBar?.hide()

        loadData()
    }


    private fun loadData() {
        homeViewModel.getSession().observe(viewLifecycleOwner) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                homeViewModel.getDataLaundry(tokenAuth)
                binding.rvLaundry.apply {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    setHasFixedSize(true)
                }
                homeViewModel.listDataLaundry.observe(viewLifecycleOwner) { listData ->
                    binding.rvLaundry.adapter = HomeAdapter(listData)
                }
                homeViewModel.isLoading.observe(viewLifecycleOwner) { load ->
                    showLoading(load)
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
        _binding = null
    }
}