package labs.nusantara.smartrinse.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import labs.nusantara.smartrinse.services.api.APIService
import labs.nusantara.smartrinse.services.response.*
import labs.nusantara.smartrinse.utils.Event
import labs.nusantara.smartrinse.utils.SessionModel
import labs.nusantara.smartrinse.utils.SessionPreferences
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaundryRepository private constructor(
    private val preferences: SessionPreferences,
    private val apiService: APIService
) {
    private val _regResponse = MutableLiveData<RegisterResponse>()
    val regResponse: LiveData<RegisterResponse> = _regResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRecLoading = MutableLiveData<Boolean>()
    val isRecLoading: LiveData<Boolean> = _isRecLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _listArticleItem = MutableLiveData<List<ArticleItem>>()
    val listArticleItem: LiveData<List<ArticleItem>> = _listArticleItem

    private val _listLaundryItem = MutableLiveData<List<LaundryItem>>()
    val listLaundryItem: LiveData<List<LaundryItem>> = _listLaundryItem

    private val _listLaundryRecItem = MutableLiveData<List<LaundryRecomendationItem>>()
    val listLaundryRecItem: LiveData<List<LaundryRecomendationItem>> = _listLaundryRecItem

    private val _listUserItem = MutableLiveData<List<User>>()
    val listUserItem: LiveData<List<User>> = _listUserItem

    private val _listFaqItem = MutableLiveData<List<FaqItem>>()
    val listFaqItem: LiveData<List<FaqItem>> = _listFaqItem

    private val _listLaundryDetail = MutableLiveData<List<Laundry>?>()
    val listLaundryDetail: MutableLiveData<List<Laundry>?> = _listLaundryDetail

    private val _listLaundryService = MutableLiveData<List<ServicesItem>?>()
    val listLaundryService: MutableLiveData<List<ServicesItem>?> = _listLaundryService

    private val _listLaundryReviews = MutableLiveData<List<ReviewsItem>?>()
    val listLaundryReviews: MutableLiveData<List<ReviewsItem>?> = _listLaundryReviews

    private val _changePasswordResponse = MutableLiveData<UserPasswordResponse>()

    private val _changeProfileResponse = MutableLiveData<UserDetailResponse>()

    private val _trxResponse = MutableLiveData<TransactionResponse>()
    val trxResponse: LiveData<TransactionResponse> = _trxResponse


    fun postRegister(name: String, email: String, password: String, confPassword: String) {
        _isLoading.value = true
        val client = apiService.postRegister(name, email, password, confPassword)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _regResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.postLogin(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event("Login Failed")
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun getArticleSimple(token: String) {
        Log.d("RESPONSE:", token)
        _isLoading.value = true
        val client = apiService.getArticle(token)
        client.enqueue(object : Callback<ArticleResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                _isLoading.value = false
                val listData = response.body()?.article
                if (response.isSuccessful) {
                    if (listData.isNullOrEmpty()) {
                        _toastText.value = Event("Artikel tidak ditemukan")
                    } else {
                        _listArticleItem.value = listData
                    }
                } else {
                    _toastText.value = Event(response.message())
                    Log.e(TAG, "ErrorMessage: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event("No internet connection")
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun getLaundrySimple(token: String) {
        Log.d("RESPONSE:", token)
        _isLoading.value = true
        val client = apiService.getLaundry(token)
        client.enqueue(object : Callback<LaundryResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<LaundryResponse>,
                response: Response<LaundryResponse>
            ) {
                _isLoading.value = false
                val listData = response.body()?.laundry
                if (response.isSuccessful) {
                    if (listData.isNullOrEmpty()) {
                        _toastText.value = Event("Laundry tidak ditemukan")
                    } else {
                        _listLaundryItem.value = listData
                    }
                } else {
                    _toastText.value = Event(response.message())
                    Log.e(TAG, "ErrorMessage: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LaundryResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event("No internet connection")
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun getLaundrySentiment(token: String) {
        Log.d("RESPONSE:", token)
        _isRecLoading.value = true
        val client = apiService.getLaundrySentiment(token)
        client.enqueue(object : Callback<LaundryRecomendationResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<LaundryRecomendationResponse>,
                response: Response<LaundryRecomendationResponse>
            ) {
                _isRecLoading.value = false
                val listData = response.body()?.laundry
                if (response.isSuccessful) {
                    if (listData.isNullOrEmpty()) {
                        _toastText.value = Event("Laundry tidak ditemukan")
                    } else {
                        _listLaundryRecItem.value = listData
                    }
                } else {
                    _toastText.value = Event(response.message())
                    Log.e(TAG, "ErrorMessage: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LaundryRecomendationResponse>, t: Throwable) {
                _isRecLoading.value = false
                _toastText.value = Event("No internet connection")
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun getUserDetail(token: String, USER_ID: String) {
        _isLoading.value = true
        val client = apiService.getUserDetail(token, USER_ID)
        client.enqueue(object : Callback<UserDetailResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userDetail = response.body()?.user
                    if (userDetail != null) {
                        _listUserItem.value = listOf(userDetail)
                    } else {
                        _toastText.value = Event("User data not found")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getLaundryDetailSimple(token: String, laundryId: String) {
        _isLoading.value = true
        val client = apiService.getLaundryDetail(token, laundryId)
        client.enqueue(object : Callback<LaundryDetailResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<LaundryDetailResponse>,
                response: Response<LaundryDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundryDetail = response.body()?.laundry
                    if (laundryDetail != null) {
                        _listLaundryDetail.value = listOf(laundryDetail)
                    } else {
                        _toastText.value = Event("Laundry data not found")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LaundryDetailResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getLaundryServiceSimple(token: String, laundryId: String) {
        _isLoading.value = true
        val client = apiService.getLaundryDetail(token, laundryId)
        client.enqueue(object : Callback<LaundryDetailResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<LaundryDetailResponse>,
                response: Response<LaundryDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundryDetail = response.body()?.services
                    if (laundryDetail != null) {
                        _listLaundryService.value = laundryDetail
                    } else {
                        _toastText.value = Event("Laundry service not found")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LaundryDetailResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getLaundryReviewsSimple(token: String, laundryId: String) {
        _isLoading.value = true
        val client = apiService.getLaundryDetail(token, laundryId)
        client.enqueue(object : Callback<LaundryDetailResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<LaundryDetailResponse>,
                response: Response<LaundryDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val laundryDetail = response.body()?.laundry?.reviews
                    if (laundryDetail != null) {
                        _listLaundryReviews.value = laundryDetail
                    } else {
                        _toastText.value = Event("Laundry reviews not found")
                    }
                } else {
                    _isLoading.value = false
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LaundryDetailResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun putUser(
        token: String,
        userId: String,
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        _isLoading.value = true
        val client = apiService.putUser(token, userId, oldPassword, newPassword, confirmPassword)

        client.enqueue(object : Callback<UserPasswordResponse> {
            override fun onResponse(
                call: Call<UserPasswordResponse>,
                response: Response<UserPasswordResponse>
            ) {
                _isLoading.value = false
                Log.d("ResponseChange : ", response.body().toString())
                if (response.isSuccessful && response.body() != null) {
                    _changePasswordResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.body()?.message.toString())
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.body()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<UserPasswordResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun putProfileUser(
        token: String,
        userId: String,
        userTelp: RequestBody,
        userCity: RequestBody,
        userGender: RequestBody,
        imageMultipart: MultipartBody.Part
    ) {
        _isLoading.value = true
        Log.d(
            "Change: ",
            "Token: $token, userId: $userId, userTelp: $userTelp, image: $imageMultipart"
        )
        val client = apiService.putProfUser(token, userId, userTelp, userCity, userGender, imageMultipart)

        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                Log.d("ResponseChangessss : ", response.toString())
                Log.d("ResponseChange : ", response.body().toString())
                if (response.isSuccessful && response.body() != null) {
                    _changeProfileResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.body()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun getFaq(token: String) {
        Log.d("RESPONSE:", token)
        _isLoading.value = true
        val client = apiService.getFaq(token)
        client.enqueue(object : Callback<FaqResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<FaqResponse>,
                response: Response<FaqResponse>
            ) {
                _isLoading.value = false
                val listData = response.body()?.faq
                if (response.isSuccessful) {
                    if (listData.isNullOrEmpty()) {
                        _toastText.value = Event("FAQ tidak ditemukan")
                    } else {
                        _listFaqItem.value = listData
                    }
                } else {
                    _toastText.value = Event(response.message())
                    Log.e(TAG, "ErrorMessage: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FaqResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event("No internet connection")
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun postTransactionOrder(token: String, laundryId: String, requestBody: String) {
        _isLoading.value = true
        val dataConvert = RequestBody.create("application/json".toMediaTypeOrNull(), requestBody)

        val client = apiService.postTransaction(token, laundryId, dataConvert)
        client.enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                _isLoading.value = false
                Log.d("RESP : ", response.toString())
                Log.d("RESPBDY : ", response.body().toString())
                if (response.isSuccessful && response.body() != null) {
                    _trxResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event("Process Failed")
                    val errorMessage = response.message() ?: ""
                    val responseBody = response.body()?.message.toString()
                    Log.e(TAG, "ErrorMessage: $errorMessage, $responseBody")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "ErrorMessage: ${t.message.toString()}")
            }
        })
    }

    fun getSession(): LiveData<SessionModel> {
        return preferences.getSession().asLiveData()
    }

    suspend fun saveSession(session: SessionModel) {
        preferences.saveSession(session)
    }

    suspend fun login() {
        preferences.login()
    }

    suspend fun clearLogout() {
        preferences.logout()
        _loginResponse.value = LoginResponse(Data("", "", "", "", false), false, "", 0)
    }

    companion object {
        private const val TAG = "LaundryRepository"

        @Volatile
        private var instance: LaundryRepository? = null
        fun getInstance(
            preferences: SessionPreferences,
            apiService: APIService
        ): LaundryRepository = instance ?: synchronized(this) {
            instance ?: LaundryRepository(preferences, apiService)
        }.also { instance = it }
    }
}