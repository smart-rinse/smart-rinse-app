package labs.nusantara.smartrinse.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.RegisterResponse
import labs.nusantara.smartrinse.utils.Event

class RegisterViewModel(private val repository: LaundryRepository) : ViewModel() {
    val registerResponse: LiveData<RegisterResponse> = repository.regResponse
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText

    fun postRegister(name: String, email: String, password: String, confPassword: String) {
        viewModelScope.launch {
            repository.postRegister(name, email, password, confPassword)
        }
    }
}