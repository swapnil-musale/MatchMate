package com.devx.matchmate.ui.profileMatch

import com.devx.domain.model.ProfileMatch
import kotlinx.collections.immutable.ImmutableList

sealed class ProfileMatchScreenUiState {
    data object Ideal: ProfileMatchScreenUiState()

    data object Loading: ProfileMatchScreenUiState()

    data class Success(val profileMatchList: ImmutableList<ProfileMatch>): ProfileMatchScreenUiState()

    data class Error(val message: String): ProfileMatchScreenUiState()
}
