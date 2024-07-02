package com.devx.matchmate.ui.profileMatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devx.domain.useCase.GetProfileMatchesUseCase
import com.devx.domain.useCase.UpdateProfileMatchStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMatchViewModel
    @Inject
    constructor(
        private val getProfileMatchesUseCase: GetProfileMatchesUseCase,
        private val updateProfileMatchStatusUseCase: UpdateProfileMatchStatusUseCase,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<ProfileMatchScreenUiState> =
            MutableStateFlow(value = ProfileMatchScreenUiState.Ideal)
        val uiState: StateFlow<ProfileMatchScreenUiState> = _uiState.asStateFlow()

        fun getProfileMatches(fromNetwork: Boolean = false) {
            viewModelScope.launch(context = coroutineDispatcher) {
                _uiState.value = ProfileMatchScreenUiState.Loading
                getProfileMatchesUseCase(
                    count = DEFAULT_PROFILE_COUNT_TO_LOAD,
                    fromNetwork = fromNetwork,
                ).catch { exception ->
                    _uiState.value =
                        ProfileMatchScreenUiState.Error(message = exception.localizedMessage.orEmpty())
                }.collect { localProfileMatches ->
                    _uiState.value =
                        ProfileMatchScreenUiState.Success(profileMatchList = localProfileMatches.reversed().toImmutableList())
                }
            }
        }

        fun onProfileMatchStatusUpdated(
            userId: String,
            updatedStatus: Int,
        ) {
            viewModelScope.launch(context = coroutineDispatcher) {
                updateProfileMatchStatusUseCase(userId = userId, status = updatedStatus)
            }
        }

        override fun onCleared() {
            coroutineDispatcher.cancel()
            super.onCleared()
        }
    }

private const val DEFAULT_PROFILE_COUNT_TO_LOAD = 10
