package labs.nusantara.smartrinse.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import labs.nusantara.smartrinse.databinding.FragmentArticleBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val articleViewModel: ArticleViewModel by viewModels { factory }
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        requireActivity().actionBar?.hide()
        loadData()
        binding.radNull.isClickable = false
        binding.radNull2.isClickable = false
    }

    private fun loadData() {
        articleViewModel.getSession().observe(viewLifecycleOwner) { session ->
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                articleViewModel.getDataStory(tokenAuth)
                binding.rvArticle.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    setHasFixedSize(true)
                }
                articleViewModel.listDataArticle.observe(viewLifecycleOwner) { listData ->
                    binding.rvArticle.adapter = ArticleAdapter(listData)
                }
                articleViewModel.isLoading.observe(viewLifecycleOwner) { load ->
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
