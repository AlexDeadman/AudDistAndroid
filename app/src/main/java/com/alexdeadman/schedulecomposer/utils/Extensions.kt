package com.alexdeadman.schedulecomposer.utils

import android.content.Context
import android.text.Editable
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.alexdeadman.schedulecomposer.viewmodels.AbstractViewModel
import com.alexdeadman.schedulecomposer.viewmodels.ViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import com.validator.textinputvalidator.valid
import com.validator.textinputvalidator.validate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

fun Any?.toStringOrDash() = this?.toString() ?: "—"

fun Editable?.toStringOrNull() = if (this.isNullOrBlank()) null else this.toString()

fun Fragment.provideViewModel(
    factory: ViewModelFactory,
    clazz: KClass<out AbstractViewModel>,
): AbstractViewModel =
    ViewModelProvider(
        requireActivity(),
        factory.withClass(clazz)
    )[clazz.java]

inline fun <T> Flow<T>.launchRepeatingCollect(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit,
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect(action)
        }
    }
}

fun TextInputLayout.validate(validators: Collection<(String) -> Pair<Boolean, String>>) {
    validate("", validators) {}
}

fun TextInputLayout.isValid(): Boolean {
    return valid().also {
        if (!it) editText?.run {
            requestFocus()
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
