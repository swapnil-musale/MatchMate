package com.devx.matchmate.ui.profileMatch

import com.devx.domain.model.ProfileMatch

sealed class ProfileMatchScreenUiState {
    data object Ideal: ProfileMatchScreenUiState()

    data object Loading: ProfileMatchScreenUiState()

    data class Success(val profileMatchList: List<ProfileMatch>): ProfileMatchScreenUiState()

    data class Error(val message: String): ProfileMatchScreenUiState()
}
