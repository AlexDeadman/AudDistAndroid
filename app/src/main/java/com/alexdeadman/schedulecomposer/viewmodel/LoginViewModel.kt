package com.alexdeadman.schedulecomposer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexdeadman.schedulecomposer.App.Companion.preferences
import com.alexdeadman.schedulecomposer.R
import com.alexdeadman.schedulecomposer.model.auth.LoginData
import com.alexdeadman.schedulecomposer.service.ScApi
import com.alexdeadman.schedulecomposer.util.key.PreferenceKeys
import com.alexdeadman.schedulecomposer.util.state.LoginState
import com.alexdeadman.schedulecomposer.util.state.LoginState.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val scApi: ScApi,
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState?>(null)
    val state: StateFlow<LoginState?> = _state.asStateFlow()

    fun getToken(username: String, password: String) {
        _state.value = Sending
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = try {
                val loginData = LoginData(
                    LoginData.Data(
                        "TokenCreateView",
                        LoginData.Data.Attributes(username, password)
                    )
                )
                val authToken = scApi.getToken(
                    Gson().toJson(loginData).toRequestBody()
                ).data.attributes.authToken

                preferences.edit().apply {
                    putString(PreferenceKeys.AUTH_TOKEN, authToken)
                    putString(PreferenceKeys.USERNAME, username)
                    apply()
                }
                Success
            } catch (e: Exception) {
                Error(R.string.unknown_error)
            }
        }
    }
}