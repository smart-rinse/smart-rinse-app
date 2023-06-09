package labs.nusantara.smartrinse.ui.laundry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.Laundry
import labs.nusantara.smartrinse.services.response.ReviewsItem
import labs.nusantara.smartrinse.services.response.ServicesItem
import labs.nusantara.smartrinse.utils.Event
import labs.nusantara.smartrinse.utils.SessionModel

class LaundryDetailViewModel (private val repository: LaundryRepository) : ViewModel() {

    val listDataLaundry: MutableLiveData<List<Laundry>?> = repository.listLaundryDetail
    val listDataLaundryService: MutableLiveData<List<ServicesItem>?> = repository.listLaundryService
    val listDataLaundryReview: MutableLiveData<List<ReviewsItem>?> = repository.listLaundryReviews
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText
    val idText: LiveData<Event<String>> = repository.idText

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getDataLaundryDetail(token: String, laundryId: String) {
        viewModelScope.launch {
            repository.getLaundryDetailSimple(token, laundryId)
        }
    }

    fun getLaundryService(token: String, laundryId: String) {
        viewModelScope.launch {
            repository.getLaundryServiceSimple(token, laundryId)
        }
    }

    fun getLaundryReviews(token: String, laundryId: String) {
        viewModelScope.launch {
            repository.getLaundryReviewsSimple(token, laundryId)
        }
    }

    fun postTransaction(token: String, laundryId: String, requestBody: String) {
        viewModelScope.launch {
            repository.postTransactionOrder(token, laundryId, requestBody)
        }
    }

    fun putTransaction(token: String, trxId: String) {
        viewModelScope.launch {
            repository.putTransactionOrder(token, trxId)
        }
    }

    fun postFavorite(token: String, laundryId: String) {
        viewModelScope.launch {
            repository.postFavorite(token, laundryId)
        }
    }

    fun delFavorite(token: String, laundryId: String) {
        viewModelScope.launch {
            repository.delFavorite(token, laundryId)
        }
    }

}