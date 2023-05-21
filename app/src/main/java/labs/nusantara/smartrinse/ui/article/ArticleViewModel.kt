package labs.nusantara.smartrinse.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import labs.nusantara.smartrinse.repository.LaundryRepository
import labs.nusantara.smartrinse.services.response.ArticleItem
import labs.nusantara.smartrinse.utils.SessionModel

class ArticleViewModel (private val repository: LaundryRepository) : ViewModel() {

    val listDataArticle: LiveData<List<ArticleItem>> = repository.listArticleItem
    val isLoading: LiveData<Boolean> = repository.isLoading

    fun getSession(): LiveData<SessionModel> {
        return repository.getSession()
    }

    fun getDataStory(token: String) {
        viewModelScope.launch {
            repository.getArticleSimple(token)
        }
    }

}