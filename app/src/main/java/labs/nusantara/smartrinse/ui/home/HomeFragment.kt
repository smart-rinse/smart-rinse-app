package labs.nusantara.smartrinse.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import labs.nusantara.smartrinse.databinding.FragmentHomeBinding
import labs.nusantara.smartrinse.ui.login.LoginActivity
import labs.nusantara.smartrinse.utils.ViewModelFactory
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }
    private var token: String? = null
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var currentAddress: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        requireActivity().actionBar?.hide()

        // Load address from session if available
        val currentAddress = homeViewModel.getCurrentAddress()

        if (currentAddress != null) {
            binding.ivPinmap.visibility = View.VISIBLE
            binding.labelLocation.visibility = View.VISIBLE
            binding.labelLocation.text = currentAddress
        }

        loadData()
        searchData()
        setLocationMap()
    }

    private fun searchData() {
        binding.svLaundry.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d("Masuk Search :", query.toString())
                    if (query != null) {
                        loadSearch(query)
                    } else {
                        loadData()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        loadData() // Load default data when the search query is empty
                    }
                    return true
                }
            })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save current address to the ViewModel
        homeViewModel.setCurrentAddress(binding.labelLocation.text?.toString())
    }

    private fun loadSay(name: String) {
        // Get the current time
        val currentTime = Calendar.getInstance()
        val hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY)

        // Determine the time of day
        val timeOfDay = when (hourOfDay) {
            in 0..11 -> "Morning"
            in 12..16 -> "Afternoon"
            else -> "Evening"
        }

        val timeOfDayString = "Good $timeOfDay"
        binding.labelSay.text = timeOfDayString
        binding.labelSayName.text = name
    }

    private fun setLocationMap() {
        // Cek apakah alamat sudah ada di sesi
        if (currentAddress != null) {
            binding.ivPinmap.visibility = View.VISIBLE
            binding.labelLocation.visibility = View.VISIBLE
            binding.labelLocation.text = currentAddress
            return
        }

        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val latitude = location.latitude
                val longitude = location.longitude
                val address = reverseGeocode(latitude, longitude)
                binding.ivPinmap.visibility = View.VISIBLE
                binding.labelLocation.visibility = View.VISIBLE
                binding.labelLocation.text = address
                Log.d("ReverseGeocode", "Address: $address")

                // Save current address to the ViewModel
                homeViewModel.setCurrentAddress(address)

                locationManager.removeUpdates(this)
            }

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        // Check location permission
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Request location updates
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }


    private fun reverseGeocode(latitude: Double, longitude: Double): String? {
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses: List<Address>?

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1)
                Log.d("A", addresses.toString())
                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    // Format the address components as per your requirement
                    val addressLine = address.getAddressLine(0)
                    val local = address.locality
                    val subAdmin = address.subAdminArea
                    val state = address.adminArea
                    val country = address.countryName
                    val postalCode = address.postalCode
                    return local
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun loadSearch(keyword: String) {
        // Reset the RecyclerView adapters
        binding.labelRecLaundry.visibility = View.GONE
        binding.rvRecLaundry.adapter = null
        binding.rvLaundry.adapter = null

        homeViewModel.getSession().observe(viewLifecycleOwner) { session ->
            val name = session.name
            loadSay(name)
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                // Get All Laundry
                homeViewModel.getSearchLaundry(keyword)
                binding.rvLaundry.apply {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    setHasFixedSize(true)
                }
                homeViewModel.listDataLaundrySearch.observe(viewLifecycleOwner) { listData ->
                    Log.d("List : ", listData.toString())
                    binding.rvLaundry.adapter = HomeSearchAdapter(listData)
                }
                homeViewModel.isLoading.observe(viewLifecycleOwner) { load ->
                    showLoading(load)
                }
            }
        }
    }

    private fun loadData() {
        // Reset the RecyclerView adapters
        binding.labelRecLaundry.visibility = View.VISIBLE
        binding.rvRecLaundry.adapter = null
        binding.rvLaundry.adapter = null

        homeViewModel.getSession().observe(viewLifecycleOwner) { session ->
            val name = session.name
            loadSay(name)
            token = session.token
            val tokenAuth = session.token
            if (!session.isLogin) {
                backLogin()
            } else {
                // Get Recomendation Laundry
                homeViewModel.getDataLaundrySentiment(tokenAuth)
                binding.rvRecLaundry.apply {
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                }
                homeViewModel.listDataRecLaundry.observe(viewLifecycleOwner) { listData ->
                    binding.rvRecLaundry.adapter = HomeRecAdapter(listData)
                }
                homeViewModel.isRecLoading.observe(viewLifecycleOwner) { load ->
                    showRecLoading(load)
                }

                // Get All Laundry
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

    private fun showRecLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> binding.progressBarRec.visibility = View.VISIBLE
            false -> binding.progressBarRec.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> binding.progressBar.visibility = View.VISIBLE
            false -> binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}
