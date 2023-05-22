package labs.nusantara.smartrinse.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getSession(): Flow<SessionModel> {
        return dataStore.data.map { preferences ->
            SessionModel(
                preferences[ID_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[STATE_KEY] ?: false,
            )
        }
    }

    suspend fun saveSession(session: SessionModel) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = session.userId
            preferences[EMAIL_KEY] = session.email
            preferences[NAME_KEY] = session.name
            preferences[STATE_KEY] = session.isLogin
            preferences[TOKEN_KEY] = session.token
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

//    fun getIdKey(): Preferences.Key<String> {
//        return ID_KEY
//    }

    companion object {
        @Volatile
        private var INSTANCE: SessionPreferences? = null
        private val ID_KEY = stringPreferencesKey("userId")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("islogin")

        fun getInstance(dataStore: DataStore<Preferences>): SessionPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}