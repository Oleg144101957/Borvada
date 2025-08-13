package com.bo.va.da76.model.data

sealed class LoadingState {
    data object InitState : LoadingState()
    data object NoNetworkState : LoadingState()
    data class ContentState(val url: String) : LoadingState()
    data object HomeState : LoadingState()
}