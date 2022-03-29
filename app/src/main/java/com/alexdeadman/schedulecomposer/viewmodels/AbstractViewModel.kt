package com.alexdeadman.schedulecomposer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexdeadman.schedulecomposer.R
import com.alexdeadman.schedulecomposer.model.DataList
import com.alexdeadman.schedulecomposer.model.entity.Attributes
import com.alexdeadman.schedulecomposer.model.entity.Entity
import com.alexdeadman.schedulecomposer.utils.state.ListState
import com.alexdeadman.schedulecomposer.utils.state.ListState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class AbstractViewModel(
    private val get: suspend () -> DataList<out Entity<out Attributes>>
) : ViewModel() {

    private val _state = MutableStateFlow<ListState?>(null)
    val state: StateFlow<ListState?> = _state.asStateFlow()

    init {
        fetchEntities()
    }

    fun fetchEntities() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = try {
                val result = get()
                if (result.data.isEmpty()) NoItems
                Loaded(result)
            } catch (e: Exception) {
                Error(R.string.unknown_error /*TODO TEMPO*/)
            }
        }
    }
}