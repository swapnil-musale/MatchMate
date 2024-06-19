package com.devx.matchmate.ui.profileMatch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devx.data.repository.NetworkConnectivityManager
import com.devx.domain.useCase.GetProfileMatchesFromLocalUseCase
import com.devx.domain.useCase.GetProfileMatchesUseCase
import com.devx.domain.useCase.UpdateProfileMatchStatusUseCase
import com.devx.domain.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PROFILE_COUNT_TO_LOAD = 1
const val TAG = "TAG"

@HiltViewModel
class ProfileMatchViewModel
    @Inject
    constructor(
        private val getProfileMatchesUseCase: GetProfileMatchesUseCase,
        private val getProfileMatchesFromLocalUseCase: GetProfileMatchesFromLocalUseCase,
        private val updateProfileMatchStatusUseCase: UpdateProfileMatchStatusUseCase,
        private val networkConnectivityManager: NetworkConnectivityManager,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<ProfileMatchScreenUiState> =
            MutableStateFlow(value = ProfileMatchScreenUiState())
        val uiState: StateFlow<ProfileMatchScreenUiState> = _uiState.asStateFlow()

        private var isConnectionActive: Boolean = false

        init {
            networkConnectivityManager.register()
            getProfileMatchesFromLocal()

            viewModelScope.launch {
                networkConnectivityManager.isConnected.collect {
                    isConnectionActive = it
                }
            }
        }

        private fun getProfileMatchesFromLocal() {
            viewModelScope.launch(context = coroutineDispatcher) {
                _uiState.update { it.copy(isLoading = true) }
                delay(500)

                getProfileMatchesFromLocalUseCase(count = PROFILE_COUNT_TO_LOAD)
                    .catch { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = exception.localizedMessage,
                            )
                        }
                    }.collect { localProfileMatches ->
                        if (localProfileMatches.isEmpty()) {
                            getProfileMatchesFromNetwork()
                            return@collect
                        }

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                profileMatchList = localProfileMatches,
                            )
                        }
                    }
            }
        }

        fun getProfileMatchesFromNetwork() {
            if (isConnectionActive) {
                viewModelScope.launch(context = coroutineDispatcher) {
                    _uiState.update { it.copy(isLoading = true) }
                    when (val response = getProfileMatchesUseCase(count = PROFILE_COUNT_TO_LOAD)) {
                        is NetworkResponse.Success -> {
                            Log.d(TAG, "getProfileMatches: Success")
                        }

                        // Network HTTP Error
                        is NetworkResponse.Error -> {
                            Log.d(TAG, "getProfileMatches: Error")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = response.errorMessage ?: "",
                                )
                            }
                        }

                        // Other than Network HTTP Error
                        is NetworkResponse.Exception -> {
                            Log.d(TAG, "getProfileMatches: exception")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = response.exception?.localizedMessage ?: "",
                                )
                            }
                        }
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Internet connection not available",
                    )
                }
            }
        }

        fun onProfileMatchStatusUpdated(
            userId: String,
            updatedStatus: Int,
        ) {
            viewModelScope.launch(coroutineDispatcher) {
                updateProfileMatchStatusUseCase(userId = userId, status = updatedStatus)
            }
        }

        fun resetErrorMessage() {
            _uiState.update { it.copy(errorMessage = "") }
        }

        override fun onCleared() {
            coroutineDispatcher.cancel()
            networkConnectivityManager.unregister()
            super.onCleared()
        }
    }
