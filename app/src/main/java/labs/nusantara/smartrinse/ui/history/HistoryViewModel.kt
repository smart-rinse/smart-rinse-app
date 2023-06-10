package labs.nusantara.smartrinse.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.UserTransactionItem
import labs.nusantara.smartrinse.utils.SessionModel

class HistoryViewModel (private val repository: LaundryRepository) : ViewModel() {

    val listDataHistory: LiveData<List<UserTransactionItem>> = repository.listHistoryItem
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getHistory(token: String) {
        viewModelScope.launch {
            repository.getHistorySimple(token)
        }
    }

}