package labs.nusantara.smartrinse.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.LaundryItem
import labs.nusantara.smartrinse.services.response.LaundryRecomendationItem
import labs.nusantara.smartrinse.utils.SessionModel

class HomeViewModel(private val repository: LaundryRepository) : ViewModel() {

    val listDataLaundry: LiveData<List<LaundryItem>> = repository.listLaundryItem
    val listDataRecLaundry: LiveData<List<LaundryRecomendationItem>> = repository.listLaundryRecItem
    val isLoading: LiveData<Boolean> = repository.isLoading
    val isRecLoading: LiveData<Boolean> = repository.isRecLoading

    private var currentAddress: String? = null

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getCurrentAddress(): String? {
        return currentAddress
    }

    fun setCurrentAddress(address: String?) {
        currentAddress = address
    }

    fun getDataLaundry(token: String) {
        viewModelScope.launch {
            repository.getLaundrySimple(token)
        }
    }

    fun getDataLaundrySentiment(token: String) {
        viewModelScope.launch {
            repository.getLaundrySentiment(token)
        }
    }
}
