package labs.nusantara.smartrinse.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.utils.SessionModel

class HomeViewModel (private val repository: LaundryRepository) : ViewModel() {

    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}