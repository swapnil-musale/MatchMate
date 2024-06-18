package com.devx.matchmate.ui.profileMatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devx.domain.useCase.GetProfileMatchesUseCase
import com.devx.domain.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PROFILE_COUNT_TO_LOAD = 1

@HiltViewModel
class ProfileMatchViewModel @Inject constructor(
    private val getProfileMatchesUseCase: GetProfileMatchesUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfileMatchScreenUiState> =
        MutableStateFlow(value = ProfileMatchScreenUiState())
    val uiState: StateFlow<ProfileMatchScreenUiState> = _uiState.asStateFlow()

    init {
        getProfileMatches()
    }

    private fun getProfileMatches() {
        viewModelScope.launch(context = coroutineDispatcher) {
            _uiState.update { it.copy(isLoading = true) }
            when (val response = getProfileMatchesUseCase(count = PROFILE_COUNT_TO_LOAD)) {
                is NetworkResponse.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            profileMatchList = response.data,
                        )
                    }
                }

                // Network HTTP Error
                is NetworkResponse.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = response.errorMessage ?: "",
                        )
                    }
                }

                // Other than Network Error
                is NetworkResponse.Exception -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = response.exception?.localizedMessage ?: "",
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        coroutineDispatcher.cancel()
        super.onCleared()
    }
}
