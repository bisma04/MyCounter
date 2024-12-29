package com.example.mycounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycounter.event.CounterEvent
import com.example.mycounter.model.CounterModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CounterViewModel() : ViewModel() {
    private val _counterState = MutableStateFlow(CounterModel(count = 0))
    val counterState: StateFlow<CounterModel> = _counterState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<CounterEvent>()
    val eventFlow: SharedFlow<CounterEvent> = _eventFlow.asSharedFlow()

    fun increment() {
        val current = _counterState.value.count
        if (current < 100) {
            _counterState.value = CounterModel(current + 1)
        } else {
            emitEvent(CounterEvent.ShowMessage("Maximum limit reached"))
        }
    }

    fun decrement() {
        val current = _counterState.value.count
        if (current > 0) {
            _counterState.value = CounterModel(current - 1)
        } else {
            emitEvent(CounterEvent.ShowMessage("Minimum limit reached"))
        }
    }

    fun reset() {
        _counterState.value = CounterModel(0)
    }

    fun onNextClicked() {
        emitEvent(CounterEvent.NavigateToNextFragment)
    }

    private fun emitEvent(event: CounterEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

}