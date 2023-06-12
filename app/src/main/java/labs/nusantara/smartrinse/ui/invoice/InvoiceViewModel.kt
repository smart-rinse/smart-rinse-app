package labs.nusantara.smartrinse.ui.invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.UserTransactionDetail
import labs.nusantara.smartrinse.services.response.UserTransactionDetailServicesItem
import labs.nusantara.smartrinse.utils.Event
import labs.nusantara.smartrinse.utils.SessionModel

class InvoiceViewModel (private val repository: LaundryRepository) : ViewModel() {

    val listDetailTransaction: LiveData<List<UserTransactionDetail>> = repository.listDetailTransaction
    val listDetailService: LiveData<List<UserTransactionDetailServicesItem>?> = repository.listDetailService
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getServiceDetail(token: String, idTrx: String) {
        viewModelScope.launch {
            repository.getServiceDetail(token, idTrx)
        }
    }

    fun getTrxDetail(token: String, idTrx: String) {
        viewModelScope.launch {
            repository.getServiceDetail(token, idTrx)
        }
    }

    fun postReview(token: String, laundryId: String, content: String, rating: Int) {
        viewModelScope.launch {
            repository.postReview(token, laundryId, content, rating)
        }
    }

}