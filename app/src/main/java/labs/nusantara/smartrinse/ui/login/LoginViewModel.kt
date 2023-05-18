package labs.nusantara.smartrinse.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.LoginResponse
import labs.nusantara.smartrinse.utils.Event
import labs.nusantara.smartrinse.utils.SessionModel

class LoginViewModel (private val repository: LaundryRepository) : ViewModel() {
    val loginResponse: LiveData<LoginResponse> = repository.loginResponse
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            repository.postLogin(email, password)
        }
    }

    fun saveSession(session: SessionModel) {
        viewModelScope.launch {
            repository.saveSession(session)
        }
    }

    fun login() {
        viewModelScope.launch {
            repository.login()
        }
    }
}