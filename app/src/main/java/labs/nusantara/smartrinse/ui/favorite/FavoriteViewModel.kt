package labs.nusantara.smartrinse.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.FavoriteLaundryItem
import labs.nusantara.smartrinse.utils.Event
import labs.nusantara.smartrinse.utils.SessionModel

class FavoriteViewModel (private val repository: LaundryRepository) : ViewModel() {

    val listDataFavoriteLaundry: MutableLiveData<List<FavoriteLaundryItem>> = repository.listFavorite
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getFavorite(token: String) {
        viewModelScope.launch {
            repository.getFavorite(token)
        }
    }

}