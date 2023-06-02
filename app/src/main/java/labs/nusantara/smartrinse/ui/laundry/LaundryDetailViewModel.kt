package labs.nusantara.smartrinse.ui.laundry

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.Laundry
import labs.nusantara.smartrinse.services.response.ReviewsItem
import labs.nusantara.smartrinse.utils.SessionModel

class LaundryDetailViewModel (private val repository: LaundryRepository) : ViewModel() {

    val listDataLaundry: LiveData<List<Laundry>> = repository.listLaundryDetail
    val listDataLaundryReview: LiveData<List<ReviewsItem>> = repository.listLaundryDetailReview
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getDataLaundryDetail(token: String, laundryId: String) {
        viewModelScope.launch {
            repository.getLaundryDetailSimple(token, laundryId)
        }
    }

}