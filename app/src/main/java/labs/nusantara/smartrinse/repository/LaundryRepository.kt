package labs.nusantara.smartrinse.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import labs.nusantara.smartrinse.services.api.APIService
import labs.nusantara.smartrinse.services.response.LoginResponse
import labs.nusantara.smartrinse.services.response.RegisterResponse
import labs.nusantara.smartrinse.utils.Event
import labs.nusantara.smartrinse.utils.SessionModel
import labs.nusantara.smartrinse.utils.SessionPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaundryRepository private constructor (
    private val preferences: SessionPreferences,
    private val apiService: APIService
){
    private val _regResponse = MutableLiveData<RegisterResponse>()
    val regResponse: LiveData<RegisterResponse> = _regResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

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
                    _toastText.value = Event(response.body()?.msg.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "ErrorMessage: ${response.message()}, ${response.body()?.msg.toString()}"
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
                    _toastText.value = Event(response.message().toString())
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

    fun getSession(): LiveData<SessionModel> {
        return preferences.getSession().asLiveData()
    }

    suspend fun saveSession(session: SessionModel) {
        preferences.saveSession(session)
    }

    suspend fun login() {
        preferences.login()
    }

    suspend fun logout() {
        preferences.logout()
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