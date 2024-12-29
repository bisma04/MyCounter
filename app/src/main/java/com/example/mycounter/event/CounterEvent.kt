package com.example.mycounter.event

sealed class CounterEvent {
    data class ShowMessage(val message: String) : CounterEvent()
    data object NavigateToNextFragment : CounterEvent()
}