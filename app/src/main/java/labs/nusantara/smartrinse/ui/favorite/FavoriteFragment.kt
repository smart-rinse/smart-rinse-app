package labs.nusantara.smartrinse.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import labs.nusantara.smartrinse.databinding.FragmentFavoriteBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }
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
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        loadData()
    }

    private fun loadData() {
        favoriteViewModel.getSession().observe(viewLifecycleOwner) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                favoriteViewModel.getFavorite(tokenAuth)

                if (isViewCreated) {
                    binding.rvFavorite.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                    }

                    favoriteViewModel.listDataFavoriteLaundry.observe(viewLifecycleOwner) { listData ->
                        if(listData.isNotEmpty()) {
                            binding.imageNotFound.visibility = View.GONE
                            binding.tvNotFound.visibility = View.GONE
                            binding.rvFavorite.adapter = FavoriteAdapter(listData)
                        }else {
                            binding.imageNotFound.visibility = View.VISIBLE
                            binding.tvNotFound.visibility = View.VISIBLE
                            binding.rvFavorite.adapter = null
                        }
                    }
                    favoriteViewModel.isLoading.observe(viewLifecycleOwner) { load ->
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