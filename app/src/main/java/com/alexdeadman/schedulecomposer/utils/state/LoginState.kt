package com.alexdeadman.schedulecomposer.utils.state

sealed class LoginState {
    object Sending : LoginState()
    object Success : LoginState()
    class Error(val messageStringId: Int) : LoginState()
}
