package labs.nusantara.smartrinse.ui.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.FaqItem
import labs.nusantara.smartrinse.utils.SessionModel

class FaqViewModel (private val repository: LaundryRepository) : ViewModel() {

    val listDataArticle: LiveData<List<FaqItem>> = repository.listFaqItem
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getDataFaq(token: String) {
        viewModelScope.launch {
            repository.getFaq(token)
        }
    }

}