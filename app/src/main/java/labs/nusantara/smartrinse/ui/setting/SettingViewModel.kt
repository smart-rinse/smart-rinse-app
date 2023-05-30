package labs.nusantara.smartrinse.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.User
import labs.nusantara.smartrinse.utils.Event
import labs.nusantara.smartrinse.utils.SessionModel

class SettingViewModel (private val repository: LaundryRepository) : ViewModel() {
    val listDataUser: LiveData<List<User>> = repository.listUserItem
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getDataUser(token: String, userId: String) {
        viewModelScope.launch {
            repository.getUserDetail(token, userId)
        }
    }

    fun putUser(token: String, userId: String, oldPassword: String, newPassword: String, confirmPassword: String) {
        viewModelScope.launch {
            repository.putUser(token, userId, oldPassword, newPassword, confirmPassword)
        }
    }

    fun putProfileUser(token: String, userId: String, userTelp: String, userCity: String, userGender: String) {
        viewModelScope.launch {
            repository.putProfileUser(token, userId, userTelp, userCity, userGender)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}